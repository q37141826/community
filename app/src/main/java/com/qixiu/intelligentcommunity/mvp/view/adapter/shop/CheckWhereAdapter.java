package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.CheckWhereBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.CheckWhereHolder;

/**
 * Created by HuiHeZe on 2017/5/24.
 */

public class CheckWhereAdapter  extends RecyclerBaseAdapter<CheckWhereBean.DataBean,CheckWhereHolder> {

    @Override
    public int getLayoutId() {
        return R.layout.item_check_trance;
    }

    @Override
    public CheckWhereHolder createViewHolder(View itemView, Context context, int viewType) {
        return new CheckWhereHolder(itemView,context,this);
    }
}
