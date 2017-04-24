package com.booboomx.cooker.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.booboomx.cooker.R;
import com.booboomx.cooker.base.BaseActivity;
import com.booboomx.cooker.component.tagComponent.ChannelItem;
import com.booboomx.cooker.component.tagComponent.ChannelManage;
import com.booboomx.cooker.component.tagComponent.DragGrid;
import com.booboomx.cooker.component.tagComponent.OtherGridView;
import com.booboomx.cooker.manager.CustomCategoryManager;
import com.booboomx.cooker.presenter.Presenter;
import com.booboomx.cooker.ui.adapter.DragAdapter;
import com.booboomx.cooker.ui.adapter.OtherAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class CookChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.userGridView)
    public DragGrid userGridView;
    @Bind(R.id.otherGridView)
    public OtherGridView otherGridView;

    private DragAdapter userAdapter;
    private OtherAdapter otherAdapter;

    public ArrayList<ChannelItem> otherChannelList = new ArrayList<>();
    public ArrayList<ChannelItem> userChannelList = new ArrayList<>();


    private boolean isMove = false;
    private boolean isDataChanged = false;


    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        userChannelList = (ArrayList<ChannelItem>) ChannelManage.getManage().getUserChannel();
        otherChannelList = (ArrayList<ChannelItem>) ChannelManage.getManage().getOtherChannel();


        userAdapter = new DragAdapter(this, userChannelList);
        userGridView.setAdapter(userAdapter);

        otherAdapter = new OtherAdapter(this, otherChannelList);
        otherGridView.setAdapter(otherAdapter);


        otherGridView.setOnItemClickListener(this);
        userGridView.setOnItemClickListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_channel;
    }


    public final static int Request_Code_Channel = 30456;
    public final static int Result_Code_Channel_Changed = 30457;
    public final static int Result_Code_Channel_NoChanged = 30457;


    public static void startActivity(Activity activity) {

        activity.startActivityForResult(new Intent(activity,
                        CookChannelActivity.class), Request_Code_Channel,
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()
        );

    }


    @Override
    protected void onPause() {
        super.onPause();
        CustomCategoryManager.getInstance().save(
                ChannelManage.getManage().getDefaultUserChannels(),
                ChannelManage.getManage().getDefaultOtherChannels()
        );
    }

    @OnClick(R.id.imgv_back)
    public void onClickBack(){
        if(isDataChanged) {
            setResult(Result_Code_Channel_Changed, new Intent());
        }
        else{
            setResult(Result_Code_Channel_NoChanged, new Intent());
        }
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        if (isMove) {
            return;
        }

        switch (parent.getId()) {
            case R.id.userGridView:

                isDataChanged = true;
                if (position != 0 && position != 1) {

                    final ImageView moveImageView = getView(view);
                    if (moveImageView != null) {

                        TextView newTextView = (TextView) view.findViewById(R.id.text_item);


                        final int[] startLocation = new int[2];
                        newTextView.getLocationInWindow(startLocation);


                        final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);


                        otherAdapter.setVisible(false);
                        otherAdapter.addItem(channel);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    int[] endLocation = new int[2];

                                    otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                    MoveAnim(moveImageView, startLocation, endLocation, channel, userGridView);
                                    userAdapter.setRemove(position);

                                } catch (Exception e) {

                                }

                            }
                        }, 50L);
                    }
                }

                break;
            case R.id.otherGridView:
                isDataChanged=true;
                final ImageView moveImageView = getView(view);
                if (moveImageView != null) {

                    TextView newTextView = (TextView) view.findViewById(R.id.text_item);


                    final int[] startLocation=new int[2];
                    newTextView.getLocationInWindow(startLocation);


                    final ChannelItem channel =((OtherAdapter) parent.getAdapter()).getItem(position);


                    userAdapter.setVisible(false);
                    userAdapter.addItem(channel);


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try{
                                int[] endLocation = new int[2];

                                userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
                                MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);

                                otherAdapter.setRemove(position);


                            }catch (Exception e){

                            }

                        }
                    },50L);
                }
                break;
        }
    }

    public void MoveAnim(View moveView, int[] startLocation, int[] endLocation,
                         ChannelItem channelItem, final GridView gridView) {
        int[] initLocation = new int[2];
        moveView.getLocationInWindow(initLocation);

        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);

        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0],
                endLocation[0],
                startLocation[1],
                endLocation[1]
        );
        moveAnimation.setDuration(300L);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setFillAfter(false);
        animationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                moveViewGroup.removeView(mMoveView);

                if (gridView instanceof DragGrid) {
                    otherAdapter.setVisible(true);
                    otherAdapter.notifyDataSetChanged();
                    userAdapter.remove();
                } else {
                    userAdapter.setVisible(true);
                    userAdapter.notifyDataSetChanged();
                    otherAdapter.remove();
                }

                isMove = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public View getMoveView(ViewGroup viewGroup, View view, int[] initPosition) {
        int x = initPosition[0];
        int y = initPosition[1];

        viewGroup.addView(view);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;

        view.setLayoutParams(layoutParams);

        return view;
    }

    public ViewGroup getMoveViewGroup() {

        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();

        LinearLayout linearLayout = new LinearLayout(this);

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        moveViewGroup.addView(linearLayout);
        return linearLayout;

    }


    public ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmap);
        return imageView;

    }
}
