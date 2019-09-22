package com.qixiu.intelligentcommunity.mvp.view.activity.store.order;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AddressListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.address_manage.AddAddressActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.AddressListAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.shop.AddressListHolder;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class ShopAddressActivity extends NewTitleActivity implements View.OnClickListener ,AddressListHolder.AddressRefresh{
    private RecyclerView recycleView_getgoodz_address;
    private Button btn_add_newAddress;
    SwipeRefreshLayout swiprefresh_address;
    private AddressListAdapter adapter;
    private TextView textView_ishaving_address;


    @Override
    protected void onInitData() {
        mTitleView.setTitle("收货地址");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String token = MD5Util.getToken(ConstantUrl.AddressListTag);
        OkHttpUtils.post().url(ConstantUrl.AddressListUrl).addParams("userId", Preference.get(StringConstants.STRING_ID, "")).addParams("token", token).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                swiprefresh_address.setRefreshing(false);
            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = new Gson();
                try {
                    AddressListBean addressListBean = gson.fromJson(s, AddressListBean.class);
                    if(addressListBean.getO().size()==0){
                        textView_ishaving_address.setVisibility(View.VISIBLE);
                    }else {
                        textView_ishaving_address.setVisibility(View.GONE);
                    }
                    adapter.refreshData(addressListBean.getO());
                    swiprefresh_address.setRefreshing(false);
                } catch (Exception e) {
                    adapter.refreshData(new ArrayList<AddressListBean.OBean>());
                    textView_ishaving_address.setVisibility(View.VISIBLE);
                }
                swiprefresh_address.setRefreshing(false);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shop_address;
    }

    private void setClick() {
        btn_add_newAddress.setOnClickListener(this);
        swiprefresh_address.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onInitData() ;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_newAddress:
                Intent intent = new Intent(this,AddAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void refreshAddress() {
        onInitData() ;
    }

    @Override
    protected void onRestart() {
        super.onStart();
        onInitData();
    }

    @Override
    protected void onInitViewNew() {
        textView_ishaving_address= (TextView) findViewById(R.id.textView_ishaving_address);
        recycleView_getgoodz_address = (RecyclerView) findViewById(R.id.recycleView_getgoodz_address);
        btn_add_newAddress = (Button) findViewById(R.id.btn_add_newAddress);

        swiprefresh_address = (SwipeRefreshLayout) findViewById(R.id.swiprefresh_address);
        adapter = new AddressListAdapter();
        recycleView_getgoodz_address.setLayoutManager(new LinearLayoutManager(this));
        recycleView_getgoodz_address.setAdapter(adapter);
        setClick();
    }
}
