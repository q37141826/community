package com.qixiu.intelligentcommunity.mvp.view.activity.store.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.Alipay;
import com.alipay.PayResult;
import com.alipay.interf.IPay;
import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.PlatformConfigConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AddressListBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AliPayBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.WeixinPayModel;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.address_manage.AddAddressActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class ConfirmOrderActivity extends NewTitleActivity implements View.OnClickListener, IPay {
    private String pay_code = "alipay", addressId, addressName, addressPhone, addressContent, addressProvince, addressCity, addressDistict;
    private RelativeLayout belowme, layout_belowme2, relativelayout_goto_address_list;
    private ImageView imageView_alipay, imageView_weixinpay, tomyright2, imageView_tomytight_01;
    private Button btn_pay_conforder;
    private TextView textView_name_comforOrder, textView_phone_comforOrder, textView_address_comforOrder, textView_editaddress_coformorder, textView_totalprice, textView_postage;
    private PopupWindow mPopWindow;
    //支付方式判断
    private String cart_id = "",//这是购物车进来的参数
            goodsid, speckey, goodssum;//这是直接支付进来的参数
    double totolprice, postage;
    //生成订单的入口
    private String url;//不同的方式进来的url不一样
    //微信支付类
    IWXAPI wxapi;
    //微信支付成功后的广播
    BroadcastReceiver receiver;
    //返回的支付订单
    String order_id;
    AliPayBean aliPayBean;
    //传过去编辑的地址
    AddressListBean.OBean addressoBean;
    ZProgressHUD zProgressHUD;

    @Override
    protected void onInitData() {
        zProgressHUD = new ZProgressHUD(this);
        Preference.put(ConstantString.payWhat, ConfirmOrderActivity.class.getSimpleName());
        mTitleView.setTitle("确认订单");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setclick();
        try {
            cart_id = getIntent().getStringExtra(IntentDataKeyConstant.CART_ID);
            goodsid = getIntent().getStringExtra(IntentDataKeyConstant.GOODS_ID);
            speckey = getIntent().getStringExtra(IntentDataKeyConstant.SPEC_KEY);
            goodssum = getIntent().getStringExtra(IntentDataKeyConstant.GOODS_NUM);
            totolprice = getIntent().getDoubleExtra("totolprice", 0.0);
            postage = getIntent().getDoubleExtra(IntentDataKeyConstant.POSTAGE_KEY, 0.0);
            textView_totalprice.setText("¥" + totolprice + "");
            if (postage > 0) {
                textView_postage.setText(" （ 运费：¥" + postage + " ） : ");
            } else {
                textView_postage.setText(" （ 含运费 ） : ");
            }
        } catch (Exception e) {

        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentDataKeyConstant.BROADCAST_CONFIRM_FINISH
        );
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
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_confirm_order;
    }

    private void setclick() {
        belowme.setOnClickListener(this);
        layout_belowme2.setOnClickListener(this);
        imageView_alipay.setOnClickListener(this);
        imageView_weixinpay.setOnClickListener(this);
        textView_editaddress_coformorder.setOnClickListener(this);
        btn_pay_conforder.setOnClickListener(this);
        relativelayout_goto_address_list.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.belowme:
            case R.id.imageView_alipay:
                pay_code = "alipay";
                tomyright2.setImageResource(R.mipmap.alipay_selected);
                imageView_tomytight_01.setImageResource(R.mipmap.weixin_notselected);
                imageView_alipay.setBackgroundResource(R.mipmap.is_selected);
                imageView_weixinpay.setBackgroundResource(R.mipmap.circle_noselected);
                break;

            case R.id.layout_belowme2:
            case R.id.imageView_weixinpay:
                tomyright2.setImageResource(R.mipmap.alipay_notselected);
                imageView_tomytight_01.setImageResource(R.mipmap.weixin_selected3x);
                pay_code = "weixin";
                imageView_weixinpay.setBackgroundResource(R.mipmap.is_selected);
                imageView_alipay.setBackgroundResource(R.mipmap.circle_noselected);
                break;

            case R.id.textView_editaddress_coformorder:
                Intent intent = new Intent(this, AddAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", addressoBean);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btn_pay_conforder:
                zProgressHUD.show();
                getMessage();
                //生成订单
                startCreatOrder();
                break;
            case R.id.relativelayout_goto_address_list:
                CommonUtils.startIntent(this, ShopAddressActivity.class);
                break;
        }
    }

    private void startCreatOrder() {
        String token;
        if (cart_id != null && !"".equals(cart_id)) {
            token = MD5Util.getToken(ConstantUrl.SubGoodsFromCartTag);
            url = ConstantUrl.SubGoodsFromCartUrl;
            OkHttpUtils.post().url(url).addParams("token", token)
                    .addParams("user_id", Preference.get(StringConstants.STRING_ID, ""))
                    .addParams("cart_id", cart_id)
                    .addParams("address_id", addressId)
                    .addParams("pay_code", pay_code).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(String s, int i) {
                    Gson gson = GetGson.init();
                    Intent intent = new Intent();
                    intent.setAction(IntentDataKeyConstant.BROADCAST_PAY_SHOPCARREFRESH_ACTION);
                    sendBroadcast(intent);
                    try {
                        if (pay_code.equals("alipay")) {
                            AliPayBean aliPayBean = gson.fromJson(s, AliPayBean.class);
//                            ToastUtil.showToast(ConfirmOrderActivity.this,aliPayBean.getM());
                            new Alipay(ConfirmOrderActivity.this, ConfirmOrderActivity.this).startPay(aliPayBean.getO().getAlipay());
                        } else {
                            WeixinPayModel weixinPayModel = gson.fromJson(s, WeixinPayModel.class);
                            startWeixinPay(weixinPayModel);
                        }
                    } catch (Exception e) {
                        MessageBean messageBean = GetGson.parseMessageBean(s);
                        ToastUtil.showToast(ConfirmOrderActivity.this, messageBean.getM());
                    }
                }
            });
        } else {
            token = MD5Util.getToken(ConstantUrl.addOrderNowTag);
            url = ConstantUrl.addOrderNowUrl;
            OkHttpUtils.post().url(url).addParams("token", token)
                    .addParams("user_id", Preference.get(StringConstants.STRING_ID, ""))
                    .addParams("goods_id", goodsid)
                    .addParams("goods_num", goodssum)
                    .addParams("spec_key", speckey)
                    .addParams("address_id", addressId)
                    .addParams("pay_code", pay_code).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {

                }

                @Override
                public void onResponse(String s, int i) {
                    Gson gson = GetGson.init();
                    Intent intent = new Intent();
                    intent.setAction(IntentDataKeyConstant.BROADCAST_PAY_SHOPCARREFRESH_ACTION);
                    sendBroadcast(intent);
                    try {
                        if (pay_code.equals("alipay")) {
                            aliPayBean = gson.fromJson(s, AliPayBean.class);
//                            ToastUtil.showToast(ConfirmOrderActivity.this,aliPayBean.getM());
                            new Alipay(ConfirmOrderActivity.this, ConfirmOrderActivity.this).startPay(aliPayBean.getO().getAlipay());
                        } else {
                            WeixinPayModel weixinPayModel = gson.fromJson(s, WeixinPayModel.class);
                            startWeixinPay(weixinPayModel);
                        }
                    } catch (Exception e) {
                        MessageBean messageBean = GetGson.parseMessageBean(s);
                        ToastUtil.showToast(ConfirmOrderActivity.this, messageBean.getM());
                    }
                }
            });
        }
    }


    public void getMessage() {
        addressName = textView_name_comforOrder.getText().toString();
        addressPhone = textView_phone_comforOrder.getText().toString();
        addressContent = textView_address_comforOrder.getText().toString();
    }

    public void getDefaultAddress() {
        String token = MD5Util.getToken(ConstantUrl.AddressListTag);
        OkHttpUtils.post().url(ConstantUrl.AddressListUrl).addParams("userId", Preference.get(StringConstants.STRING_ID, "")).addParams("token", token).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = new Gson();
                try {
                    AddressListBean addressListBean = gson.fromJson(s, AddressListBean.class);
                    if (addressListBean.getO().size() == 0) {
//                        //弹出对话框
//                        setPopwindow();
                        //换一个弹窗
                        setDialog();
                    } else {
                        for (int ii = 0; ii < addressListBean.getO().size(); ii++) {
                            AddressListBean.OBean addressoBean = addressListBean.getO().get(ii);
                            if (addressListBean.getO().get(ii).getIs_default().equals("1")) {
                                addressContent = addressoBean.getAddress();
                                addressId = addressoBean.getAddress_id();
                                addressName = addressoBean.getConsignee();
                                addressPhone = addressoBean.getMobile();
                                ConfirmOrderActivity.this.addressoBean = addressoBean;
                                textView_address_comforOrder.setText(addressoBean.getProvince() + addressoBean.getCity() + addressoBean.getDistrict() + addressContent);
                                textView_name_comforOrder.setText(addressName);
                                textView_phone_comforOrder.setText(addressPhone);
                            }
                        }
                    }
                } catch (Exception e) {
//                    setPopwindow();
                    //换一个弹窗
                    setDialog();
                }

            }
        });
    }

    private void setDialog() {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("新增地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ConfirmOrderActivity.this, ShopAddressActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setMessage("没有默认地址");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }


    @Override
    protected void onStart() {
        super.onStart();
        //判断是否有默认地址
        getDefaultAddress();
        if (mPopWindow != null) {
            mPopWindow.dismiss();
        }

    }


    @Override
    public void onSuccess(String msg) {
        zProgressHUD.dismiss();
        Log.e("zhifu", msg);
        try {
            String token = MD5Util.getToken(ConstantUrl.NoticePaySuccessTag);
            OkHttpUtils.post().url(ConstantUrl.NoticePaySuccessUrl)
                    .addParams("token", token)
                    .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                    .addParams("order_id", aliPayBean.getO().getOrder_id())
//                .addParams("pay_code",aliPayBean.getO().getAlipay())
//                .addParams("pay_name",pay_code)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {
                    ToastUtil.showToast(ConfirmOrderActivity.this, "网络错误");
                }

                @Override
                public void onResponse(String s, int i) {
                    finish();
//                MessageBean messageBean = GetGson.parseMessageBean(s);
//                ToastUtil.showToast(ConfirmOrderActivity.this,messageBean.getM());
                }
            });
        } catch (Exception e) {
        }
        Intent intent = new Intent(IntentDataKeyConstant.BROADCAST_PAY_SHOPCARREFRESH_ACTION
        );
        sendBroadcast(intent);
        finish();
    }

    @Override
    public void onFailure(PayResult payResult) {
        finish();
        zProgressHUD.dismiss();
    }


    private void startWeixinPay(WeixinPayModel baseModel) {

        wxapi = WXAPIFactory.createWXAPI(this, PlatformConfigConstant.WEIXIN_APP_ID);
        wxapi.registerApp(PlatformConfigConstant.WEIXIN_APP_ID);
        PayReq payReq = new PayReq();
        payReq.appId = PlatformConfigConstant.WEIXIN_APP_ID;
        payReq.partnerId = baseModel.getO().getPartnerid();
        payReq.prepayId = baseModel.getO().getPrepayid();
        payReq.packageValue = baseModel.getO().getPackageX();
        payReq.nonceStr = baseModel.getO().getNoncestr();
        payReq.timeStamp = baseModel.getO().getTimestamp() + "";
        payReq.sign = baseModel.getO().getSign();
        payReq.extData = "app data";
        wxapi.sendReq(payReq);
        Log.e("prepayid", payReq.prepayId + "---");

        Preference.put(StringConstants.PAY_NAME, pay_code);
        Preference.put(StringConstants.OR_DERID, baseModel.getO().getOrder_id());

    }


    @Override
    protected void onInitViewNew() {
        textView_postage = (TextView) findViewById(R.id.textView_postage);
        textView_totalprice = (TextView) findViewById(R.id.textView_totalprice);
        textView_editaddress_coformorder = (TextView) findViewById(R.id.textView_editaddress_coformorder);
        textView_name_comforOrder = (TextView) findViewById(R.id.textView_name_comforOrder);
        textView_phone_comforOrder = (TextView) findViewById(R.id.textView_phone_comforOrder);
        textView_address_comforOrder = (TextView) findViewById(R.id.textView_address_comforOrder);
        imageView_alipay = (ImageView) findViewById(R.id.imageView_alipay);
        imageView_alipay.setBackgroundResource(R.mipmap.is_selected);
        imageView_weixinpay = (ImageView) findViewById(R.id.imageView_weixinpay);
        imageView_weixinpay.setBackgroundResource(R.mipmap.circle_noselected);
        btn_pay_conforder = (Button) findViewById(R.id.btn_pay_conforder);
        belowme = (RelativeLayout) findViewById(R.id.belowme);
        layout_belowme2 = (RelativeLayout) findViewById(R.id.layout_belowme2);
        tomyright2 = (ImageView) findViewById(R.id.tomyright2);
        imageView_tomytight_01 = (ImageView) findViewById(R.id.imageView_tomytight_01);
        relativelayout_goto_address_list = (RelativeLayout) findViewById(R.id.relativelayout_goto_address_list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
