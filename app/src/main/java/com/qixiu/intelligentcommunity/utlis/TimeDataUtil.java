package com.qixiu.intelligentcommunity.utlis;

import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuiHeZe on 2017/8/25.
 */

public class TimeDataUtil {
    public static String getDate(long addTime) {
        String time = "";
        Time t = new Time();
        t.set(System.currentTimeMillis() + addTime);
        int year = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int minute = t.minute;
        int hour = t.hour;
        String months = month + "";
        String days = day + "";
        if (month < 10) {
            months = 0 + "" + months;
        }
        if (day < 10) {
            days = 0 + "" + day;
        }
        time = "" + year +"/"+ months+"/" + days;
        Log.e("time",System.currentTimeMillis()+"添加的时间"+addTime+"最后解析为"+time);
        return time;
    }


    public static String getTime(long addTime) {
        String time = "";
        Time t = new Time();
        t.set(System.currentTimeMillis() + addTime);
        int year = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int minute = t.minute;
        int hour = t.hour;
        String months = month + "";
        String days = day + "";
        if (month < 10) {
            months = 0 + "" + months;
        }
        if (day < 10) {
            days = 0 + "" + day;
        }
        if (hour < 10) {
            time = "" + "0" + hour + ":00";
        } else {
            time = "" + hour + ":00";
        }
        return time + "";
    }

    public static List<String> getDataList(int days) {
        List<String> list = new ArrayList<>();
        for (long i = 0; i < days; i++) {
            String date = getDate(i * 24 * 3600 * 1000);
            list.add(date);
        }
        return list;
    }

    public static List<String> getTimeList(int times) {
        List<String> list = new ArrayList<>();
        for (long i = 0; i < times; i++) {
            String date = getTime(i * 3600 * 1000);
            list.add(date);
        }
        return list;
    }

    public static List<String> getTimtArea(int now) {
        if(now>11){
            return null;
        }
        List<String> list = new ArrayList<>();
        String time[] = {"9:00-10:00", "10:00-11:00", "11:00-12:00", "12:00-13:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00", "17:00-18:00", "18:00-19:00", "19:00-20:00"};
        for (int i = now; i < time.length; i++) {
            list.add(time[i]);
        }
        return list;
    }

    public static int getTime() {
        int timeAreas=0;
        Time time = new Time();
        time.setToNow();
        if(time.hour>9&&time.hour<20){
            timeAreas=time.hour-9;
        }
        return timeAreas;
    }

    public static boolean  isToday(String time){
        String date = getDate(0);
        if((date+"").equals(time)){
            return true;
        }
        return false;
    }

    public static final int BEFORE_SECTION=001,AFTER_SECTION=002,DURING_SECTION=003;
    public static int getTimeSection(int before,int after) {
        int timeAreas=0;
        Time time = new Time();
        time.setToNow();
        if(time.hour<before){
         return  BEFORE_SECTION;
        }else if(time.hour>after){
            return AFTER_SECTION;
        }else {
            return DURING_SECTION;
        }
    }

    public  int getYear(){
        String time = "";
        Time t = new Time();
        t.set(System.currentTimeMillis() );
        int year = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int minute = t.minute;
        int hour = t.hour;
        return year;
    }
    public  int getMonth(){
        String time = "";
        Time t = new Time();
        t.set(System.currentTimeMillis() );
        int year = t.year;
        int month = t.month + 1;
        int day = t.monthDay;
        int minute = t.minute;
        int hour = t.hour;
        return month;
    }


}
