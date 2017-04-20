package com.booboomx.cooker.model.bean;

/**
 * Created by booboomx on 17/4/20.
 */

public class CategorySubscriberResultInfo {

    private String msg;
    private String retCode;
    private CategoryResultInfo result;

    public CategorySubscriberResultInfo(){

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

    public CategoryResultInfo getResult() {
        return result;
    }

    public void setResult(CategoryResultInfo result) {
        this.result = result;
    }




}
