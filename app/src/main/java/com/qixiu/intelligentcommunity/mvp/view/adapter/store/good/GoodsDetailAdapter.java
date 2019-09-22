package com.qixiu.intelligentcommunity.mvp.view.adapter.store.good;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.goods.GoodDetailHolder;


/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GoodsDetailAdapter extends RecyclerBaseAdapter<GoodsDetailBean.GoodsDetailInfos.GoodsCommentBean, GoodDetailHolder> {


    @Override
    public int getLayoutId() {
        return R.layout.item_gooddetail_rv;
    }

    @Override
    public GoodDetailHolder createViewHolder(View itemView, Context context, int viewType) {
        return new GoodDetailHolder(itemView, context, this);
    }
}
