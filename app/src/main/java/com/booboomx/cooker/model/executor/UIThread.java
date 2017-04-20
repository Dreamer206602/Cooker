package com.booboomx.cooker.model.executor;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by booboomx on 17/4/20.
 */

public class UIThread implements PostExecutorThread{
    private UIThread() {

    }

    public static UIThread instance() {
        return Holder.INSTANCE;
    }

    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    private final static class Holder {
        private static final UIThread INSTANCE = new UIThread();
    }
}

