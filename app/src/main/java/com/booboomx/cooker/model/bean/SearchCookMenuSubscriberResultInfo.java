package com.booboomx.cooker.model.bean;

/**
 * Created by booboomx on 17/4/20.
 */

public class SearchCookMenuSubscriberResultInfo {
    private String msg;
    private String retCode;
    private SearchCookMenuResultInfo result;

    public SearchCookMenuSubscriberResultInfo(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public SearchCookMenuResultInfo getResult() {
        return result;
    }

    public void setResult(SearchCookMenuResultInfo result) {
        this.result = result;
    }
}