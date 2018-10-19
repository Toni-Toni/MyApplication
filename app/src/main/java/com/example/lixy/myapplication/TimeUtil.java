package com.example.lixy.myapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/1/311:22
 */
public class TimeUtil {

    public void setTimeText(long time,long timeup) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long resultlong = time - timeup;
            if (resultlong <= 200000) {
                System.out.println("隐藏");
            } else {
                System.out.println("显示");

                Calendar today = Calendar.getInstance();
                today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH),0,0,0);// 今天零点
                System.out.println("today:" + simpleDateFormat.format(new Date(today.getTimeInMillis())));

                Calendar yestoday = Calendar.getInstance();
                yestoday.add(Calendar.DATE, -1);// 昨天零点
                yestoday.set(yestoday.get(Calendar.YEAR), yestoday.get(Calendar.MONTH),yestoday.get(Calendar.DAY_OF_MONTH),0,0,0);// 今天零点
                System.out.println("yestoday:" + simpleDateFormat.format(new Date(yestoday.getTimeInMillis())));

                Calendar week = Calendar.getInstance();
                week.add(Calendar.DATE, -week.get(Calendar.DAY_OF_WEEK) + 1);// 上周零点
                week.set(week.get(Calendar.YEAR), week.get(Calendar.MONTH),week.get(Calendar.DAY_OF_MONTH),0,0,0);
                System.out.println("week:" + simpleDateFormat.format(new Date(week.getTimeInMillis())));

                Date d = new Date(time);
                if (time >= today.getTimeInMillis())// 今天
                {
                    SimpleDateFormat myFmt1 = new SimpleDateFormat("HH:mm");
                    System.out.println("" + myFmt1.format(d));
                } else if (time >= yestoday.getTimeInMillis()) {
                    SimpleDateFormat myFmt1 = new SimpleDateFormat("HH:mm");
                    System.out.println("昨天 " + myFmt1.format(d));
                } else if (time >= week.getTimeInMillis()) {
                    SimpleDateFormat myFmt1 = new SimpleDateFormat("E HH:mm");
                    System.out.println(myFmt1.format(d));
                } else {
                    SimpleDateFormat myFmt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    System.out.println(myFmt1.format(d));
                }
            }
        }
}
