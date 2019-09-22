package com.qixiu.intelligentcommunity.mvp.view.holder.mypublish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.DefaultBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import org.w3c.dom.Text;

/**
 * Created by HuiHeZe on 2017/6/15.
 */

public class MyPublishHolder extends RecyclerBaseHolder<BaseBean> {
   private TextView textView_message_mypublish;

    public MyPublishHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_message_mypublish= (TextView) itemView.findViewById(R.id. textView_message_mypublish);
    }


    @Override
    public void bindHolder(int position) {

    }
}
