package com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.GetGoodsDetailsBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class GetGoodsDetailsActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean> {
    private OKHttpRequestModel okHttpRequestModel;
    private Button btn_confirmGetGoods;
    private TextView textView_goodsName, textView_getGoodsTime, textView_getAlready;
    private String id;

    @Override
    protected void onInitViewNew() {
        textView_goodsName = (TextView) findViewById(R.id.textView_goodsName);
        textView_getGoodsTime = (TextView) findViewById(R.id.textView_getGoodsTime);
        textView_getAlready = (TextView) findViewById(R.id.textView_getAlready);
        btn_confirmGetGoods = (Button) findViewById(R.id.btn_confirmGetGoods);
        btn_confirmGetGoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirmGetGoods:
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                BaseBean bean = new BaseBean();
                okHttpRequestModel.okhHttpPost(ConstantUrl.confirmGetGoodsUrl, map, bean);

                break;
        }
    }

    @Override
    protected void onInitData() {
        okHttpRequestModel = new OKHttpRequestModel(this);
        getDetails();
        mTitleView.setTitle("收件详情");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_get_goods_details;
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data.getUrl().equals(ConstantUrl.getGoodsDetailsUrl)) {
            if (!(data instanceof GetGoodsDetailsBean)) {
                ToastUtil.toast("看到这条消息说明服务器数据和apiview上面的数据结构不一致");
                return;
            }
            GetGoodsDetailsBean bean = (GetGoodsDetailsBean) data;
            textView_getGoodsTime.setText(bean.getO().getGtime());
            textView_goodsName.setText(bean.getO().getName());
            if (bean.getO().getStatus() == 1) {
                btn_confirmGetGoods.setVisibility(View.VISIBLE);
                textView_getAlready.setVisibility(View.GONE);
            } else {
                btn_confirmGetGoods.setVisibility(View.GONE);
                textView_getAlready.setVisibility(View.VISIBLE);
            }
        }
        if (data.getUrl().equals(ConstantUrl.confirmGetGoodsUrl)) {
            ToastUtil.toast(data.getM());
            finish();
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }

    public void getDetails() {
        id = getIntent().getStringExtra("id");
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        BaseBean bean = new GetGoodsDetailsBean();
        okHttpRequestModel.okhHttpPost(ConstantUrl.getGoodsDetailsUrl, map, bean);
    }
}
