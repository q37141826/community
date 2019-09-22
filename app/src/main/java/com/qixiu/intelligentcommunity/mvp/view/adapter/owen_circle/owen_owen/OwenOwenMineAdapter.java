package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_owen;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenMineBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen.OwenOwenMineHolder;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenOwenMineAdapter extends RecyclerBaseAdapter<OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean, OwenOwenMineHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.owner_owen_circle_my_list_item;
    }

    @Override
    public OwenOwenMineHolder createViewHolder(View itemView, Context context, int viewType) {
        return new OwenOwenMineHolder(itemView, context, this);
    }
}
