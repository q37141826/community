package com.qixiu.intelligentcommunity.mvp.view.fragment.serve;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.ServiceBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.ServiceAdapter;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.holder.ServiceHolder;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class ServeFragment extends BaseFragment implements ServiceHolder.CallPermission {
    private RecyclerView recyclerview_service;
    private ServiceAdapter adapter;
    private SwipeRefreshLayout swip_service;
    private String callPhone;
    private String permissions[] = {Manifest.permission.CALL_PHONE};

    @Override
    protected void onInitData() {
        adapter = new ServiceAdapter();
        adapter.setCallListenner(this);
        recyclerview_service.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_service.setAdapter(adapter);
        swip_service.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

            getData();
        }
    }

    @Override
    protected void onInitView(View view) {
        swip_service = (SwipeRefreshLayout) view.findViewById(R.id.swip_service);
        recyclerview_service = (RecyclerView) view.findViewById(R.id.recyclerview_service);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main_serve;
    }


    public void getData() {
        if (adapter != null ) {
            OkHttpUtils.get().url(ConstantUrl.serviceUrl).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    swip_service.setRefreshing(false);
                }

                @Override
                public void onResponse(String s, int i) {
                    try {
                        ServiceBean serviceBean = GetGson.init().fromJson(s, ServiceBean.class);
                        adapter.refreshData(serviceBean.getO());
                    } catch (Exception e) {
                    }
                    swip_service.setRefreshing(false);
                }
            });
        } else {
            swip_service.setRefreshing(false);
        }
    }


    @Override
    public void call(String phone) {
        callPhone = phone;
        if (!hasPermission(getContext(), permissions)) {
            hasRequse(getActivity(), 1, permissions);
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);
    }


}
