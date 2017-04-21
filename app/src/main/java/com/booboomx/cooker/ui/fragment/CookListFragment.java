package com.booboomx.cooker.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.booboomx.cooker.IView.ICookListView;
import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseFragment;
import com.booboomx.cooker.component.fab_transformation.FabTransformation;
import com.booboomx.cooker.component.twinklingrefreshlayout.Footer.BottomProgressView;
import com.booboomx.cooker.component.twinklingrefreshlayout.PeRefreshLayout.PeRefreshLayout;
import com.booboomx.cooker.component.twinklingrefreshlayout.PeRefreshLayout.PeRefreshLayoutListener;
import com.booboomx.cooker.component.twinklingrefreshlayout.RefreshListenerAdapter;
import com.booboomx.cooker.component.twinklingrefreshlayout.TwinklingRefreshLayout;
import com.booboomx.cooker.component.twinklingrefreshlayout.header.bezierlayout.BezierLayout;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.model.bean.TB_CustomCategory;
import com.booboomx.cooker.presenter.CookListPresenter;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.CookListAdapter;
import com.booboomx.cooker.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 */
public class CookListFragment extends BaseFragment implements PeRefreshLayoutListener, ICookListView {



    @Bind(R.id.refreshLayout_data)
    public PeRefreshLayout mPeRefreshLayout;
    @Bind(R.id.view_overlay)
    public View viewOverlay;
    @Bind(R.id.fab_app)
    public FloatingActionButton floatingActionButton;
    @Bind(R.id.view_sheet)
    public View viewSheet;

    public TwinklingRefreshLayout mTwinklingRefreshLayout;
    public RecyclerView mRecyclerView;

    public CookListAdapter mAdapter;

    private TB_CustomCategory mTB_customCategory;
    private CookListPresenter mPresenter;



    public void setCustomCategoryData(TB_CustomCategory customCategoryData){
        this.mTB_customCategory = customCategoryData;
    }
    @Override
    protected Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPeRefreshLayout.setPeRefreshLayoutListener(this);

        mTwinklingRefreshLayout = mPeRefreshLayout.getTwinklingRefreshLayout();

        mRecyclerView = mPeRefreshLayout.getRecyclerView();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter=new CookListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        BezierLayout headerView = new BezierLayout(getActivity());
        headerView.setRoundProgressColor(getResources().getColor(R.color.colorPrimary));
        headerView.setWaveColor(getResources().getColor(R.color.main_indicator_bg));
        headerView.setRippleColor(getResources().getColor(R.color.white));
        mTwinklingRefreshLayout.setHeaderView(headerView);
        mTwinklingRefreshLayout.setWaveHeight(140);

        BottomProgressView bottomProgressView = new BottomProgressView(mTwinklingRefreshLayout.getContext());
        bottomProgressView.setAnimatingColor(getResources().getColor(R.color.colorPrimary));
        mTwinklingRefreshLayout.setBottomView(bottomProgressView);
        mTwinklingRefreshLayout.setOverScrollBottomShow(true);


        ArrayList<CookDetail>datas=new ArrayList<>();
        mAdapter.setDataList(datas);



        mPresenter=new CookListPresenter(getContext(),this);
        mPresenter.updateRefreshCookMenuByID(mTB_customCategory.getCtgId());

        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);

                mPresenter.updateRefreshCookMenuByID(mTB_customCategory.getCtgId());
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);

                mPresenter.loadMoreCookMenuByID(mTB_customCategory.getCtgId());
            }
        });

    }


    @OnClick(R.id.fab_app)
    public void  onClickFabApp(){
        if(floatingActionButton.getVisibility()==View.VISIBLE){
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay).transformTo(viewSheet);
        }
    }


    @OnClick(R.id.view_overlay)
    public void onClickOverlay(){
        closeFabMenu();
    }

    public boolean closeFabMenu(){
        if(floatingActionButton.getVisibility()!=View.VISIBLE){
            FabTransformation.with(floatingActionButton).setOverlay(viewOverlay)
                    .transformFrom(viewSheet);

            return true;
        }
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cook_list;
    }

    @Override
    public void onPeRefreshLayoutClick() {
        mPresenter.updateRefreshCookMenuByID(mTB_customCategory.getCtgId());

    }

    @Override
    public void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list) {

        if(mPeRefreshLayout.isShowDataView()){
            mPeRefreshLayout.setModeList();
        }

        mTwinklingRefreshLayout.finishRefreshing();
        mAdapter.setDataList(conversion(list));
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCookListUpdateRefreshFaile(String msg) {

        if(mPeRefreshLayout.isShowDataView()){
            mPeRefreshLayout.setModeException(msg);
            return;
        }

        mTwinklingRefreshLayout.finishRefreshing();
        ToastUtils.showToast(getContext(),msg);

    }

    @Override
    public void onCookListLoadMoreSuccess(ArrayList<CookDetail> list) {
        mTwinklingRefreshLayout.finishLoadmore();
        mAdapter.addItems(conversion(list));

    }

    @Override
    public void onCookListLoadMoreFaile(String msg) {
        mTwinklingRefreshLayout.finishRefreshing();
        ToastUtils.showToast(getContext(),msg);

    }

    private List<CookDetail> conversion(ArrayList<CookDetail>list){
        List<CookDetail>datas=new ArrayList<>();
        for (CookDetail item:list){
            datas.add(item);
        }
        return datas;
    }
}
