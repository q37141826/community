package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.MessageListBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.MessageListHolder;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class MessageListAdapter extends RecyclerBaseAdapter<MessageListBean.OBean.ListBean,MessageListHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_messagelist;
    }

    @Override
    public MessageListHolder createViewHolder(View itemView, Context context, int viewType) {
        return new MessageListHolder(itemView,context,this);
    }
}
