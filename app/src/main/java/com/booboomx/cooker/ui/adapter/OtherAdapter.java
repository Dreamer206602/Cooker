package com.booboomx.cooker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.component.tagComponent.ChannelItem;

import java.util.List;

/**
 * Created by booboomx on 17/4/24.
 */

public class OtherAdapter extends BaseAdapter {
    private Context mContext;
    private List<ChannelItem>mChannelItems;
    private TextView item_text;
    private  boolean isVisible=true;
    private int remove_position=-1;

    public OtherAdapter(Context context,List<ChannelItem> channelItems){
        this.mContext=context;
        this.mChannelItems=channelItems;
    }

    @Override
    public int getCount() {
        return mChannelItems.size()>0?mChannelItems.size():0;
    }

    @Override
    public ChannelItem getItem(int position) {
        return mChannelItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.subscribe_category_item,null);
        item_text= (TextView) view.findViewById(R.id.text_item);

        ChannelItem channelItem = getItem(position);
        item_text.setText(channelItem.getName());

        if(!isVisible&&(position==mChannelItems.size()-1)){
            item_text.setText("");
        }
        if(remove_position==position){
            item_text.setText("");
        }

        return view;
    }

    public void addItem(ChannelItem channelItem){
        mChannelItems.add(channelItem);
        notifyDataSetChanged();
    }

    public void  setRemove(int position){

        remove_position=position;
        notifyDataSetChanged();
    }

    public void remove(){
        mChannelItems.remove(remove_position);
        remove_position=-1;
        notifyDataSetChanged();
    }

    public boolean isVisible(){
        return isVisible;
    }

    public void setVisible(boolean visible){
        isVisible=visible;
    }
}
