package com.qixiu.intelligentcommunity.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Author Xiexianyong
 * Date: 2017.06.23
 * Description: 只处理点击效果的CheckBox ,点击时不改变选中状态
 */

public class OnlyClickCheckBox extends CheckBox {
    public OnlyClickCheckBox(Context context) {
        super(context);
    }

    public OnlyClickCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnlyClickCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return callOnClick();
    }
}
