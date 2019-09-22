package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.content.Intent;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.GoodDetailFragment;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class GoodDetailActivity extends NewTitleActivity implements View.OnClickListener {
    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setTitle("商品详情");
        mTitleView.setRightImage(R.mipmap.shopcar_icon);
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseApplication.isLoginToLongin(GoodDetailActivity.this)) {
                    return;
                }
                startActivity(new Intent(GoodDetailActivity.this, StoreShopCarActivity.class));
            }
        });

        switchFragment(new GoodDetailFragment(), R.id.fl_good_details, getIntent().getExtras());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_good_details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
