package com.example.lixy.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/5/2711:16
 */
public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("MyBroadCast","MyBroadCast" + intent.getAction());
        Toast.makeText(context,intent.getAction() + "",Toast.LENGTH_LONG).show();
    }
}
