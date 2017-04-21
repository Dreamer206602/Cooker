package com.booboomx.cooker.presenter;

import android.content.Context;

import com.booboomx.cooker.IView.ICookListView;
import com.booboomx.cooker.R;
import com.booboomx.cooker.model.bean.SearchCookMenuSubscriberResultInfo;
import com.booboomx.cooker.model.interfaces.ICookRespository;
import com.booboomx.cooker.model.respository.CookRespository;
import com.booboomx.cooker.utils.Constants;
import com.booboomx.cooker.utils.ErrorExceptionUtil;

import rx.Subscriber;

/**
 * Created by booboomx on 17/4/21.
 */

public class CookListPresenter extends Presenter{

    private ICookListView iCookListView;
    private ICookRespository iCookRespository;
    private int curPage = 1;
    private int totalPages = 1;

    public CookListPresenter(Context context, ICookListView iCookListView){
        super(context);

        this.iCookListView = iCookListView;
        this.iCookRespository = CookRespository.getInstance();
    }

    public void destroy(){
        if(updateRefreshCookMenuByIDSubscriber != null){
            updateRefreshCookMenuByIDSubscriber.unsubscribe();
        }

        if(loadMoreCookMenuByIDSubscriber != null){
            loadMoreCookMenuByIDSubscriber.unsubscribe();
        }
    }

    public void updateRefreshCookMenuByID(String cid){
        curPage = 1;
        mRxJavaExecutor.execute(
                iCookRespository.searchCookMenuByID(cid, curPage, Constants.Per_Page_Size)
                , updateRefreshCookMenuByIDSubscriber = new UpdateRefreshCookMenuByIDSubscriber()
        );
    }

    public void loadMoreCookMenuByID(String cid){
        curPage++;
        if(curPage > totalPages){
            curPage--;
            if(iCookListView != null)
                iCookListView.onCookListLoadMoreFaile(mContext.getString(R.string.toast_msg_no_more_data));
            return ;
        }

        mRxJavaExecutor.execute(
                iCookRespository.searchCookMenuByID(cid, curPage, Constants.Per_Page_Size)
                , loadMoreCookMenuByIDSubscriber = new LoadMoreCookMenuByIDSubscriber()
        );
    }

    private LoadMoreCookMenuByIDSubscriber loadMoreCookMenuByIDSubscriber;
    private class LoadMoreCookMenuByIDSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(loadMoreCookMenuByIDSubscriber != null){
                loadMoreCookMenuByIDSubscriber.unsubscribe();
            }

            if(iCookListView != null)
                iCookListView.onCookListLoadMoreFaile(e.getMessage());

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){

            if(iCookListView != null)
                iCookListView.onCookListLoadMoreSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }

    private UpdateRefreshCookMenuByIDSubscriber updateRefreshCookMenuByIDSubscriber;
    private class UpdateRefreshCookMenuByIDSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(updateRefreshCookMenuByIDSubscriber != null){
                updateRefreshCookMenuByIDSubscriber.unsubscribe();
            }

            if(iCookListView != null)
                iCookListView.onCookListUpdateRefreshFaile(ErrorExceptionUtil.getErrorMsg(e));

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data){
            totalPages = data.getResult().getTotal();

            if(iCookListView != null)
                iCookListView.onCookListUpdateRefreshSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }
}

