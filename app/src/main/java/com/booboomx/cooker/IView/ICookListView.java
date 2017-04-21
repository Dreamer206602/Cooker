package com.booboomx.cooker.IView;

import com.booboomx.cooker.model.bean.CookDetail;

import java.util.ArrayList;

/**
 * Created by booboomx on 17/4/21.
 */

public interface ICookListView {
    void onCookListUpdateRefreshSuccess(ArrayList<CookDetail> list);
    void onCookListUpdateRefreshFaile(String msg);

    void onCookListLoadMoreSuccess(ArrayList<CookDetail> list);
    void onCookListLoadMoreFaile(String msg);
}
