package com.qixiu.intelligentcommunity.mvp.view.adapter.store;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.classify.StoreClassifyBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.StoreClassifyHolder;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class StoreClassifyAdapter extends RecyclerBaseAdapter<StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean, StoreClassifyHolder> {


    @Override
    public int getLayoutId() {
        return R.layout.item_storeclassify;
    }

    @Override
    public StoreClassifyHolder createViewHolder(View itemView, Context context, int viewType) {
        return new StoreClassifyHolder(itemView, context, this);
    }
}
