package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.GetGoodsRecordBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.homepage.GetGoodsRecorderHolder;

/**
 * Created by HuiHeZe on 2017/9/8.
 */

public class GetGoodsRecordAdapter extends RecyclerBaseAdapter<GetGoodsRecordBean.OBean.ListBean,GetGoodsRecorderHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_get_goods_record;
    }

    @Override
    public GetGoodsRecorderHolder createViewHolder(View itemView, Context context, int viewType) {
        return new GetGoodsRecorderHolder(itemView,context,this);
    }
}
