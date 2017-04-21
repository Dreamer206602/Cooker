package com.booboomx.cooker.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.model.bean.CookDetail;
import com.booboomx.cooker.ui.adapter.base.BaseRecyclerAdapter;
import com.booboomx.cooker.ui.adapter.base.CommonHolder;
import com.booboomx.cooker.utils.GlideUtil;

import butterknife.Bind;

/**
 * Created by booboomx on 17/4/21.
 */

public class CookListAdapter extends BaseRecyclerAdapter<CookDetail>{

    private Activity mActivity;
    private GlideUtil mGlideUtil;
    public CookListAdapter(Activity activity){
        this.mActivity=activity;
        mGlideUtil=new GlideUtil();
    }


    public class  CardHolder extends CommonHolder<CookDetail>{

        @Bind(R.id.img_cook)
        ImageView imagCook;

        @Bind(R.id.text_name)
        TextView textName;

        @Bind(R.id.cardView)
        CardView mCardView;


        public CardHolder(Context context, ViewGroup root) {
            super(context, root, R.layout.item_cook_list);
        }

        @Override
        public void bindData(CookDetail cook) {

            if(cook.getThumbnail()!=null)
                mGlideUtil.attach(imagCook).injectImageWithNull(cook.getThumbnail());

            textName.setText(cook.getName());


            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });




        }
    }


    @Override
    public CommonHolder<CookDetail> setViewHolder(ViewGroup parent) {
        return new CardHolder(parent.getContext(),parent);
    }
}
