package com.booboomx.cooker.IView;

import com.booboomx.cooker.model.bean.CookDetail;

import java.util.ArrayList;

/**
 * Created by booboomx on 17/4/23.
 */

public interface ICookSearchView {

     void onCookSearchSuccess(ArrayList<CookDetail> list, int totalPages);
     void onCookSearchFaile(String msg);
     void onCookSearchEmpty();
}
