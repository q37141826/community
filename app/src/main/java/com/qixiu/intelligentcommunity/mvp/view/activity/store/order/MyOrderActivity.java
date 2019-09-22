package com.qixiu.intelligentcommunity.mvp.view.activity.store.order;

import android.support.design.widget.TabLayout;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.OrderFragmentAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.shop.OrderFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.hviewpager.HackyViewPager;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends NewTitleActivity implements View.OnClickListener {
    private TabLayout tablayout_myorder;
    private HackyViewPager recycleView_myoreder;
    List<FragmentInterface> fragmentInterfaces = null;
    private String[] titles = new String[]{"全部", "待付款", "待发货", "已发货", "已收货"};



    @Override
    protected void onInitData() {
        if (fragmentInterfaces == null) {
            fragmentInterfaces = new ArrayList<>();
        }

        for (int i = 0; i < titles.length; i++) {
            OrderFragment orderFragment = new OrderFragment();
            orderFragment.setPosition(i);
            fragmentInterfaces.add(orderFragment);
        }
        OrderFragmentAdapter orderFragmentAdapter = new OrderFragmentAdapter(this.getSupportFragmentManager(), fragmentInterfaces);
        orderFragmentAdapter.setPageTitle(titles);
        recycleView_myoreder.setAdapter(orderFragmentAdapter);
        tablayout_myorder.setupWithViewPager(recycleView_myoreder);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_order;
    }

    private void setClick() {

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setTitle("我的订单");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tablayout_myorder = (TabLayout) findViewById(R.id.tablayout_myorder);
        recycleView_myoreder = (HackyViewPager) findViewById(R.id.recycleView_myoreder);
        setClick();
    }
}
