package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenCircleAllBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen.OwenerOwenAllHolder;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwenOwenAllAdapter extends RecyclerBaseAdapter<OwenCircleAllBean.OBean.ListBean,OwenerOwenAllHolder> {
    private MyRefreshListener refreshlistener;
    @Override
    public int getLayoutId() {
        return R.layout.owner_circle_whole_list_item_new;
    }

    @Override
    public OwenerOwenAllHolder createViewHolder(View itemView, Context context, int viewType) {
        OwenerOwenAllHolder owenerOwenAllHolder = new OwenerOwenAllHolder(itemView, context, this);
        owenerOwenAllHolder.setRefreshListener(refreshlistener);
        return owenerOwenAllHolder;
    }

    public void setRefreshListener(MyRefreshListener refreshlistener) {
        this. refreshlistener = refreshlistener;
    }

    public interface MyRefreshListener {
        void OnRefresh(OwenCircleAllBean.OBean.ListBean mdata,int postion);
        void OnDeleteItem(OwenCircleAllBean.OBean.ListBean mdata);
    }
}
