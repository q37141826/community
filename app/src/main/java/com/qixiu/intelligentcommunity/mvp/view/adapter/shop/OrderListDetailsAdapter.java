package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.MyOrderListDetailsHolder;


/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderListDetailsAdapter extends RecyclerBaseAdapter<OrderBean.OBean.ListBean.GoodsBean, MyOrderListDetailsHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.item2_myorder_detais;
    }

    @Override
    public MyOrderListDetailsHolder createViewHolder(View itemView, Context context, int viewType) {
        return new MyOrderListDetailsHolder(itemView, context, this);
    }
}
