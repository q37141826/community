package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.mine.PointsDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.PointsHolder;

/**
 * Created by HuiHeZe on 2017/9/8.
 */

public class PointsAdapter extends RecyclerBaseAdapter<PointsDetailsBean.OBean.ListBean,PointsHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_layout_points_list;
    }

    @Override
    public PointsHolder createViewHolder(View itemView, Context context, int viewType) {
        return new PointsHolder(itemView,context,this);
    }
}
