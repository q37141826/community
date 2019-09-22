package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer.AnswerDetailsCommentsHolder;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class AnswerDetailsCommentsAdapter extends RecyclerBaseAdapter<OwenAnswerDetailsBean.EBean,AnswerDetailsCommentsHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_answer_comments_all_details;
    }

    @Override
    public AnswerDetailsCommentsHolder createViewHolder(View itemView, Context context, int viewType) {
        return new AnswerDetailsCommentsHolder(itemView,context,this);
    }
}
