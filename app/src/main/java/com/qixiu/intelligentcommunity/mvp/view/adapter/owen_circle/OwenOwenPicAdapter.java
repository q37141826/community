package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen.OwenOwenPicHolder;

/**
 * Created by Administrator on 2017/7/1 0001.
 */
public class OwenOwenPicAdapter  extends RecyclerBaseAdapter<OwenOwenPicBean,OwenOwenPicHolder>{
    @Override
    public int getLayoutId() {
        return R.layout.item_owenowen_pic;
    }

    @Override
    public OwenOwenPicHolder createViewHolder(View itemView, Context context, int viewType) {
        return new OwenOwenPicHolder(itemView,context,this);
    }
}
