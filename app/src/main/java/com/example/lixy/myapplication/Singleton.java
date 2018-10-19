package com.example.lixy.myapplication;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/3/816:20
 */
public abstract class Singleton<T> {
    private T mSingleton;
    protected abstract T create();
    public final T get(){
        synchronized (this){
            if (mSingleton == null){
                mSingleton = create();
            }
        }
        return mSingleton;
    }
}
