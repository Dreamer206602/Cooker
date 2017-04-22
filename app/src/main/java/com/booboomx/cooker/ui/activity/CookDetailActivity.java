package com.booboomx.cooker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.CookDetailAdapter;
import com.booboomx.cooker.utils.GlideUtil;
import com.booboomx.cooker.utils.StatusBarUtil;

import butterknife.Bind;

public class CookDetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    public CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.recyclerview_list)
    public RecyclerView recyclerList;

    @Bind(R.id.imgv_bg)
    public ImageView imgvBg;

    private CookDetail mCookDetail;
    private boolean isShowCollection;
    private GlideUtil mGlideUtil;

    private CookDetailAdapter mAdapter;



    private final static String Intnet_Data_Cook = "cook";
    private final static String Intnet_Data_Collection = "collection";

    public static void startAvtivity(Activity activity, View view, CookDetail detail,boolean isShowCollection){
        Intent intent=new Intent(activity,CookDetailActivity.class);
        intent.putExtra(Intnet_Data_Cook,detail);
        intent.putExtra(Intnet_Data_Collection,isShowCollection);


        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                Pair.create(view,activity.getString(R.string.transition_cook_detail_imgv_bg))
                ,
                Pair.create(view,activity.getString(R.string.transition_cook_detail_content))

        );

        activity.startActivityForResult(intent,10029,optionsCompat.toBundle());
    }

    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarUtil.setImmersiveStatusBar(this);
        StatusBarUtil.setImmersiveStatusBarToolbar(toolbar,this);

        setSupportActionBar(toolbar);
        ActionBar actionBar =
                getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        mCookDetail=intent.getParcelableExtra(Intnet_Data_Cook);

        isShowCollection=intent.getBooleanExtra(Intnet_Data_Collection,false);


        if (mCookDetail == null) {

            return;
        }

        mGlideUtil=new GlideUtil();

        if(mCookDetail.getThumbnail()!=null){
            mGlideUtil.attach(imgvBg).injectImageWithNull(mCookDetail.getThumbnail());
        }


        getSupportActionBar().setTitle(mCookDetail.getName());
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter=new CookDetailAdapter(this,mCookDetail,isShowCollection);
        recyclerList.setAdapter(mAdapter);




    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_detail;
    }
}
