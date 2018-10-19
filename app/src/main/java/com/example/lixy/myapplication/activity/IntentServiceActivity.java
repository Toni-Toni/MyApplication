package com.example.lixy.myapplication.activity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.lixy.myapplication.R;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/3/116:11
 */
public class IntentServiceActivity extends BaseActivity implements Handler.Callback {
    private TextView textView;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intentservice);

        textView = findViewById(R.id.textView);
        handler = new Handler(this);
        MyIntentService.setUIHandler(handler);

        for (int i = 0 ; i < 5; i ++) {
            Intent intent = new Intent(this,MyIntentService.class);
            intent.putExtra("data",String.valueOf(i));
            startService(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,MyIntentService.class));
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        textView.append("\n" + msg.obj.toString());
        return false;
    }

    public static class MyIntentService extends IntentService{
        private static Handler uiHandler;
        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         *
         */
        public MyIntentService() {
            super("MyIntentService");
        }

        public static void setUIHandler(Handler handler){
            uiHandler = handler;
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            try {
                String data = intent.getStringExtra("data");
                Log.d("MyIntentService","onHandleIntent:" + data);
                Thread.sleep(8000);
                Message message = uiHandler.obtainMessage();
                message.obj = data;
                uiHandler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCreate() {
            super.onCreate();
            Log.d("MyIntentService","onCreate");
        }

        @Override
        public void onStart(@Nullable Intent intent, int startId) {
            super.onStart(intent, startId);
            Log.d("MyIntentService","onStart:" + startId);
        }

        @Override
        public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
            Log.d("MyIntentService","onStartCommand:" + startId);
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("MyIntentService","onDestroy");
        }
    }
}
