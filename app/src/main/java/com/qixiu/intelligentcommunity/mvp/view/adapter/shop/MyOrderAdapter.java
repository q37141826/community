package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.MyOrderHolder;


/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class MyOrderAdapter extends RecyclerBaseAdapter<OrderBean.OBean.ListBean, MyOrderHolder> {

    private MyOrderRefreshListener myOrderRefreshListener;

    @Override
    public int getLayoutId() {
        return R.layout.item1_myholder;
    }

    @Override
    public MyOrderHolder createViewHolder(View itemView, Context context, int viewType) {
        MyOrderHolder myOrderHolder = new MyOrderHolder(itemView, context, this);
        myOrderHolder.setMyOrderRefreshListener(myOrderRefreshListener);
        return myOrderHolder;
    }

    public void setMyOrderRefreshListener(MyOrderRefreshListener myOrderRefreshListener) {
       this. myOrderRefreshListener = myOrderRefreshListener;
    }

    public interface MyOrderRefreshListener {
        void onOrderRefresh(OrderBean.OBean.ListBean mdata);
    }

}
