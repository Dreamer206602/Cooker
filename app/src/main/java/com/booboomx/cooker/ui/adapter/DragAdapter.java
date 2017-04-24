package com.booboomx.cooker.ui.adapter;

import android.content.Context;
import android.util.Log;
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

public class DragAdapter extends BaseAdapter {

    public static final String TAG = DragAdapter.class.getSimpleName();
    private boolean isItemShow = false;
    private Context mContext;
    private int holdposition;
    private boolean isChanged = false;
    private boolean isVisible = true;
    public List<ChannelItem> mChannelItemList;
    private TextView item_text;
    public int removw_position = -1;


    public DragAdapter(Context context, List<ChannelItem> channelItems) {
        this.mContext = context;
        this.mChannelItemList = channelItems;
    }


    @Override
    public int getCount() {
        return mChannelItemList.size() > 0 ? mChannelItemList.size() : 0;
    }

    @Override
    public ChannelItem getItem(int position) {
        if (mChannelItemList != null && mChannelItemList.size() > 0) {
            return mChannelItemList.get(position);

        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.subscribe_category_item, null);

        item_text = (TextView) view.findViewById(R.id.text_item);

        ChannelItem channel = getItem(position);
        item_text.setText(channel.getName());

        if ((position == 0) || (position == 1)) {
            item_text.setEnabled(false);
        }

        if (isChanged && (position == holdposition) && !isItemShow) {
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
            isChanged = false;
        }

        if (!isVisible && (position == -1 + mChannelItemList.size())) {
            item_text.setText("");
            item_text.setSelected(true);
            item_text.setEnabled(true);
        }

        if(removw_position==position){
            item_text.setText("");
        }

        return view;
    }

    public void  addItem(ChannelItem channelItem){
        mChannelItemList.add(channelItem);
        notifyDataSetChanged();
    }

    public void exchange(int dragPosition,int dropPosition){

        holdposition=dropPosition;
        ChannelItem dragItem = getItem((dragPosition));
        Log.d(TAG, "startPostion=" + dragPosition + ";endPosition=" + dropPosition);


        if(dragPosition<dropPosition){
            mChannelItemList.add(dropPosition+1,dragItem);
            mChannelItemList.remove(dragPosition);

        }else{
            mChannelItemList.add(dropPosition,dragItem);
            mChannelItemList.remove(dragPosition+1);
        }

        isChanged=true;
        notifyDataSetChanged();

    }

    public void setRemove(int position){
        removw_position=position;
        notifyDataSetChanged();

    }

    public void remove(){
        mChannelItemList.remove(removw_position);
        removw_position=-1;
        notifyDataSetChanged();
    }


    public boolean isVisible(){
        return isVisible;
    }

    public void setVisible(boolean visible){
        isVisible=visible;
    }
    public void setShowDropItem(boolean show){
        isItemShow=show;
    }

}
