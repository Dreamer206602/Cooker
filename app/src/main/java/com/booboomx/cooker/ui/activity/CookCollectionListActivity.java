package com.booboomx.cooker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.component.dialog.CollectionSelectDialog;
import com.booboomx.cooker.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.booboomx.cooker.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;
import com.booboomx.cooker.manager.CookCollectionManager;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.model.bean.TB_CookDetail;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.CookCollectionListAdapter;

import butterknife.Bind;

public class CookCollectionListActivity extends BaseActivity implements CookCollectionListAdapter.OnCookCollectionListListener {


    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.refresh_layout)
    public TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    private CookCollectionListAdapter cookCollectionListAdapter;

    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.collection);

        cookCollectionListAdapter = new CookCollectionListAdapter(this);
        cookCollectionListAdapter.setDataList(CookCollectionManager.getInstance().get());
        recyclerList.setLayoutManager(new LinearLayoutManager(recyclerList.getContext()));
        recyclerList.setAdapter(cookCollectionListAdapter);

        BezierLayout headerView = new BezierLayout(this);
        twinklingRefreshLayout.setHeaderView(headerView);
        twinklingRefreshLayout.setPureScrollModeOn(true);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_collection_list;
    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, CookCollectionListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCookCollectionListClick(View view, CookDetail cook) {
        CookDetailActivity.startAvtivity(this, view, cook, false);

    }

    @Override
    public void onCookCollectionListMore(final TB_CookDetail cook) {

        CollectionSelectDialog dlg = new CollectionSelectDialog(this);
        dlg.setOnCollectionSelectListener(new CollectionSelectDialog.OnCollectionSelectListener() {
            @Override
            public void onCollectionSelectDelete() {
                CookCollectionManager.getInstance().delete(CookCollectionManager.getInstance().tb2CookDetail(cook));
                cookCollectionListAdapter.deletItem(cook);
            }
        });
        dlg.show();

    }
}
