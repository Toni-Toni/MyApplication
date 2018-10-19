package com.example.lixy.myapplication.activity;

import android.app.Fragment;
import android.util.Log;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/3/214:33
 */
public class BaseFragment extends Fragment {
    protected final String TAG = BaseFragment.this.getClass().getSimpleName();


    protected void logD(String msg){
        Log.d(TAG,msg);
    }
}
