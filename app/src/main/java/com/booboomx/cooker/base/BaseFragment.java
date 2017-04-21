package com.booboomx.cooker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.cooker.presenter.Presenter;

import butterknife.ButterKnife;

/**
 * Created by booboomx on 17/4/20.
 */

public abstract class BaseFragment extends Fragment{

    protected Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,view);
        initView(inflater,container,savedInstanceState);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(mPresenter==null&&getPresenter()!=null){
            mPresenter=getPresenter();
        }
    }

    protected abstract Presenter getPresenter();

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        if(mPresenter!=null){
            mPresenter.destroy();
        }
        super.onDestroy();

    }

    protected abstract void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract int getLayoutId();
}
