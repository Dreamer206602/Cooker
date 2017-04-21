package com.booboomx.cooker.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseFragment;
import com.booboomx.cooker.component.magicindicator.MagicIndicator;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import com.booboomx.cooker.component.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import com.booboomx.cooker.manager.CustomCategoryManager;
import com.booboomx.cooker.model.bean.TB_CustomCategory;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.MainPageViewPageAdapter;

import java.util.List;

import butterknife.Bind;

/**
 *
 */
public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private List<TB_CustomCategory>mCustomCategories;

    @Bind(R.id.magic_indicator)
    public MagicIndicator magicIndicator;
    @Bind(R.id.view_pager)
    public ViewPager viewPager;


    private CommonNavigator mCommonNavigator;
    private MainPageViewPageAdapter mPageAdapter;



    @Override
    protected Presenter getPresenter() {
        return null;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        initIndicatorView();
    }

    private void initIndicatorView() {

        mCustomCategories= CustomCategoryManager.getInstance().getDatas();

        mCommonNavigator=new CommonNavigator(getContext());
        mCommonNavigator.setScrollPivotX(0.35f);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mCustomCategories.size()>0?mCustomCategories.size():0;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView=new SimplePagerTitleView(getContext());
                simplePagerTitleView.setText(mCustomCategories.get(index).getName());
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator=new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#de9816"));

                return indicator;
            }
        });


        magicIndicator.setNavigator(mCommonNavigator);
        mPageAdapter=new MainPageViewPageAdapter(getFragmentManager(),mCustomCategories);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mPageAdapter);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);


    }

    @Override
    public void onPageSelected(int position) {

        magicIndicator.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        magicIndicator.onPageScrollStateChanged(state);

    }

    public void  updateChannel(){
        initIndicatorView();
    }
}
