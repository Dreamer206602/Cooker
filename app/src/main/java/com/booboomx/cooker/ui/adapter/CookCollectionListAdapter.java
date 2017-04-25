package com.booboomx.cooker.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.manager.CookCollectionManager;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.model.bean.TB_CookDetail;
import com.booboomx.cooker.ui.adapter.base.BaseRecyclerAdapter;
import com.booboomx.cooker.ui.adapter.base.CommonHolder;
import com.booboomx.cooker.utils.GlideUtil;

import butterknife.Bind;

/**
 * Created by booboomx on 17/4/25.
 */

public class CookCollectionListAdapter extends BaseRecyclerAdapter<TB_CookDetail> {

    private GlideUtil glideUtil;

    public CookCollectionListAdapter(OnCookCollectionListListener onCookCollectionListListener){
        this.onCookCollectionListListener =onCookCollectionListListener;
        glideUtil = new GlideUtil();
    }

    @Override
    public CommonHolder<TB_CookDetail> setViewHolder(ViewGroup parent) {
        return new CookDetailHolder(parent.getContext(), parent);
    }

    class CookDetailHolder extends CommonHolder<TB_CookDetail> {

        @Bind(R.id.imgv_src)
        public ImageView imgvSrc;
        @Bind(R.id.text_name)
        public TextView textName;
        @Bind(R.id.text_tag)
        public TextView textTag;
        @Bind(R.id.relative_more)
        public RelativeLayout relativeMore;

        public CookDetailHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_collection_list);
        }

        @Override
        public void bindData(final TB_CookDetail cook) {
            textName.setText(cook.getName());
            textTag.setText(cook.getCtgTitles());

            if(cook.getThumbnail() != null && (!TextUtils.isEmpty(cook.getThumbnail()))){
                glideUtil.attach(imgvSrc).injectImageWithNull(cook.getThumbnail());
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onCookCollectionListListener != null)
                        onCookCollectionListListener.onCookCollectionListClick(imgvSrc, CookCollectionManager.getInstance().tb2CookDetail(cook));
                }
            });

            relativeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onCookCollectionListListener != null)
                        onCookCollectionListListener.onCookCollectionListMore(cook);
                }
            });

        }

    }

    private OnCookCollectionListListener onCookCollectionListListener;
    public interface OnCookCollectionListListener{
        public void onCookCollectionListClick(View view, CookDetail cook);
        public void onCookCollectionListMore(TB_CookDetail cook);
    }
}
