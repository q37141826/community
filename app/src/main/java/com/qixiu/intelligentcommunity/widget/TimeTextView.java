package com.qixiu.intelligentcommunity.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.utlis.TimeUtility;


/**
 * @Author：XieXianyong
 * @Date：2016-11-19 下午5:30:18
 * @Decription 显示时间textview(如:11月19日 4:30 今天 4:30)
 */
public class TimeTextView extends TextView {

    public TimeTextView(Context context) {
        super(context);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTime(long mills) {
        String time = TimeUtility.getListTime(mills);
        if (!getText().toString().equals(time)) {
            setText(time);
        }
    }
}
