package com.example.lixy.myapplication;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2016/11/1420:25
 */
public class Utils {
    private final static String TAG = "Utils";
    private static Utils utils;
    public static Utils getInstance(Context context){
        if (utils == null){
            utils = new Utils();
        }
        return utils;
    }

    public String getIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = telephonyManager.getDeviceId();
        Log.d(TAG,"IMEI=========" + IMEI);
        return IMEI;
    }

   public String  getMac(Context context){
       WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
       String mac = wifiManager.getConnectionInfo().getMacAddress();
       Log.d(TAG,"mac=========" + mac);
       return mac;
   }

    @NonNull public static String notNull(@NonNull String s, String s2){
        Log.d(TAG,s.length() + "  " + s2.length());
        return null;
    }
}
