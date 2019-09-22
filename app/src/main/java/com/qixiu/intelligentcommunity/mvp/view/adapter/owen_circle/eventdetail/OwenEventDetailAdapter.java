package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.eventdetail;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_event.detail.OwenEventDetailHolder;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class OwenEventDetailAdapter extends RecyclerBaseAdapter {
   

    @Override
    public int getLayoutId() {
        return R.layout.item_eventdetail_rv;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new OwenEventDetailHolder(itemView, context, this);
    }
}
