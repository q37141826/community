package com.qixiu.intelligentcommunity.mvp.view.holder.store.goods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.GoodsDetailSpecEngine;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.spec.SingleSpecBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.good.GoodsDetailSpecContentAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.Preference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodsDetailSpecListHolder extends RecyclerBaseHolder<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> implements AdapterView.OnItemClickListener, OKHttpUIUpdataListener<BaseBean> {

    private final TextView mTv_gooddetail_popu_spec_name;
    private final GridView mGv_gooddetail_popu_spec;
    private final OKHttpRequestModel mOkHttpRequestModel;

    public GoodsDetailSpecListHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        mTv_gooddetail_popu_spec_name = (TextView) itemView.findViewById(R.id.tv_gooddetail_popu_spec_name);
        mGv_gooddetail_popu_spec = (GridView) itemView.findViewById(R.id.gv_gooddetail_popu_spec);
    }

    @Override
    public void bindHolder(int position) {
        mTv_gooddetail_popu_spec_name.setText(mData.getName() + ":");
        List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean> specBeanList = mData.getSpec();
        GoodsDetailSpecContentAdapter goodsDetailSpecContentAdapter = new GoodsDetailSpecContentAdapter();
        mGv_gooddetail_popu_spec.setAdapter(goodsDetailSpecContentAdapter);
        goodsDetailSpecContentAdapter.refreshData(specBeanList);
        mGv_gooddetail_popu_spec.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mData.setSelectSpecLine(true);
        GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean itemAtPosition = (GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean) parent.getItemAtPosition(position);
        if (itemAtPosition == null) return;

        view.setBackgroundResource(R.drawable.shape_shop_kind_select);
        TextView tv_goodsdetail_popuspec = (TextView) view.findViewById(R.id.tv_goodsdetail_popuspec);
        tv_goodsdetail_popuspec.setTextColor(view.getResources().getColor(R.color.white));
        itemAtPosition.setSelect(true);
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (i != position) {
                View currentView = parent.getChildAt(i);
                ((TextView) currentView.findViewById(R.id.tv_goodsdetail_popuspec)).setTextColor(currentView.getResources().getColor(R.color.textColor));
                currentView.setBackgroundResource(R.drawable.shape_shop_kind_notselect);
                GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean specBean = mData.getSpec().get(i);
                if (specBean != null) {
                    specBean.setSelect(false);
                }

            }
        }
        requestSpecData();


    }

    private void requestSpecData() {

        Map requestMap = new HashMap();
        requestMap.put(StringConstants.STRING_USERID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY) + StringConstants.STRING_EMPTY);
        RecyclerBaseAdapter adapter = (RecyclerBaseAdapter) mAdapter;
        Bundle arguments = adapter.getArguments();
        String goosId = null;
        if (arguments != null) {
            goosId = arguments.getString(IntentDataKeyConstant.GOODS_ID);
        }
        if (goosId != null) {
            requestMap.put(IntentDataKeyConstant.GOODS_ID, goosId);
        }

        List<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean> datas = ((RecyclerBaseAdapter) mAdapter).getDatas();
        Object specId = GoodsDetailSpecEngine.getSpecInfo(datas).get(0);
        String specIdString = specId == null ? null : (String) specId;
        if (!TextUtils.isEmpty(specIdString)) {
            requestMap.put(StringConstants.STRING_KET, specIdString);
        }

        mOkHttpRequestModel.okhHttpPost(ConstantUrl.STORE_GET_SPEC, requestMap, new SingleSpecBean());
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        Intent intent = new Intent();
        intent.setAction(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_ACTION);
        if (data != null && data instanceof SingleSpecBean) {
            SingleSpecBean shopCartBean = (SingleSpecBean) data;
            intent.putExtra(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_SPEC_KEY, shopCartBean.getO());
        }
        BaseApplication.getContext().sendBroadcast(intent);
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        Intent intent = new Intent();
        intent.setAction(IntentDataKeyConstant.BROADCAST_GOODSDETAIL_ACTION);
        BaseApplication.getContext().sendBroadcast(intent);
    }
}
