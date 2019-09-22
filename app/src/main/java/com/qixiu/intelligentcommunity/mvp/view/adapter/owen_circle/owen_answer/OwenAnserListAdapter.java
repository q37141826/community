package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerListBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer.OwenAnswerListHolder;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class OwenAnserListAdapter extends RecyclerBaseAdapter<OwenAnswerListBean.OBean.ListBean,OwenAnswerListHolder> {
    private OnAnserRefresh  listenner;
    @Override
    public int getLayoutId() {
        return R.layout.item_owen_answerlist;
    }

    @Override
    public OwenAnswerListHolder createViewHolder(View itemView, Context context, int viewType) {
        OwenAnswerListHolder owenAnswerListHolder = new OwenAnswerListHolder(itemView, context, this);
        owenAnswerListHolder.setRefreshListenner(listenner);
        return owenAnswerListHolder;
    }
    public void setRefreshListenner(OnAnserRefresh listenner){
        this.listenner=listenner;
    }

    public interface  OnAnserRefresh{
        void refresh(OwenAnswerListBean.OBean.ListBean mData);
    }
}
