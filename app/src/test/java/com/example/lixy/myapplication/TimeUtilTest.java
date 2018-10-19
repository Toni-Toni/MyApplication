package com.example.lixy.myapplication;

import org.junit.Test;

import java.util.Date;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/1/311:34
 */
public class TimeUtilTest {
    @Test
    public void setTimeText() throws Exception {
        Date date = new Date();
        long distance = 1000 * 60 * 60 * 24 * 3;
        long time = date.getTime() - distance;
        long timeup = time - distance;
        new TimeUtil().setTimeText(time,timeup);
    }
}