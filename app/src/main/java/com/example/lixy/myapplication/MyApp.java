package com.example.lixy.myapplication;

import android.app.Application;
import android.util.Log;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/12/2610:09
 */
public class MyApp extends Application {
    private static MyApp application;
    private int var = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApp", "Application onCreate var:" + var + " pid:" + android.os.Process.myPid() + " uid:" + android.os.Process.myUid());
        application = this;
        var = 100;
    }

    public static MyApp getApplication() {
        return application;
    }
}
