package com.qixiu.intelligentcommunity.mvp.view.adapter.store.shopcar;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.listener.ShopCarCallBackInterface;
import com.qixiu.intelligentcommunity.mvp.beans.store.AddGoodsToCarsBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.store.shopcar.ShopCarHolder;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ShopCarAdapter extends RecyclerBaseAdapter<AddGoodsToCarsBean.AddGoodsToCarsInfo.ShopCartBean, ShopCarHolder> implements ShopCarCallBackInterface, ShopCarHolder.ShopCarOnItemSelectedListener {

    private ShopCarHolder.ShopCarOnItemSelectedListener shopCarOnItemSelectedListener;

    @Override
    public int getLayoutId() {
        return R.layout.item_rv_shopcar;
    }

    @Override
    public ShopCarHolder createViewHolder(View itemView, Context context, int viewType) {
        ShopCarHolder shopCarHolder = new ShopCarHolder(itemView, context, this);
        shopCarHolder.setShopCarOnItemSelectedListener(this);
        return shopCarHolder;
    }

    @Override
    public void setShopCarOnItemSelectedListener(ShopCarHolder.ShopCarOnItemSelectedListener shopCarOnItemSelectedListener) {

        this.shopCarOnItemSelectedListener = shopCarOnItemSelectedListener;
    }

    @Override
    public void shopCarOnItemSelected(int position) {
        if (shopCarOnItemSelectedListener != null) {
            shopCarOnItemSelectedListener.shopCarOnItemSelected(position);
        }
    }
}
