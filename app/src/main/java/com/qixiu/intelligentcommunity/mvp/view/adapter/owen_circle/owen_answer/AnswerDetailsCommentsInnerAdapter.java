package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer.AnswerDetailsCommentsInnerHolder;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class AnswerDetailsCommentsInnerAdapter extends RecyclerBaseAdapter {
    @Override
    public int getLayoutId() {
        return R.layout.item_answer_details_comments_inner;
    }

    @Override
    public AnswerDetailsCommentsInnerHolder createViewHolder(View itemView, Context context, int viewType) {
        return new AnswerDetailsCommentsInnerHolder(itemView, context, this);
    }
}
