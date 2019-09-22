package com.qixiu.intelligentcommunity.mvp.view.adapter;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.ServiceBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.ServiceHolder;

/**
 * Created by HuiHeZe on 2017/6/26.
 */

public class ServiceAdapter extends RecyclerBaseAdapter<ServiceBean.OBean, ServiceHolder> {
    private ServiceHolder.CallPermission call;



    public void setCallListenner(ServiceHolder.CallPermission call) {
        this.call = call;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_service;
    }


    @Override
    public ServiceHolder createViewHolder(View itemView, Context context, int viewType) {
        ServiceHolder serviceHolder = new ServiceHolder(itemView, context, this);
        if (call != null) {
            serviceHolder.setCallListenner(call);
        }
        return serviceHolder;
    }
}
