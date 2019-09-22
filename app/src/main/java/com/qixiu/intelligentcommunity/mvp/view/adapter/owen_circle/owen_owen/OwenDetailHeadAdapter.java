package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_owen;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen.OwenDetailsPriaseHeadHolder;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenDetailHeadAdapter extends RecyclerBaseAdapter<String,OwenDetailsPriaseHeadHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_zan_head;
    }

    @Override
    public OwenDetailsPriaseHeadHolder createViewHolder(View itemView, Context context, int viewType) {
        return new OwenDetailsPriaseHeadHolder(itemView,context,this);
    }
}
