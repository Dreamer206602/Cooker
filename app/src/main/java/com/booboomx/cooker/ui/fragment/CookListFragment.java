package com.booboomx.cooker.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseFragment;
import com.booboomx.cooker.model.bean.TB_CustomCategory;
import com.booboomx.cooker.presenter.Presenter;

/**
 */
public class CookListFragment extends BaseFragment {



    private TB_CustomCategory mTB_customCategory;


    public void setCustomCategoryData(TB_CustomCategory customCategoryData){
        this.mTB_customCategory = customCategoryData;
    }
    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cook_list;
    }

}
