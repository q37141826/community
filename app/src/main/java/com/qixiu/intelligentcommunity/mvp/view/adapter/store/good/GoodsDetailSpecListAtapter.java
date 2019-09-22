package com.qixiu.intelligentcommunity.mvp.view.adapter.store.good;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.goods.GoodsDetailSpecListHolder;


/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodsDetailSpecListAtapter extends RecyclerBaseAdapter<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean,GoodsDetailSpecListHolder> {


    @Override
    public int getLayoutId() {
        return R.layout.item_gooddetail_popu_spec;
    }

    @Override
    public GoodsDetailSpecListHolder createViewHolder(View itemView, Context context, int viewType) {
        return new GoodsDetailSpecListHolder(itemView, context, this);
    }
}
