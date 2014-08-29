package com.soap.chh.app;

import android.app.Application;

import com.soap.chh.io.RequestManager;

/**
 * Created by yc on 14-8-29.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.init(this);
    }
}
