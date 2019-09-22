package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event.OwenEventBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class OwenEventHolder extends RecyclerBaseHolder<OwenEventBean.OwenEventInfo.ListBean> {

    private final TextView mTv_event_title;
    private final TextView mTv_start_date;
    private final TextView mTv_end_date;
    private final TextView mTv_event_joincounte;
    private final ImageView mIv_event_picture;

    public OwenEventHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mTv_event_title = (TextView) itemView.findViewById(R.id.tv_event_title);
        mTv_start_date = (TextView) itemView.findViewById(R.id.tv_start_date);
        mTv_end_date = (TextView) itemView.findViewById(R.id.tv_end_date);
        mTv_event_joincounte = (TextView) itemView.findViewById(R.id.tv_event_joincount);
        mIv_event_picture = (ImageView) itemView.findViewById(R.id.iv_event_picture);
    }

    @Override
    public void bindHolder(int position) {
        mTv_event_title.setText(mData.getTitle());
        mTv_start_date.setText(mData.getStime());
        mTv_end_date.setText(mData.getEtime());
        mTv_event_joincounte.setText(mData.getNum() + "人已参加");
        List<String> imgs = mData.getImgs();
        if (imgs != null && imgs.size() > 0) {
            Glide.with(itemView.getContext()).load(ConstantUrl.hostImageurl + imgs.get(0)).crossFade().placeholder(R.mipmap.loading).error(R.mipmap.error_image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv_event_picture);
        }


    }
}
