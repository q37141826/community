package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomeBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.HomeEventHolder;

/**
 * Created by HuiHeZe on 2017/6/22.
 */

public class EventAdapter extends RecyclerBaseAdapter<HomeBean.EBean,HomeEventHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_event_home;
    }

    @Override
    public HomeEventHolder createViewHolder(View itemView, Context context, int viewType) {
        return new HomeEventHolder(itemView,context,this);
    }
}
