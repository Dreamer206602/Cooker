package com.booboomx.cooker.model.bean;

import java.util.ArrayList;

/**
 * Created by booboomx on 17/4/20.
 */

public class CategoryResultInfo {

    private CategoryInfo categoryInfo;
    private ArrayList<CategoryChildInfo1> childs;

    public CategoryResultInfo(){

    }

    public CategoryInfo getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public ArrayList<CategoryChildInfo1> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<CategoryChildInfo1> childs) {
        this.childs = childs;
    }



}
