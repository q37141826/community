package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.os.Build;
import android.widget.TextView;

/**
 * Created by my on 2018/8/9.
 */

public class DrawableUtils {

    public static void setDrawableResouce(TextView textView, Context context, int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } else {
            textView.setCompoundDrawables(context.getResources().getDrawable(left), context.getResources().getDrawable(top),
                    context.getResources().getDrawable(right), context.getResources().getDrawable(bottom));
        }
    }


    public static void setLeftDrawableResouce(TextView textView, Context context, int left) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, 0, 0, 0);
        } else {
            textView.setCompoundDrawables(context.getResources().getDrawable(left), context.getResources().getDrawable(0),
                    context.getResources().getDrawable(0), context.getResources().getDrawable(0));
        }
    }

    public static void setRightDrawableResouce(TextView textView, Context context, int right) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, right, 0);
        } else {
            textView.setCompoundDrawables(context.getResources().getDrawable(0), context.getResources().getDrawable(0),
                    context.getResources().getDrawable(right), context.getResources().getDrawable(0));
        }
    }

    public static void setTopDrawableResouce(TextView textView, Context context, int top) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, top, 0, 0);
        } else {
            textView.setCompoundDrawables(context.getResources().getDrawable(0), context.getResources().getDrawable(top),
                    context.getResources().getDrawable(0), context.getResources().getDrawable(0));
        }
    }

    public static void setBottomDrawableResouce(TextView textView, Context context, int bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, bottom);
        } else {
            textView.setCompoundDrawables(context.getResources().getDrawable(0), context.getResources().getDrawable(0),
                    context.getResources().getDrawable(0), context.getResources().getDrawable(bottom));
        }
    }
}
