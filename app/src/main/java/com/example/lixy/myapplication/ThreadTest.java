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

}
