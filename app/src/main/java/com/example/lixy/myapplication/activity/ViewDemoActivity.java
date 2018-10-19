package com.example.lixy.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.lixy.myapplication.R;
import com.example.lixy.myapplication.view.CustomView;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/3/517:51
 */
public class ViewDemoActivity extends BaseActivity {
    private CustomView customView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdemo);
        customView = findViewById(R.id.customView);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }
}
