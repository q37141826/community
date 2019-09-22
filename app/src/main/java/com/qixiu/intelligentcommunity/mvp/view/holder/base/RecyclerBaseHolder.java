package com.qixiu.intelligentcommunity.mvp.view.holder.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 高玉恒 on 2017/4/10 0010.
 * 仅供RecyclerView使用的Holder
 */

public abstract class RecyclerBaseHolder<D> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected RecyclerView.Adapter mAdapter;
    protected D mData;


    public RecyclerBaseHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView);
        this.mContext = context;
        this.mAdapter = adapter;
    }

    public void setData(D data) {
        this.mData = data;
        this.itemView.setTag(data);
    }

    public abstract void bindHolder(int position);


    public D getData() {

        return this.mData;
    }
}
