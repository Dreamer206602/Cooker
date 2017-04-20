package com.booboomx.cooker.model.executor;

import rx.Scheduler;

/**
 * Created by booboomx on 17/4/20.
 */

public interface PostExecutorThread {

    Scheduler getScheduler();
}
