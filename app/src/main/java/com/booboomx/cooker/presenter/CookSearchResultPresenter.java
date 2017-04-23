package com.booboomx.cooker.presenter;

import android.content.Context;

import com.booboomx.cooker.IView.ICookSearchResultView;
import com.booboomx.cooker.model.bean.SearchCookMenuSubscriberResultInfo;
import com.booboomx.cooker.model.interfaces.ICookRespository;
import com.booboomx.cooker.model.respository.CookRespository;
import com.booboomx.cooker.utils.Constants;

import rx.Subscriber;

/**
 * Created by booboomx on 17/4/23.
 */

public class CookSearchResultPresenter extends Presenter {
    private ICookSearchResultView mICookSearchResultView;
    private ICookRespository mICookRespository;
    private String searchkey;
    private int curPage=1;
    private int totalPages=1;
    public CookSearchResultPresenter(Context context,String searchKey,int totalPages,ICookSearchResultView searchResultView) {
        super(context);
        this.searchkey=searchKey;
        this.totalPages=totalPages;

        this.mICookRespository= CookRespository.getInstance();
        this.mICookSearchResultView=searchResultView;

    }


    private SearchCookMenuByNameSubscriber mSearchCookMenuByNameSubscriber;
    private class  SearchCookMenuByNameSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            if (mSearchCookMenuByNameSubscriber != null) {
                mSearchCookMenuByNameSubscriber.unsubscribe();
            }

        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data) {

            if(data==null||data.getResult()==null){
                if (mICookSearchResultView != null) {
                    mICookSearchResultView.onCookSearchLoadMoreFaile("找不到相关的菜谱");
                }

                this.onCompleted();
                return;

            }


            totalPages=data.getResult().getTotal();
            if (mICookSearchResultView != null) {
                mICookSearchResultView.onCookSearchLoadMoreSuccess(data.getResult().getList());
            }

            this.onCompleted();


        }
    }


    public void  loadMore(){

        curPage++;
        if(curPage>totalPages){
            curPage--;
            if (mICookSearchResultView != null) {
                mICookSearchResultView.onCookSearchLoadMoreNoData();
            }

            return;
        }


        mRxJavaExecutor.execute(mICookRespository.searchCookMenuByName(searchkey,curPage, Constants.Per_Page_Size),
                mSearchCookMenuByNameSubscriber=new SearchCookMenuByNameSubscriber()
                );

    }



    @Override
    public void destroy() {
        if (mSearchCookMenuByNameSubscriber != null) {
            mSearchCookMenuByNameSubscriber.unsubscribe();
        }

    }


}
