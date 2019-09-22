package com.qixiu.intelligentcommunity.mvp.view.adapter.store.classify;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.StoreClassifyHolder;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class StoreClassifyListAdapter extends RecyclerBaseAdapter {


    @Override
    public int getLayoutId() {
        return R.layout.item_storeclassify;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new StoreClassifyHolder(itemView, context, this);
    }


}
