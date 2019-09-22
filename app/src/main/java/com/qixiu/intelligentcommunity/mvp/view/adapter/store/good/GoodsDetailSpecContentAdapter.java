package com.qixiu.intelligentcommunity.mvp.view.adapter.store.good;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.DefaultBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.goods.GoodDetailSpecContentHolder;


/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodsDetailSpecContentAdapter extends DefaultBaseAdapter {

    @Override
    public int getLayoutId() {
        return R.layout.item_goodsdetail_popu_spec_gv;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new GoodDetailSpecContentHolder(itemView);
    }


}
