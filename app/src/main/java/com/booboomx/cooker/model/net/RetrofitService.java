package com.booboomx.cooker.model.net;

import android.util.Log;

import com.booboomx.cooker.BuildConfig;
import com.booboomx.cooker.utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by booboomx on 17/4/20.
 */

public class RetrofitService {

    public static final String TAG=RetrofitService.class.getSimpleName();



    private static  RetrofitService Instance=null;

    public static RetrofitService getInstance(){

        if (Instance == null) {

            Instance=new RetrofitService();

        }
        return Instance;
    }

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    private RetrofitService(){

        if(BuildConfig.DEBUG){
            mOkHttpClient=new OkHttpClient();
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {

                    Log.i(TAG, "log: --->"+message);
                }
            });

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



            mOkHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();


            mRetrofit=new Retrofit.Builder()
                    .baseUrl(Constants.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();


        }else{

            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {

                    Log.i(TAG, "log: --->"+message);
                }
            });

            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            mOkHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();


            mRetrofit=new Retrofit.Builder()
                    .baseUrl(Constants.baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();

        }
    }

    public <T> T createApi(Class<T>clazz){
        return mRetrofit.create(clazz);
    }
}
