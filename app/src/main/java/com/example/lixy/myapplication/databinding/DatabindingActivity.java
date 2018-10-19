package com.example.lixy.myapplication.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.example.lixy.myapplication.R;
import com.example.lixy.myapplication.activity.BaseActivity;

/**
 * @version V1.0
 * Copyright©2018 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/9/2014:49
 */
public class DatabindingActivity extends BaseActivity {

    private com.example.lixy.myapplication.databinding.ActivityDatabindingBinding binding;
    UserBean userBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_databinding);

        userBean = new UserBean("Closa James","15");
        binding.setUser(userBean);

        binding.setVariable(BR.act, this);

        LogD(getIntent().getData().getFragment());

    }

    public void onClick(View view){
        userBean.setHide(view.getVisibility() == View.VISIBLE);
    }
}
