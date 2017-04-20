package com.booboomx.cooker.presenter;

import android.content.Context;

import com.booboomx.cooker.IView.IWelcomeView;
import com.booboomx.cooker.manager.CookCategoryManager;
import com.booboomx.cooker.manager.CustomCategoryManager;
import com.booboomx.cooker.model.bean.CategoryChildInfo1;
import com.booboomx.cooker.model.bean.CategorySubscriberResultInfo;
import com.booboomx.cooker.model.interfaces.ICookRespository;
import com.booboomx.cooker.model.respository.CookRespository;

import java.util.ArrayList;

import rx.Subscriber;

/**
 * Created by booboomx on 17/4/20.
 */

public class WelcomePresenter extends Presenter {

    private ICookRespository mICookRespository;
    private IWelcomeView mWelcomeView;

    public WelcomePresenter(Context context,IWelcomeView iWelcomeView) {
        super(context);


        this.mICookRespository= CookRespository.getInstance();
        this.mWelcomeView=iWelcomeView;

    }

    public void initData(){

        mRxJavaExecutor.execute(mICookRespository.getCategoryQuery(),
                mGetCategoryQuerySubscriber=new GetCategoryQuerySubscriber()

                );

    }


    private GetCategoryQuerySubscriber mGetCategoryQuerySubscriber;
    private class  GetCategoryQuerySubscriber extends Subscriber<CategorySubscriberResultInfo>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

            if(mGetCategoryQuerySubscriber!=null){
                mGetCategoryQuerySubscriber.unsubscribe();
            }

            CustomCategoryManager.getInstance().initData(null);


            if(mWelcomeView!=null){
                mWelcomeView.onWelcomeInitData();
            }



        }

        @Override
        public void onNext(CategorySubscriberResultInfo data) {


            ArrayList<CategoryChildInfo1> categoryChildInfo1s = CookCategoryManager.removeBangZi(data.getResult().getChilds());

            CookCategoryManager.getInstance().initDatas(categoryChildInfo1s);

            CustomCategoryManager.getInstance().initData(categoryChildInfo1s);


            if(mWelcomeView!=null){
                mWelcomeView.onWelcomeInitData();
            }

            this.onCompleted();
        }
    }



    @Override
    public void destroy() {

    }
}
