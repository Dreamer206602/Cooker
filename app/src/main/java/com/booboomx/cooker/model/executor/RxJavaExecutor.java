package com.booboomx.cooker.model.executor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by booboomx on 17/4/20.
 */

public class RxJavaExecutor {

    private ThreadExecutor mThreadExecutor;
    private PostExecutorThread mPostExectorThread;



    private Subscription mSubscription= Subscriptions.empty();

    public RxJavaExecutor(ThreadExecutor threadExecutor, PostExecutorThread postExectorThread) {
        mThreadExecutor = threadExecutor;
        mPostExectorThread = postExectorThread;
    }

    public void execute(Observable observable, Subscriber subscriber){

        this.mSubscription=observable
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExectorThread.getScheduler())
                .subscribe(subscriber);

    }




}
