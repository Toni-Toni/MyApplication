package com.example.lixy.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/2/2717:48
 */
public class BaseActivity extends AppCompatActivity {

    protected final String TAG = BaseActivity.this.getClass().getSimpleName();

    private final int index = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    protected void LogE(String msg){
        Log.e(TAG,TAG + ">>>>>" + msg);
    }

    protected void LogD(String msg){
        Log.d(TAG,TAG + ">>>>>" + msg);
    }

    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogD(Thread.currentThread().getStackTrace()[index].getMethodName());
    }
}
