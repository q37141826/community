package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen.OwenOwenCommentsHolder;

/**
 * Created by Administrator on 2017/7/2 0002.
 */
public class OwenOwenCommentsAdapter extends RecyclerBaseAdapter<Object,OwenOwenCommentsHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_commtens;
    }

    @Override
    public OwenOwenCommentsHolder createViewHolder(View itemView, Context context, int viewType) {
        OwenOwenCommentsHolder owenOwenCommentsHolder = new OwenOwenCommentsHolder(itemView, context, this);
        return owenOwenCommentsHolder;
    }
}
