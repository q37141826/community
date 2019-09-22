package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.OrderDetailsHolder;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderDetailsAdapter extends RecyclerBaseAdapter<OrderDetailsBean.OBean.GoodsBean,OrderDetailsHolder> {



    @Override
    public int getLayoutId() {
        return R.layout.item_orderdetails;
    }

    @Override
    public OrderDetailsHolder createViewHolder(View itemView, Context context, int viewType) {
        return new OrderDetailsHolder(itemView,context,this);
    }
}
