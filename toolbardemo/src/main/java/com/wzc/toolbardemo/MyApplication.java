package com.wzc.toolbardemo;


import android.app.Application;

/**
 * Created by wzc on 2017/8/28.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
