package com.teamfingo.android.fingo.utils;

import android.app.Application;
import android.util.Log;

import com.teamfingo.android.fingo.interfaces.FingoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jaemin on 2016. 12. 9..
 */

public class AppController extends Application { // 앱이 실행되면 무조건 초기화 됨

    private static String FINGO_BASE_URL = "http://fingo-dev.ap-northeast-2.elasticbeanstalk.com/";

    private static Retrofit mRetrofit;
    private static FingoPreferences mFingoPreferences;
    private static String mToken;
    private static FingoService mService;

    @Override
    public void onCreate() {
        super.onCreate();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(FINGO_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mFingoPreferences = new FingoPreferences(this);
        mToken = mFingoPreferences.getAccessToken();

        mService = mRetrofit.create(FingoService.class);

    }

    public static FingoService getFingoService() {
        return mService;
    }

    public static String getToken() {
        Log.e("log","AppController / getToken() ==== " + mToken);

        return mToken;
    }

}