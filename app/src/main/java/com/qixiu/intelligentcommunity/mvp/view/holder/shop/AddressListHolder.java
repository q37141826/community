package com.qixiu.intelligentcommunity.mvp.view.holder.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AddressListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.address_manage.AddAddressActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/5/12.
 */

public class AddressListHolder extends RecyclerBaseHolder<AddressListBean.OBean> {
    private String addressId,addressName,addressPhone,addressContent;

    TextView textView_getgoodspeople_addresslist, textView_getgoodsphone_addresslist, textView_getgoodsaddress_addresslist, textview_isdefultaddress;
    ImageView imageView_edit_address, imageView_deleteaddress;
    RadioButton radiobtn_addressselect;

    public AddressListHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_getgoodspeople_addresslist = (TextView) itemView.findViewById(R.id.textView_getgoodspeople_addresslist);
        textView_getgoodsphone_addresslist = (TextView) itemView.findViewById(R.id.textView_getgoodsphone_addresslist);
        textView_getgoodsaddress_addresslist = (TextView) itemView.findViewById(R.id.textView_getgoodsaddress_addresslist);
        radiobtn_addressselect = (RadioButton) itemView.findViewById(R.id.radiobtn_addressselect);
        imageView_edit_address = (ImageView) itemView.findViewById(R.id.imageView_edit_address);
        imageView_deleteaddress = (ImageView) itemView.findViewById(R.id.imageView_deleteaddress);
        textview_isdefultaddress = (TextView) itemView.findViewById(R.id.textview_isdefultaddress);
    }

    @Override
    public void bindHolder(int position) {
        textView_getgoodspeople_addresslist.setText(mData.getConsignee());
        textView_getgoodsphone_addresslist.setText(mData.getMobile());
        textView_getgoodsaddress_addresslist.setText(mData.getProvince() + mData.getCity() + mData.getDistrict() + mData.getAddress());
        if ("1".equals(mData.getIs_default())) {
            radiobtn_addressselect.setChecked(true);
            textview_isdefultaddress.setText("默认地址");
            addressId=mData.getAddress_id();
            addressName=mData.getConsignee();
            addressPhone=mData.getMobile();
            addressContent=mData.getAddress();
        }else {
            radiobtn_addressselect.setChecked(false);
            textview_isdefultaddress.setText("设为默认");
        }
        imageView_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 2017/5/12  确认如何编辑收货地址 
                Intent intent = new Intent(mContext, AddAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", mData);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        imageView_deleteaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressId = mData.getAddress_id();
                startDeleteId(addressId);
            }
        });
        radiobtn_addressselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressId = mData.getAddress_id();
                setThisDefault(addressId);
            }
        });

    }

    private void setThisDefault(String addressId) {
        String token = MD5Util.getToken(ConstantUrl.SetDefaultAddressTag);
        OkHttpUtils.post().url(ConstantUrl.SetDefaultAddressUrl)
                .addParams("token", token)
                .addParams("userId", Preference.get("id", ""))
                .addParams("address_id", addressId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast(mContext, "设置失败");
            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC()==1) {
                    ToastUtil.showToast(mContext, "设置成功");

                        ((AddressRefresh)mContext).refreshAddress();
                    } else {
                    ToastUtil.showToast(mContext, "设置失败");
                    }
            }
        });

    }


    private void startDeleteId(String addressId) {
        String token = MD5Util.getToken(ConstantUrl.DelAddressTag);
        OkHttpUtils.post().url(ConstantUrl.DelAddressUrl)
                .addParams("token", token)
                .addParams("userId", Preference.get("id", "")).addParams("address_id", addressId).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast(mContext, "删除失败");
            }

            @Override
            public void onResponse(String s, int i) {
                if (s.contains("1")) {
                    ToastUtil.showToast(mContext, "删除成功");
                    ((AddressRefresh)mContext).refreshAddress();
                } else {
                    ToastUtil.showToast(mContext, "删除失败");
                }
            }
        });
    }

    public interface AddressRefresh{
        void refreshAddress();
    }
}
