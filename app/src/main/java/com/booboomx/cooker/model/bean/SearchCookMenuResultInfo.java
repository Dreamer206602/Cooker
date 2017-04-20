package com.booboomx.cooker.model.bean;

import java.util.ArrayList;

/**
 * Created by booboomx on 17/4/20.
 */

public class SearchCookMenuResultInfo {
    private int curPage;
    private int total;
    private ArrayList<CookDetail> list;

    public SearchCookMenuResultInfo(){

    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CookDetail> getList() {
        return list;
    }

    public void setList(ArrayList<CookDetail> list) {
        this.list = list;
    }
}
