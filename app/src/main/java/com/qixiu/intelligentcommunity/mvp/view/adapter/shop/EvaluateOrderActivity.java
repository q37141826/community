package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.shop.EvaluateOrderFragment;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class EvaluateOrderActivity extends NewTitleActivity implements View.OnClickListener {
    //// TODO: 2017/5/23 后台可能提供多个goodsid的拼接规格 ，现在暂时是一个
    private  String order_id,goods_id;
    EvaluateOrderFragment fragment;
    Bundle bundle;
    @Override
    protected void onInitData() {
        mTitleView.setTitle("评价订单");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle extra=getIntent().getExtras();
        OrderBean.OBean.ListBean data = extra.getParcelable("data");
        bundle.putParcelable("data", (Parcelable) data);
    }


    @Override
    protected void onInitViewNew() {
        bundle =new Bundle();
        bundle.putString("order_id",getIntent().getStringExtra("order_id"));
        bundle.putString("goods_id",getIntent().getStringExtra("goods_id"));
        fragment=new EvaluateOrderFragment();
        switchFragment(fragment, R.id.fl_evaluateorder_fragment);
        fragment.setArguments(bundle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_evaluateorder;
    }

    @Override
    public void onClick(View v) {

    }
}
