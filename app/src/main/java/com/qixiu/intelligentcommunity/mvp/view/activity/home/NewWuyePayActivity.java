package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;

public class NewWuyePayActivity extends NewTitleActivity {
    public static final String TITLE = "物业缴费";
    public static final String TITLE_RIGHT = "缴费记录";

    @Override
    protected void onInitData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_new_wuye_pay;
    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setTitle(TITLE);
        mTitleView.setRightText(TITLE_RIGHT);
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightListener(v -> {

        });
    }

    @Override
    public void onClick(View v) {

    }
}
