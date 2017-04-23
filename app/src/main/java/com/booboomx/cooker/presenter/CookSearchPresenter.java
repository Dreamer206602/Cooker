package com.booboomx.cooker.presenter;

import android.content.Context;

import com.booboomx.cooker.IView.ICookSearchView;
import com.booboomx.cooker.model.bean.SearchCookMenuSubscriberResultInfo;
import com.booboomx.cooker.model.interfaces.ICookRespository;
import com.booboomx.cooker.model.respository.CookRespository;
import com.booboomx.cooker.utils.Constants;
import com.booboomx.cooker.utils.ErrorExceptionUtil;

import rx.Subscriber;

/**
 * Created by booboomx on 17/4/23.
 */

public class CookSearchPresenter extends Presenter {
    private ICookSearchView mICookSearchView;
    private ICookRespository mICookRespository;

    public CookSearchPresenter(Context context, ICookSearchView iCookSearchView) {
        super(context);
        mICookSearchView = iCookSearchView;
        mICookRespository = CookRespository.getInstance();
    }


    public  SearchCookMenuByNameSubscriber mSearchCookMenuByNameSubscriber;
    public class  SearchCookMenuByNameSubscriber extends Subscriber<SearchCookMenuSubscriberResultInfo>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            if (mSearchCookMenuByNameSubscriber != null) {
                mSearchCookMenuByNameSubscriber.unsubscribe();
            }

            if (mICookSearchView != null) {

                mICookSearchView.onCookSearchFaile(ErrorExceptionUtil.getErrorMsg(e));
            }
        }

        @Override
        public void onNext(SearchCookMenuSubscriberResultInfo data) {

            if(data==null||data.getResult()==null){
                if (mICookSearchView != null) {
                    mICookSearchView.onCookSearchFaile("找不到相关的菜谱");
                }

                this.onCompleted();
                return;

            }

            int totalPages
                    = data.getResult().getTotal();

            if (mICookSearchView != null) {


                if(data.getResult().getList().size()<1){
                    mICookSearchView.onCookSearchEmpty();
                }else{
                    mICookSearchView.onCookSearchSuccess(data.getResult().getList(),totalPages);
                }
            }


            this.onCompleted();


        }
    }


    public void  search(String name){

        mRxJavaExecutor.execute(mICookRespository.searchCookMenuByName(name,1, Constants.Per_Page_Size),
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
