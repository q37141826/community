package com.qixiu.intelligentcommunity.mvp.beans.home;

import java.util.ArrayList;
import java.util.List;

public class PayTimeDataHelper {

    public static final String PAYTIME_ONE_YEAR = "一年（12个月）";
    public static final String PAYTIME_TWO_YEAR = "两年（24个月）";
    public static final String PAYTIME_THREE_YEAR = "三年（36个月）";
    public static final String PAYTIME_FOUR_YEAR = "四年（48个月）";
    public static final String PAYTIME_FIVE_YEAR = "五年（60个月）";


    public static List<String> getTimeList() {
        List<String> datas = new ArrayList<>();
        datas.add(PAYTIME_ONE_YEAR);
        datas.add(PAYTIME_TWO_YEAR);
        datas.add(PAYTIME_THREE_YEAR);
        datas.add(PAYTIME_FOUR_YEAR);
        datas.add(PAYTIME_FIVE_YEAR);
        return datas;
    }

    public static int getMonths(String time) {
        if(time.equals("PAYTIME_ONE_YEAR")){
            return 12;
        }
        if(time.equals("PAYTIME_TWO_YEAR")){
            return 24;
        }
        if(time.equals("PAYTIME_THREE_YEAR")){
            return 36;
        }
        if(time.equals("PAYTIME_FOUR_YEAR")){
            return 48;
        }
        if(time.equals("PAYTIME_FIVE_YEAR")){
            return 60;
        }
        return 12;
    }



}
