package com.example.lixy.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lixy.myapplication.R;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/2/2717:31
 */
public class HandlerDemoActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_send,btn_handlerthread;
    private Handler handler;
    private Handler threadHandler;
    private HandlerThread handlerThread;
    private MyReciver reciver;
    private FirstReciver firstReciver;
    private SecondReciver secondReciver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        LogD(Thread.currentThread().getId() + "");

        btn_send = findViewById(R.id.btn_send);
        btn_handlerthread = findViewById(R.id.btn_handlerthread);
        btn_send.setOnClickListener(this);
        btn_handlerthread.setOnClickListener(this);

        reciver = new MyReciver();
        LocalBroadcastManager.getInstance(this).registerReceiver(reciver,new IntentFilter("com.bcjm.test"));
        //registerReceiver(reciver,new IntentFilter("com.bcjm.test"));

        firstReciver = new FirstReciver();
        secondReciver = new SecondReciver();

        registerReceiver(secondReciver,new IntentFilter("com.bcjm.reciver"));
        registerReceiver(firstReciver,new IntentFilter("com.bcjm.reciver"));


        startThread();
        startHandlerThread();
    }

    private void startThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                LogD(Looper.myLooper().toString() + "");
                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        LogD("startThread handleMessage  msg.what===" + msg.what + " " + Thread.currentThread().getId());
                    }
                };
                Looper.loop();
                LogD("after Looper.quit()");
            }
        }).start();
    }

    private void startHandlerThread(){
        handlerThread = new HandlerThread("HandlerThread"){
            @Override
            protected void onLooperPrepared() {
                super.onLooperPrepared();
                LogD("startHandlerThread onLooperPrepared ");
            }
        };
        handlerThread.start();
        threadHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LogD("startHandlerThread handleMessage msg.what===" + msg.what + " " + Thread.currentThread().getId());
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                LogD(Thread.currentThread().getId() + "");
                Message message = handler.obtainMessage(100);
                handler.sendMessage(message);

                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.bcjm.test"));
                //sendBroadcast(new Intent("com.bcjm.test"));

                sendOrderedBroadcast(new Intent("com.bcjm.reciver"),null);
                break;
            case R.id.btn_handlerthread:
                LogD(Thread.currentThread().getId() + "");
                threadHandler.sendMessage(threadHandler.obtainMessage(101));
                threadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogD("post Runnable " + Thread.currentThread().getId() + "");
                    }
                });
                break;
        }
    }

    private class MyReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            showToast("HandlerDemoActivity:" + intent.getAction());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handlerThread != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                handlerThread.quitSafely();
            }else {
                handlerThread.quit();
            }
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(reciver);
        //unregisterReceiver(reciver);
        unregisterReceiver(firstReciver);
        unregisterReceiver(secondReciver);
    }

    public static class FirstReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("FirstReciver","onReceive");
            Bundle bundle = intent.getExtras();
            if (bundle == null){
                bundle = new Bundle();
            }
            bundle.putString("data","from first");
            //intent.putExtra("data","from first intent");
            setResultExtras(bundle);
            //abortBroadcast();
        }
    }

    public static class SecondReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("SecondReciver","onReceive");
            Log.d("SecondReciver",intent.getStringExtra("data") + "   " +getResultExtras(true).getString("data"));
        }
    }
}
