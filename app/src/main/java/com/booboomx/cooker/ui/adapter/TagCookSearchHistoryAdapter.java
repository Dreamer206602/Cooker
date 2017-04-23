package com.booboomx.cooker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.booboomx.cooker.R;
import com.booboomx.cooker.model.bean.CookSearchHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booboomx on 17/4/23.
 */

public class TagCookSearchHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CookSearchHistory>datas;

    public TagCookSearchHistoryAdapter(Context context, List<CookSearchHistory> datas) {
        mContext = context;
        this.datas = new ArrayList<>();

        for (CookSearchHistory item:datas){
            this.datas.add(item);
        }
        if(datas.size()>0){
            this.datas.add(new CookSearchHistory("清除历史"));
        }
    }


    public void setDatas(List<CookSearchHistory> datas){
        this.datas = datas;
    }

    public void clean(){
        this.datas.clear();
    }
    @Override
    public int getCount() {
        return datas.size()>0?datas.size():0;
    }

    @Override
    public CookSearchHistory getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
             convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tagview_cook_search_history, null);

            holder=new ViewHolder();
            holder.mButton= (Button) convertView.findViewById(R.id.tag_btn);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.mButton.setTag(getItem(position));
        return convertView;
    }


    public class ViewHolder {
        Button mButton;
    }
}
