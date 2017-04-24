package com.booboomx.cooker.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.fragment.MainFragment;
import com.booboomx.cooker.utils.ToastUtils;

public class MainActivity extends BaseActivity {

    private MainFragment mMainFragment;
    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        //禁止使用侧滑
        setSwipeBackEnable(false);

        mMainFragment=new MainFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_main_content,mMainFragment)
                .show(mMainFragment)
                .commit();


    }


    private boolean doubleBackToExitPressedOnce=false;

    @Override
    public void onBackPressed() {
        if(mMainFragment.onBackPressed()){
            return;
        }


        if(doubleBackToExitPressedOnce){
            super.onBackPressed();

            return;
        }

        this.doubleBackToExitPressedOnce=true;
        ToastUtils.showToast(this, R.string.toast_msg_oncemore_exit);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CookChannelActivity.Request_Code_Channel &&
                resultCode == CookChannelActivity.Result_Code_Channel_NoChanged){
            if(mMainFragment != null)
                mMainFragment.updateChannel();
        }
//
    }
}
