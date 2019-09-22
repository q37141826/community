package com.qixiu.intelligentcommunity.utlis.picker;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.utlis.TimeDataUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by HuiHeZe on 2017/9/11.
 */

public class MyDataPicker extends WheelPicker {

    /**
     * 字体颜色，横线颜色
     */

    /**
     * 年月日
     */
    public static final int YEAR_MONTH_DAY = 0;
    /**
     * 年月
     */
    public static final int YEAR_MONTH = 1;
    /**
     * 月日
     */
    public static final int MONTH_DAY = 2;
    private ArrayList<String> years = new ArrayList<String>();
    private ArrayList<String> months = new ArrayList<String>();
    private ArrayList<String> days = new ArrayList<String>();


    private ArrayList<String> times = new ArrayList<String>();
    private MyDataPicker.OnDatePickListener onDatePickListener;
    private String yearLabel = "", monthLabel = "", dayLabel = "";
    private int selectedYearIndex = 0, selectedMonthIndex = 0, selectedDayIndex = 0, selectedTimeIndex = 0;
    private int mode = YEAR_MONTH_DAY;
    private Context context;

    /**
     * 安卓开发应避免使用枚举类（enum），因为相比于静态常量enum会花费两倍以上的内存。
     *
     * @link http ://developer.android.com/training/articles/memory.html#Overhead
     */
    @IntDef(flag = false, value = {YEAR_MONTH_DAY, YEAR_MONTH, MONTH_DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    /**
     * Instantiates a new Date picker.
     *
     * @param activity the activity
     */
    public MyDataPicker(Activity activity) {
        this(activity, YEAR_MONTH_DAY);
        context = activity;
    }

    /**
     * Instantiates a new Date picker.
     *
     * @param activity the activity
     * @param mode     the mode
     * @see #YEAR_MONTH_DAY #YEAR_MONTH_DAY#YEAR_MONTH_DAY
     * @see #YEAR_MONTH #YEAR_MONTH#YEAR_MONTH
     * @see #MONTH_DAY #MONTH_DAY#MONTH_DAY
     */
    public MyDataPicker(Activity activity, @MyDataPicker.Mode int mode) {
        super(activity);
        this.mode = mode;
        for (int i = 2000; i <= 2050; i++) {
            years.add(String.valueOf(i));
        }
        for (int i = 1; i <= 12; i++) {
            months.add(DateUtils.fillZero(i));
        }
        for (int i = 1; i <= 31; i++) {
            days.add(DateUtils.fillZero(i));
        }
    }

    /**
     * Sets label.
     *
     * @param yearLabel  the year label
     * @param monthLabel the month label
     * @param dayLabel   the day label
     */
    public void setLabel(String yearLabel, String monthLabel, String dayLabel) {
        this.yearLabel = yearLabel;
        this.monthLabel = monthLabel;
        this.dayLabel = dayLabel;
    }

    /**
     * Sets range.
     *
     * @param startYear the start year
     * @param endYear   the end year
     */
    public void setRange(int startYear, int endYear) {
        years.clear();
        for (int i = startYear; i <= endYear; i++) {
            years.add(String.valueOf(i));
        }
    }

    private int findItemIndex(ArrayList<String> items, int item) {
        //折半查找有序元素的索引
        int index = Collections.binarySearch(items, item, new Comparator<Object>() {
            @Override
            public int compare(Object lhs, Object rhs) {
                String lhsStr = lhs.toString();
                String rhsStr = rhs.toString();
                lhsStr = lhsStr.startsWith("0") ? lhsStr.substring(1) : lhsStr;
                rhsStr = rhsStr.startsWith("0") ? rhsStr.substring(1) : rhsStr;
                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            }
        });
        if (index < 0) {
            index = 0;
        }
        return index;
    }

    /**
     * Sets selected item.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     */
    public void setSelectedItem(int year, int month, int day) {
        selectedYearIndex = findItemIndex(years, year);
        selectedMonthIndex = findItemIndex(months, month);
        selectedDayIndex = findItemIndex(days, day);
    }

    /**
     * Sets selected item.
     *
     * @param yearOrMonth the year or month
     * @param monthOrDay  the month or day
     */
    public void setSelectedItem(int yearOrMonth, int monthOrDay) {
        if (mode == MONTH_DAY) {
            selectedMonthIndex = findItemIndex(months, yearOrMonth);
            selectedDayIndex = findItemIndex(days, monthOrDay);
        } else {
            selectedYearIndex = findItemIndex(years, yearOrMonth);
            selectedMonthIndex = findItemIndex(months, monthOrDay);
        }
    }

    /**
     * Sets on date pick listener.
     *
     * @param listener the listener
     */
    public void setOnDatePickListener(MyDataPicker.OnDatePickListener listener) {
        this.onDatePickListener = listener;
    }

    public void setTextNormolColor(int textColor) {
        textColorNormal=textColor;
    }
    public void setTextFocusedColor(int textColor) {
        textColorFocus=textColor;
    }
    public void setLineColor(int lineColorInput) {
        lineColor=lineColorInput;
    }
    @Override
    @NonNull
    protected View makeCenterView() {
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);
        final WheelView dateView = new WheelView(activity);
        dateView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dateView.setTextSize(textSize);
        dateView.setTextColor(textColorNormal,textColorFocus);
        dateView.setLineVisible(lineVisible);
        dateView.setLineColor(lineColor);
        dateView.setOffset(offset);
        layout.addView(dateView);
        final TextView dateTextView = new TextView(activity);
        dateTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        dateTextView.setTextSize(textSize);
        dateTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(yearLabel)) {
            dateTextView.setText(yearLabel);
        }
        layout.addView(dateTextView);
        final WheelView timeView = new WheelView(activity);
        timeView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        timeView.setTextSize(textSize);
        timeView.setTextColor(textColorNormal, textColorFocus);
        timeView.setLineVisible(lineVisible);
        timeView.setLineColor(lineColor);
        timeView.setOffset(offset);
        layout.addView(timeView);
        TextView timeTextView = new TextView(activity);
        timeTextView.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        timeTextView.setTextSize(textSize);
        timeTextView.setTextColor(textColorFocus);
        if (!TextUtils.isEmpty(monthLabel)) {
            timeTextView.setText(monthLabel);
        }
        layout.addView(timeTextView);
        days.clear();
        final List<String> dataList = TimeDataUtil.getDataList(30);
        if(TimeDataUtil.getTimeSection(9,20)==TimeDataUtil.AFTER_SECTION){
            dataList.remove(0);
        }
        days.addAll(dataList);
        dateView.setItems(days, 0);
        dateView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                //更新索引
                selectedDayIndex = selectedIndex;
                //设置第二列数据
                times.clear();
                if (TimeDataUtil.isToday(days.get(selectedDayIndex))) {
                    List<String> timtArea = TimeDataUtil.getTimtArea(TimeDataUtil.getTime());
                    timeView.setItems(timtArea, 0);
                    times.addAll(timtArea);
                } else {
                    List<String> timtArea = TimeDataUtil.getTimtArea(0);
                    timeView.setItems(timtArea, 0);
                    times.addAll(timtArea);
                }
            }
        });

        if (!TextUtils.isEmpty(monthLabel)) {
            timeTextView.setText(monthLabel);
        }
        if (selectedMonthIndex == 0) {
            timeView.setItems(months);
        } else {
            timeView.setItems(months, selectedMonthIndex);
        }
        timeView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(boolean isUserScroll, int selectedIndex, String item) {
                selectedTimeIndex = selectedIndex;
            }
        });
        return layout;
    }

    private int stringToYearMonthDay(String text) {
        if (text.startsWith("0")) {
            //截取掉前缀0以便转换为整数
            text = text.substring(1);
        }
        return Integer.parseInt(text);
    }

    @Override
    protected void onSubmit() {
        if (onDatePickListener != null) {
            String day = getSelectedDay();
            String time = getSelectedTime();
            ((MyDataPicker.OnDateTimePickListener) onDatePickListener).onDatePicked(day, time);
        }
    }

    /**
     * Gets selected year.
     *
     * @return the selected year
     */
    public String getSelectedYear() {
        return years.get(selectedYearIndex);
    }

    /**
     * Gets selected month.
     *
     * @return the selected month
     */
    public String getSelectedMonth() {
        return months.get(selectedMonthIndex);
    }

    /**
     * Gets selected day.
     *
     * @return the selected day
     */
    public String getSelectedDay() {
        return days.get(selectedDayIndex);
    }

    public String getSelectedTime() {
        return times.get(selectedTimeIndex);
    }

    /**
     * The interface On date pick listener.
     */
    protected interface OnDatePickListener {

    }

    /**
     * The interface On year month day pick listener.
     */
    public interface OnDateTimePickListener extends MyDataPicker.OnDatePickListener {

        /**
         * On Mydate picked.
         *
         * @param day
         * @param time the day
         */
        void onDatePicked(String day, String time);

    }

}
