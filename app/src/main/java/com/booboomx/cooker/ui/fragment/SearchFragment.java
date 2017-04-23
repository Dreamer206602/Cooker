package com.booboomx.cooker.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.cooker.IView.ICookSearchView;
import com.booboomx.cooker.R;
import com.booboomx.cooker.component.fab_transformation.animation.SupportAnimator;
import com.booboomx.cooker.component.fab_transformation.animation.ViewAnimationUtils;
import com.booboomx.cooker.component.tagCloudLayout.TagCloudLayout;
import com.booboomx.cooker.manager.CookSearchHistoryManager;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.model.bean.CookSearchHistory;
import com.booboomx.cooker.presenter.CookSearchPresenter;
import com.booboomx.cooker.ui.activity.CookSearchResultActivity;
import com.booboomx.cooker.ui.adapter.TagCookSearchHistoryAdapter;
import com.booboomx.cooker.utils.KeyboardUtil;
import com.booboomx.cooker.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索的界面
 */
public class SearchFragment extends Fragment implements View.OnClickListener, ICookSearchView {

    @Bind(R.id.content)
    public View content;
    @Bind(R.id.edit_lay)
    public View editLay;
    @Bind(R.id.edit_search)
    public EditText editSearch;
    @Bind(R.id.img_search)
    public ImageView imgvSearch;
    @Bind(R.id.items)
    public View items;
    @Bind(R.id.tag_history)
    public TagCloudLayout tagHistory;

    private int centerX;
    private int centerY;

    private List<CookSearchHistory> datas;

    private TagCookSearchHistoryAdapter mHistoryAdapter;
    private CookSearchPresenter mPresenter;

    public boolean onBackPressed() {
        SupportAnimator mRevealAnimator = ViewAnimationUtils.createCircularReveal(
                content, centerX, centerY, 20, hypo(content.getWidth(), content.getHeight()));
        mRevealAnimator = mRevealAnimator.reverse();

        if (mRevealAnimator == null)
            return false;

        mRevealAnimator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
                content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd() {
                content.setVisibility(View.INVISIBLE);
                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel() {
            }

            @Override
            public void onAnimationRepeat() {
            }
        });

        mRevealAnimator.setDuration(400);
        mRevealAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRevealAnimator.start();

        return true;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        editLay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                editLay.getViewTreeObserver().removeOnPreDrawListener(this);
                items.setVisibility(View.INVISIBLE);
                items.setOnClickListener(SearchFragment.this);
                editLay.setVisibility(View.INVISIBLE);

                centerX = imgvSearch.getLeft() + imgvSearch.getWidth() / 2;
                centerY = imgvSearch.getTop() + imgvSearch.getHeight() / 2;


                SupportAnimator animator = ViewAnimationUtils.createCircularReveal(
                        editLay, centerX, centerY, 20, hypo(editLay.getWidth(), editLay.getHeight()));

                animator.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                        editLay.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                items.setVisibility(View.VISIBLE);
                                editSearch.requestFocus();
                                if (getActivity() != null) {
                                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(editSearch, InputMethodManager.SHOW_IMPLICIT);
                                }
                            }
                        }, 100);


                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });

                animator.setDuration(400);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());

                animator.start();
                return true;
            }
        });

        datas = CookSearchHistoryManager.getInstance().getDatas();
        mHistoryAdapter = new TagCookSearchHistoryAdapter(getContext(), datas);
        tagHistory.setAdapter(mHistoryAdapter);
        tagHistory.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                if (position == datas.size()) {
                    tagCookSearchHistoryClean();
                } else {
                    tagCookSearchHistoryClick(datas.get(position).getName());
                }
            }
        });


        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    search(editSearch.getText().toString().trim());
                }
                return true;
            }
        });


        mPresenter = new CookSearchPresenter(getContext(), this);

        return view;
    }


    private void tagCookSearchHistoryClick(String key) {
        search(key);
    }

    private void tagCookSearchHistoryClean() {

        CookSearchHistoryManager.getInstance().clean();
        tagHistory.setVisibility(View.GONE);

    }

    private float hypo(int a, int b) {

        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.root:
                onBackPressed();
                break;
        }

    }

    @OnClick(R.id.img_search)
    public void search() {
        search(editSearch.getText().toString().trim());
    }


    private String searchKey = "";

    private void search(String text) {
        //去除换行符
        searchKey = text.replaceAll("\r|\n", "");

        if (TextUtils.isEmpty(searchKey))
            return;

        KeyboardUtil.showKeyboard(getActivity(), editSearch, false);
        CookSearchHistoryManager.getInstance().add2Buffer(new CookSearchHistory(searchKey));
        mPresenter.search(searchKey);
    }

    @Override
    public void onCookSearchSuccess(ArrayList<CookDetail> list, int totalPages) {

        CookSearchHistoryManager.getInstance().save();
        CookSearchResultActivity.startActivity(getActivity(), searchKey, totalPages, list);
        onBackPressed();

    }

    @Override
    public void onCookSearchFaile(String msg) {
        ToastUtils.showToast(getActivity(), msg);

    }

    @Override
    public void onCookSearchEmpty() {

        ToastUtils.showToast(getActivity(), getString(R.string.toast_msg_no_more_search_data));


    }
}
