package com.booboomx.cooker.base;

import android.content.Context;
import android.os.Bundle;

import com.booboomx.cooker.component.swipebacklayout.app.SwipeBackActivity;
import com.booboomx.cooker.component.swipebacklayout.app.SwipeBackActivityBase;
import com.booboomx.cooker.presenter.Presenter;

import butterknife.ButterKnife;

/**
 * Created by booboomx on 17/4/20.
 */

public abstract  class BaseActivity extends SwipeBackActivity implements SwipeBackActivityBase {
    public Context mContext;
    public Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //禁止横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        mContext=this;
        ButterKnife.bind(this);

        init(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mPresenter==null&&getPresenter()!=null){
            mPresenter=getPresenter();
        }
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();

    }

    public abstract  Presenter getPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

}
