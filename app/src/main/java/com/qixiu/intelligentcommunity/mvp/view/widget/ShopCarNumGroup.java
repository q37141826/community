package com.qixiu.intelligentcommunity.mvp.view.widget;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ShopCarNumGroup {

    private TextView textshopCarNum;
    private View view;
    private RelativeLayout rl;

    public ShopCarNumGroup(Activity activity) {
        view = activity.getLayoutInflater().inflate(R.layout.shopcarnum_group, null);
        if (view instanceof RelativeLayout) {
            rl = (RelativeLayout) view;
            textshopCarNum = view.findViewById(R.id.textshopCarNum);
        }
    }

    public RelativeLayout getShopCarLayout() {
        return rl;
    }

    public void setNum(String num) {
        textshopCarNum.setText(num);
    }

    public static class ShopCarNumUtil {
        public static void addText(ViewGroup viewGroup, Activity activity, String num) {
            ShopCarNumGroup shopCarNumGroup = new ShopCarNumGroup(activity);
            viewGroup.addView(shopCarNumGroup.getShopCarLayout());
            num = num.replace(".0", "");
            shopCarNumGroup.setNum(num);
            shopCarNumGroup.getShopCarLayout().setVisibility(num.equals("0") ? View.GONE : View.VISIBLE);
        }
    }
}
