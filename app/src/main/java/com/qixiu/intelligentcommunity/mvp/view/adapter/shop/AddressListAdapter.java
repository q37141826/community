package com.qixiu.intelligentcommunity.mvp.view.adapter.shop;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AddressListBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.AddressListHolder;


/**
 * Created by HuiHeZe on 2017/5/12.
 */

public class AddressListAdapter extends RecyclerBaseAdapter<AddressListBean.OBean,AddressListHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_addresslist;
    }

    @Override
    public AddressListHolder createViewHolder(View itemView, Context context, int viewType) {
        return new AddressListHolder(itemView,context,this);
    }
}
