package com.example.lixy.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/8/2216:50
 */
public class ThreadTest {
    private String LOG = ThreadTest.class.getSimpleName();
    private byte[] lock = new byte[0];

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    volatile private List<Integer>  list = new ArrayList<>();

    public synchronized void get() {
        for (int i = 0; i < list.size(); i++) {
            Log.d(LOG, "get:" + list.get(i));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void add() {
        synchronized (lock) {
            for (int i = 0; i < 50; i++) {
                Log.d(LOG, "add:" + i);
                list.add(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long threadLocal(){

        threadLocal.set(System.currentTimeMillis());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (threadLocal.get() == null){
                        threadLocal.set(10L);
                    }else {
                        threadLocal.set(threadLocal.get() + 1);
                    }
                    System.out.println("Thread1==" + threadLocal.get());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    if (threadLocal.get() == null){
                        threadLocal.set((long) i);
                    }else {
                        threadLocal.set(threadLocal.get() + 1);
                    }
                    System.out.println("Thread2==" + threadLocal.get());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (System.currentTimeMillis() - threadLocal.get()) / 1000;
    }

}
