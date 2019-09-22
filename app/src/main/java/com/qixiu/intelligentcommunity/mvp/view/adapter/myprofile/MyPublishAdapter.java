package com.qixiu.intelligentcommunity.mvp.view.adapter.myprofile;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.mypublish.MyPublishHolder;

/**
 * Created by HuiHeZe on 2017/6/16.
 */

public class MyPublishAdapter extends RecyclerBaseAdapter<BaseBean,MyPublishHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_my_publish;
    }

    @Override
    public MyPublishHolder createViewHolder(View itemView, Context context, int viewType) {
        return new MyPublishHolder(itemView,context,this);
    }
}
