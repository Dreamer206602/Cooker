package com.booboomx.cooker.model.bean;

/**
 * Created by booboomx on 17/4/23.
 */

public class CookSearchHistory {
    private String name;

    public CookSearchHistory(){

    }

    public CookSearchHistory(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
