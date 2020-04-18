package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.Alipay;
import com.alipay.PayResult;
import com.alipay.interf.AliBean;
import com.alipay.interf.IPay;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.PlatformConfigConstant;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wxpay.WeixinPayBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class AliWeixinPayActivity extends BaseActivity implements View.OnClickListener, IPay, OKHttpUIUpdataListener<BaseBean> {
    private RelativeLayout relativeLayout_title_aliweixinpay, relativeLayout_weixinpay, relativeLayout_alipay;
    private ImageView imageView_weixinicon, imageView_ali_icon, imageView_weixinpay_selection, imageView_alipay_selection;
    private TextView textView_weixinpay001, textView_weixinpay002, textView_ali_001, textView_ali_002, textView_money_howmuch;
    //缴费参数
    private String payid, money, paymethod = 1 + "", fromwhere;
    private int type = 1;

    private Button btn_confirm_pay;
    //
    private OKHttpRequestModel okmodel;
    //接受打赏成功的广播
    private BroadcastReceiver receiver;

    private String title;

    ZProgressHUD zProgressHUD;

    @Override
    protected void onInitData() {
        zProgressHUD = new ZProgressHUD(this);
        okmodel = new OKHttpRequestModel(this);
        setWhichSelected(0);
        try {
            payid = getIntent().getStringExtra("payid");
            money = getIntent().getStringExtra("money");
            type = getIntent().getIntExtra("type", 1);
            fromwhere = getIntent().getStringExtra(ConstantString.FROM_WHERE);
        } catch (Exception e) {
        }
        textView_money_howmuch.setText("¥ " + money);

        IntentFilter filter = new IntentFilter(IntentDataKeyConstant.BROADCAST_PAY_SHOPCARREFRESH_ACTION);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    zProgressHUD.dismiss();
                } catch (Exception e) {
                }
                finish();
            }
        };
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onInitView() {
        btn_confirm_pay = (Button) findViewById(R.id.btn_confirm_pay);
        textView_money_howmuch = (TextView) findViewById(R.id.textView_money_howmuch);
        relativeLayout_weixinpay = (RelativeLayout) findViewById(R.id.relativeLayout_weixinpay);
        relativeLayout_alipay = (RelativeLayout) findViewById(R.id.relativeLayout_alipay);
        imageView_weixinicon = (ImageView) findViewById(R.id.imageView_weixinicon);
        imageView_ali_icon = (ImageView) findViewById(R.id.imageView_ali_icon);
        imageView_weixinpay_selection = (ImageView) findViewById(R.id.imageView_weixinpay_selection);
        imageView_alipay_selection = (ImageView) findViewById(R.id.imageView_alipay_selection);
        textView_weixinpay001 = (TextView) findViewById(R.id.textView_weixinpay001);
        textView_weixinpay002 = (TextView) findViewById(R.id.textView_weixinpay002);
        textView_ali_001 = (TextView) findViewById(R.id.textView_ali_001);
        textView_ali_002 = (TextView) findViewById(R.id.textView_ali_002);
        relativeLayout_title_aliweixinpay = (RelativeLayout) findViewById(R.id.relativeLayout_title_aliweixinpay);
        inittitle();
        initclick();
    }

    private void initclick() {
        btn_confirm_pay.setOnClickListener(this);
        relativeLayout_weixinpay.setOnClickListener(this);
        relativeLayout_alipay.setOnClickListener(this);
    }

    private void inittitle() {
        TitleView titleView = new TitleView(this);
        titleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("确认缴费");
        titleView.setTitle_textColor(getResources().getColor(R.color.white));
        relativeLayout_title_aliweixinpay.addView(titleView.getView());
        try {
            title = getIntent().getStringExtra(ConstantString.TITLE_NAME);
            if (title != null) {
                titleView.setTitle(title);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_ali_weixin_pay;
    }

    public void setWhichSelected(int whichSelected) {
        if (whichSelected == 0) {
            paymethod = 1 + "";
            imageView_weixinicon.setImageResource(R.mipmap.weixin_selected3x);
            imageView_weixinpay_selection.setImageResource(R.mipmap.is_selected);
            imageView_ali_icon.setImageResource(R.mipmap.alipay_notselected);
            imageView_alipay_selection.setImageResource(R.mipmap.not_selected);
            textView_weixinpay001.setTextColor(getResources().getColor(R.color.font_grey));
            textView_weixinpay002.setTextColor(getResources().getColor(R.color.font_grey));
            textView_ali_001.setTextColor(getResources().getColor(R.color.yancy_grey500));
            textView_ali_002.setTextColor(getResources().getColor(R.color.yancy_grey500));
        } else {
            paymethod = 2 + "";
            imageView_weixinicon.setImageResource(R.mipmap.weixin_notselected);
            imageView_weixinpay_selection.setImageResource(R.mipmap.not_selected);
            imageView_ali_icon.setImageResource(R.mipmap.alipay_selected);
            imageView_alipay_selection.setImageResource(R.mipmap.is_selected);
            textView_weixinpay001.setTextColor(getResources().getColor(R.color.yancy_grey500));
            textView_weixinpay002.setTextColor(getResources().getColor(R.color.yancy_grey500));
            textView_ali_001.setTextColor(getResources().getColor(R.color.font_grey));
            textView_ali_002.setTextColor(getResources().getColor(R.color.font_grey));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_weixinpay:
                setWhichSelected(0);
                break;
            case R.id.relativeLayout_alipay:
                setWhichSelected(1);
                break;
            case R.id.btn_confirm_pay:
                zProgressHUD.show();
                if (fromwhere == null) {
                    startPay();
                } else {
                    startGiveTip();
                }
                break;
        }
    }

    private void startGiveTip() {
        Map<String, String> map = new HashMap<>();
        map.put("id", payid);
        map.put("paymethod", paymethod);
        map.put("money", money);
        BaseBean bean;
        if (paymethod.equals("2")) {
            bean = new AliBean();
        } else {
            bean = new WeixinPayBean();
        }
        okmodel.okhHttpPost(ConstantUrl.giveTipUrl, map, bean);
    }

    private void startPay() {
        OkHttpUtils.post().url(ConstantUrl.payUrl)
                .addParams("cost_id", payid)
                .addParams("paymethod", paymethod)
                .addParams("type", type + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                zProgressHUD.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                Preference.put(ConstantString.IS_WUYE_OR_CAR, type);
                if (paymethod.equals("2")) {
                    try {
                        AliBean aliBean = GetGson.init().fromJson(s, AliBean.class);
                        new Alipay(AliWeixinPayActivity.this, AliWeixinPayActivity.this).startPay(aliBean.getO().toString());
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        WeixinPayBean bean = GetGson.init().fromJson(s, WeixinPayBean.class);
                        if (bean.getO().getPrepayid() == null || bean.getO().getPrepayid().equals("")) {
                            ToastUtil.showToast(AliWeixinPayActivity.this, "支付异常，请联系客服");
                            return;
                        }
                        startWeixinPay(bean);
                    } catch (Exception e) {
                    }
                }
            }
        });
    }

    private void startWeixinPay(WeixinPayBean bean) {
        IWXAPI wxapi;
        wxapi = WXAPIFactory.createWXAPI(this, PlatformConfigConstant.WEIXIN_APP_ID);
        wxapi.registerApp(PlatformConfigConstant.WEIXIN_APP_ID);
        PayReq payReq = new PayReq();
        payReq.appId = PlatformConfigConstant.WEIXIN_APP_ID;
        payReq.partnerId = bean.getO().getPartnerid();
        payReq.prepayId = bean.getO().getPrepayid();
        payReq.packageValue = bean.getO().getPackageX();
        payReq.nonceStr = bean.getO().getNoncestr();
        payReq.timeStamp = bean.getO().getTimestamp() + "";
        payReq.sign = bean.getO().getSign();
        payReq.extData = "app data";
        wxapi.sendReq(payReq);
        Log.e("prepayid", payReq.prepayId + "---");
    }

    @Override
    public void onSuccess(String msg) {
        zProgressHUD.dismiss();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        if (fromwhere == null) {
            CommonUtils.startIntent(this, PayRecordActivity.class, bundle);
        } else {
            Intent intent = new Intent();
            intent.setAction(ConstantString.broadCastFinishPay);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onFailure(PayResult payResult) {
        zProgressHUD.dismiss();
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (paymethod.equals("2")) {
            new Alipay(AliWeixinPayActivity.this, AliWeixinPayActivity.this).startPay(data.getO().toString());
        }
        if (data instanceof WeixinPayBean) {
            WeixinPayBean bean = (WeixinPayBean) data;
            startWeixinPay(bean);
        }

    }

    @Override
    public void onError(Call call, Exception e, int i) {
        ToastUtil.toast(R.string.link_error);
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        ToastUtil.toast(c_codeBean.getM());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
