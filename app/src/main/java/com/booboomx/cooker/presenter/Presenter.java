package com.booboomx.cooker.presenter;

import android.content.Context;

import com.booboomx.cooker.model.executor.JobExecutor;
import com.booboomx.cooker.model.executor.RxJavaExecutor;
import com.booboomx.cooker.model.executor.UIThread;

/**
 * Created by booboomx on 17/4/20.
 */

public abstract class Presenter {
    public Context mContext;
    public RxJavaExecutor mRxJavaExecutor;


    public Presenter(Context context) {
        this.mContext = context;
        this.mRxJavaExecutor = new RxJavaExecutor(JobExecutor.instance(), UIThread.instance());
    }



    public abstract  void destroy();

}
