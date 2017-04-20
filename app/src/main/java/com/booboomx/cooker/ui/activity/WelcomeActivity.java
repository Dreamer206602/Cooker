package com.booboomx.cooker.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.booboomx.cooker.IView.IWelcomeView;
import com.booboomx.cooker.presenter.WelcomePresenter;

public class WelcomeActivity extends Activity implements IWelcomeView{

    private WelcomePresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=new WelcomePresenter(this,this);
        mPresenter.initData();
    }

    @Override
    public void onWelcomeInitData() {
        startMainActivity();

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    private void startMainActivity(){
        Intent intent=new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();

    }




}
