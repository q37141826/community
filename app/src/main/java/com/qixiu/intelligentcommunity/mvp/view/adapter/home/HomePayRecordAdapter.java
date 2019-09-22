package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomePayRecordBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.HomePayRecordHolder;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class HomePayRecordAdapter extends RecyclerBaseAdapter<HomePayRecordBean.OBean.ListBean,HomePayRecordHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_payrecord_home;
    }

    @Override
    public HomePayRecordHolder createViewHolder(View itemView, Context context, int viewType) {
        return new HomePayRecordHolder(itemView,context,this);
    }
}
