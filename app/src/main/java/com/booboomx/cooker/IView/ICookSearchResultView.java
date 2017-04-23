package com.booboomx.cooker.IView;

import com.booboomx.cooker.model.bean.CookDetail;

import java.util.ArrayList;

/**
 * Created by booboomx on 17/4/23.
 */

public interface ICookSearchResultView {
     void onCookSearchLoadMoreSuccess(ArrayList<CookDetail> list);
     void onCookSearchLoadMoreFaile(String msg);
     void onCookSearchLoadMoreNoData();
}
