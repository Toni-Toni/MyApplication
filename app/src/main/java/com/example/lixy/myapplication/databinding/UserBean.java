package com.example.lixy.myapplication.databinding;

import android.databinding.BaseObservable;

import com.android.databinding.library.baseAdapters.BR;


/**
 * @version V1.0
 * Copyright©2018 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/9/2014:47
 */
public class UserBean extends BaseObservable{
    private String name;
    private String age;

    private boolean hide;

    public UserBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
        notifyPropertyChanged(BR.user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.user);
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.user);
    }
}
