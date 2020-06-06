package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.content.Intent;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.GoodDetailFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.ShopCarNumGroup;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class GoodDetailActivity extends NewTitleActivity implements View.OnClickListener, OKHttpUIUpdataListener {

    OKHttpRequestModel mOkHttpRequestModel ;

    @Override
    protected void onInitData() {
        mOkHttpRequestModel =new OKHttpRequestModel(this);
    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setTitle("商品详情");
        mTitleView.setRightImage(R.mipmap.shopcar_white);
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BaseApplication.isLoginToLongin(GoodDetailActivity.this)) {
                    return;
                }
                startActivity(new Intent(GoodDetailActivity.this, StoreShopCarActivity.class));
            }
        });

        switchFragment(new GoodDetailFragment(), R.id.fl_good_details, getIntent().getExtras());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_good_details;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getShopCarNum();
    }

    private void getShopCarNum() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", LoginUtils.getLoginId());
        mOkHttpRequestModel.okhHttpPost(ConstantUrl.shopCarNumUrl,map,new BaseBean());
    }

    @Override
    public void onSuccess(Object data, int i) {
        setShopCarNum(data);
    }

    private void setShopCarNum(Object data) {
        if(data instanceof BaseBean){
            BaseBean baseBean = (BaseBean) data;
            if(baseBean.getUrl().equals(ConstantUrl.shopCarNumUrl)){
                ShopCarNumGroup.ShopCarNumUtil.addText(mTitleView.getRl_num_right(),getActivity(),baseBean.getO().toString());
            }
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }
}
