package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/8/25.
 */

public class MyPopTimePicker implements View.OnClickListener {
    private Pop_itemSelectListener call, leftCall;
    private Context context;
    private View contentView;
    private PopupWindow popupWindow;
    private RelativeLayout relativeLayout_back_for_dismiss;
    private TextView textView_left_pop_picker, textView_right_pop_picker;
    private PickerView pickview_time_left, pickview_time_right;
    private String result = "", leftText = "", rightText = "";

    public MyPopTimePicker(Context context, List<String> listLeft, List<String> listRight, Pop_itemSelectListener leftcall, Pop_itemSelectListener call) {
        this.call = call;
        this.leftCall = leftcall;
        this.context = context;
        setAllView();
        setClick();
        pickview_time_left.setData(listLeft);
        pickview_time_right.setData(listRight);
        pickview_time_left.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                leftText = text;
                MyPopTimePicker.this.leftCall.getRightSelectData(text);
            }
        });
        pickview_time_right.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                rightText = text;
            }
        });
        leftText = listLeft.get(listLeft.size() / 2);
        rightText = listRight.get(listRight.size() / 2);
    }

    public void setLeftData(List<String> list) {
        pickview_time_left.setData(list);
    }


    public void setRightData(List<String> list) {
        pickview_time_right.setData(list);
        rightText = list.get(list.size() / 2);
    }

    private void setClick() {
        textView_right_pop_picker.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_right_pop_picker:
                call.getRightSelectData(leftText + "    " + rightText);
                dismiss();
                break;
        }
    }


    public interface Pop_itemSelectListener {
        void getRightSelectData(String str);
    }

    private void setAllView() {
        contentView = View.inflate(context, R.layout.pop_picker, null);
        relativeLayout_back_for_dismiss = (RelativeLayout) contentView.findViewById(R.id.relativeLayout_back_for_dismiss);
        textView_left_pop_picker = (TextView) contentView.findViewById(R.id.textView_left_pop_picker);
        textView_right_pop_picker = (TextView) contentView.findViewById(R.id.textView_right_pop_picker);
        pickview_time_left = (PickerView) contentView.findViewById(R.id.pickview_time_left);
        pickview_time_right = (PickerView) contentView.findViewById(R.id.pickview_time_right);
        setPop();
        showAtLocation(Gravity.CENTER, 0, 0);
        //设置一个空的监听事件，抢夺左侧的界面焦点

    }

    private void setPop() {
        //根据字数计算每个条目的长度
        popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);

        relativeLayout_back_for_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    public void show() {
        showAtLocation(Gravity.CENTER, 0, 0);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public void showAtLocation(int gravity, int x, int y) {
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(contentView, gravity, x, y);
        }
    }
}
