package com.booboomx.cooker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.booboomx.cooker.IView.ICookSearchResultView;
import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.component.twinklingrefreshlayout.Footer.BottomProgressView;
import com.booboomx.cooker.component.twinklingrefreshlayout.RefreshListenerAdapter;
import com.booboomx.cooker.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.presenter.CookSearchResultPresenter;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.CookListAdapter;
import com.booboomx.cooker.utils.ToastUtils;

import java.util.ArrayList;

import butterknife.Bind;

public class CookSearchResultActivity extends BaseActivity implements ICookSearchResultView{

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.refresh_layout)
    public TwinklingRefreshLayout twinklingRefreshLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    private int totalPages;
    private String name;
    private ArrayList<CookDetail> list;
    private CookListAdapter cookListAdapter;
    private CookSearchResultPresenter cookSearchResultPresenter;

    private final static String Intent_Key_Name = "name";
    private final static String Intent_Key_TotalPages = "totalPages";
    private final static String Intent_Key_List = "list";
    public static void startActivity(Activity activity, String name, int totalPages, ArrayList<CookDetail> list){
        Intent intent = new Intent(activity, CookSearchResultActivity.class);

        intent.putExtra(Intent_Key_Name, name);
        intent.putExtra(Intent_Key_TotalPages, totalPages);
        intent.putParcelableArrayListExtra(Intent_Key_List, list);

        activity.startActivity(intent);
    }

    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void init(Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        Intent intent = getIntent();
        name = intent.getStringExtra(Intent_Key_Name);
        totalPages = intent.getIntExtra(Intent_Key_TotalPages, 0);
        list = intent.getParcelableArrayListExtra(Intent_Key_List);

        getSupportActionBar().setTitle(name);

        recyclerList.setLayoutManager(new LinearLayoutManager(recyclerList.getContext()));
        cookListAdapter = new CookListAdapter(this);
        recyclerList.setAdapter(cookListAdapter);

        twinklingRefreshLayout.setOverScrollRefreshShow(false);
        twinklingRefreshLayout.setEnableRefresh(false);
        twinklingRefreshLayout.setEnableLoadmore(true);

        BottomProgressView bottomProgressView = new BottomProgressView(twinklingRefreshLayout.getContext());
        bottomProgressView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
        twinklingRefreshLayout.setBottomView(bottomProgressView);
        twinklingRefreshLayout.setOverScrollBottomShow(true);

        twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                cookSearchResultPresenter.loadMore();
            }
        });

        cookListAdapter.setDataList(list);
        cookListAdapter.notifyDataSetChanged();

        cookSearchResultPresenter = new CookSearchResultPresenter(this, name, totalPages, this);



    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_search_result;
    }

    @Override
    public void onBackPressed() {
        boolean success=getSupportFragmentManager().popBackStackImmediate();
        if(!success)
        super.onBackPressed();
    }

    @Override
    public void onCookSearchLoadMoreSuccess(ArrayList<CookDetail> list) {

        twinklingRefreshLayout.finishLoadmore();
        cookListAdapter.addItems(list);
    }

    @Override
    public void onCookSearchLoadMoreFaile(String msg) {
        twinklingRefreshLayout.finishLoadmore();
        ToastUtils.showToast(this,msg);

    }

    @Override
    public void onCookSearchLoadMoreNoData() {
        ToastUtils.showToast(this, getString(R.string.toast_msg_no_more_data));



    }
}
