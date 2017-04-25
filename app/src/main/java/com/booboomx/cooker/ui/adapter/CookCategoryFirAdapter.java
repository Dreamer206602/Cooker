package com.booboomx.cooker.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.app.BaseApplication;
import com.booboomx.cooker.model.bean.CategoryInfo;
import com.booboomx.cooker.ui.adapter.base.BaseRecyclerAdapter;
import com.booboomx.cooker.ui.adapter.base.CommonHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by booboomx on 17/4/25.
 */

public class CookCategoryFirAdapter<T> extends BaseRecyclerAdapter<CookCategoryFirAdapter.CookCategoryFirStruct>{


    public CookCategoryFirAdapter(OnCookCategoryFirListener listener){
        this.mOnCookCategoryFirListener=listener;
    }
    @Override
    public CommonHolder<CookCategoryFirStruct> setViewHolder(ViewGroup parent) {
        return new CookCategoryFirHolder(parent.getContext(),parent);
    }

    public class  CookCategoryFirHolder extends CommonHolder<CookCategoryFirStruct>{

        @Bind(R.id.ralative_bg)
        public RelativeLayout relativeBg;
        @Bind(R.id.text_select)
        public TextView textSelect;
        @Bind(R.id.text_category)
        public TextView textCategory;

        public CookCategoryFirHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_category_fir);
        }

        @Override
        public void bindData(final CookCategoryFirStruct cook) {

            if(cook.isSelect){
                textSelect.setVisibility(View.VISIBLE);
                relativeBg.setBackgroundColor(BaseApplication.getContext().getResources().getColor(R.color.white));
            }else{
                textSelect.setVisibility(View.GONE);
                relativeBg.setBackgroundColor(BaseApplication.getContext().getResources().getColor(R.color.black_alpha_light));
            }


            textCategory.setText(getCategoryName(cook.data.getName()));


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = cook.data.getCtgId();
                    if (mOnCookCategoryFirListener != null) {
                        mOnCookCategoryFirListener.onCategoryFirClick(id);
                    }


                    for(CookCategoryFirStruct item:dataList){

                       if(item.data.getCtgId().equals(id)){
                            item.isSelect=true;
                        }else{
                            item.isSelect=false;
                        }

                    }

                    notifyDataSetChanged();

                }
            });

        }
    }

    private OnCookCategoryFirListener mOnCookCategoryFirListener;
    public interface OnCookCategoryFirListener{
        void onCategoryFirClick(String ctgId);
    }

    public static String getCategoryName(String name){
        return name.replace("按","").replace("选择菜谱","");
    }



    public static class  CookCategoryFirStruct{
        private CategoryInfo data;
        private boolean isSelect;

        public CookCategoryFirStruct(CategoryInfo data){
            this.data=data;
            this.isSelect=false;
        }
    }

    public static List<CookCategoryFirStruct>createDatas(List<CategoryInfo>datas){
        List<CookCategoryFirStruct>dstDatas=new ArrayList<>();
        for (CategoryInfo item:datas){
            dstDatas.add(new CookCategoryFirStruct(item));
        }

        if(dstDatas.size()>0){
            dstDatas.get(0).isSelect=true;
        }

        return dstDatas;
    }


}
