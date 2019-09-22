package com.qixiu.intelligentcommunity.utlis.base64;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/7 0007.
 */

public class DateFormatUtils {

    public static Date parsebyStringTag(String paresTag, String paresSource) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(paresTag);
        return simpleDateFormat.parse(paresSource);
    }

    public static int compareCurentDatePreciseToDay(String paresTag, String paresSource) throws Exception {
        Date sourceDate = parsebyStringTag(paresTag, paresSource);
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        String monthString = "";
        String dayString = "";

        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = month + "";
        }
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = day + "";
        }
        String currentData = year + "." + monthString + "." + dayString;
        Date currentDate = parsebyStringTag(paresTag, currentData);
        if (sourceDate.getTime() < currentDate.getTime()) {
            return -1;
        } else if (sourceDate.getTime() == currentDate.getTime()) {
            return 0;
        } else {
            return 1;
        }
    }
}
