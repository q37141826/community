package com.qixiu.intelligentcommunity.mvp.view.adapter.store.good;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.DefaultBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.goods.GoodDetailCommentHolder;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodsDetailCommentAdapter extends DefaultBaseAdapter {
    @Override
    public int getLayoutId() {
        return R.layout.items_goodsdetail_pictrue;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new GoodDetailCommentHolder(itemView);
    }


}
