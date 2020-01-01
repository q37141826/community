package com.qixiu.intelligentcommunity.mvp.view.activity.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.serve.ServeFragment;

public class ServiceActivity extends NewTitleActivity {


    @Override
    protected void onInitData() {
        mTitleView.setTitle("服务定制");
        addFragment(R.id.frameLayout_service, new ServeFragment(), "");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_service;
    }

    @Override
    protected void onInitViewNew() {

    }

    @Override
    public void onClick(View view) {

    }

}
