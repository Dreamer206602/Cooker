package com.booboomx.cooker.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.booboomx.cooker.model.bean.TB_CustomCategory;
import com.booboomx.cooker.ui.fragment.CookListFragment;

import java.util.List;

/**
 * Created by booboomx on 17/4/21.
 */

public class MainPageViewPageAdapter extends FragmentPagerAdapter{

    private List<TB_CustomCategory>mCustomCategories;
    public MainPageViewPageAdapter(FragmentManager fm,List<TB_CustomCategory>customCategories) {

        super(fm);
        this.mCustomCategories=customCategories;
    }

    @Override
    public CookListFragment getItem(int position) {

        CookListFragment fragment=null;
        fragment=new CookListFragment();
        fragment.setCustomCategoryData(mCustomCategories.get(position));

        return fragment;
    }

    @Override
    public int getCount() {

        return mCustomCategories.size()>0?mCustomCategories.size():0;
    }

    @Override
    public long getItemId(int position) {

        int hashCode = mCustomCategories.get(position).hashCode();

        return hashCode;
    }


}
