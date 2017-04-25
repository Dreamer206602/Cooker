package com.booboomx.cooker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.manager.CookCategoryManager;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.CookCategoryFirAdapter;
import com.booboomx.cooker.ui.adapter.CookCategorySndAdapter;

import butterknife.Bind;

public class CookCategoryActivity extends BaseActivity implements CookCategoryFirAdapter.OnCookCategoryFirListener, CookCategorySndAdapter.OnCookCategorySndListener {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.recyclerview_list_category)
    public RecyclerView recyclerListCategory;
    @Bind(R.id.recyclerview_list_content)
    public RecyclerView recyclerListContent;

    private CookCategoryFirAdapter cookCategoryFirAdapter;
    private CookCategorySndAdapter cookCategorySndAdapter;

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
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        cookCategoryFirAdapter = new CookCategoryFirAdapter(this);
        cookCategoryFirAdapter.setDataList(CookCategoryFirAdapter.createDatas(CookCategoryManager.getInstance().getCategoryFirDatas()));

        recyclerListCategory.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListCategory.setAdapter(cookCategoryFirAdapter);

        cookCategorySndAdapter = new CookCategorySndAdapter(this);
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(CookCategoryManager.getInstance().getCategoryFirDatas().get(0).getCtgId())
                )
        );
        recyclerListContent.setLayoutManager(new LinearLayoutManager(recyclerListCategory.getContext()));
        recyclerListContent.setAdapter(cookCategorySndAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_category;
    }

    @Override
    public void onBackPressed() {
        boolean b = getSupportFragmentManager().popBackStackImmediate();

        if(!b)
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if(itemId==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void  startActivity(Activity activity){
        Intent intent=new Intent(activity,CookCategoryActivity.class);
        activity.startActivity(intent);

    }

    @Override
    public void onCategoryFirClick(String ctgId) {
        cookCategorySndAdapter.setDataList(
                CookCategorySndAdapter.createDatas(
                        CookCategoryManager.getInstance().getCategorySndDatas(ctgId)
                )
        );


    }

    @Override
    public void onCookCategorySndClick(String ctgId, String name) {

        CookListActivity.startActivity(this,ctgId,name);


    }
}

