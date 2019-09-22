package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenDetailsPriaseHeadHolder extends RecyclerBaseHolder<String> {
    private CircleImageView circuler_head_owen_detail;

    public OwenDetailsPriaseHeadHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        circuler_head_owen_detail= (CircleImageView) itemView.findViewById(R.id.circuler_head_owen_detail);
    }

    @Override
    public void bindHolder(int position) {
        Glide.with(mContext).load(ConstantUrl.hostImageurl + mData).into(circuler_head_owen_detail);
    }
}
