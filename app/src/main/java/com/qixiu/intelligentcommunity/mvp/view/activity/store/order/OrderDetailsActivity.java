package com.qixiu.intelligentcommunity.mvp.view.activity.store.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AliPayBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderDetailsBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.WeixinPayModel;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.OrderDetailsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderDetailsActivity extends NewTitleActivity implements View.OnClickListener, IPay {
    private boolean IS_DELEVER = false;
    private OrderDetailsAdapter adapter;
    //订单的状态参数
    private String order_id = "", order_status = "", ship_status = "";
    private Button btn_deleteOrder_detail, btn_payOrder_detail, btn_notice_send_detail, btn_checkwhere_detail, btn_getConform_detail;
    private RecyclerView recycleView_goods_in_order;
    private List<Button> buttons = new ArrayList<>();
    private TextView textView_getgoodspeople_address_detail, textView_getgoodsphone_address_detail, textView_getgoodsaddress_address_detail,
            textView_orderid_detail, textView_goodstotolprice_detail, textView_tranceportPrice_detail, textView_ordertotolPrice_detail,
            textView_createTime_orderdetail, textView_paytime_orderdetail, textView_payIsOver;
    OrderDetailsBean orderDetailsBean;
    private AlertDialog dialog;
    //订单支付类型
    String pay_code;
    //微信支付类
    IWXAPI wxapi;
    //支付宝支付回传类
    AliPayBean aliPayBean;
    //判断是删除订单还是确认收货
    boolean IS_DELETE = false;
    @Override
    protected void onInitData() {
        mTitleView.setTitle("订单详情");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        order_id = getIntent().getStringExtra("order_id");
        order_status = getIntent().getStringExtra("order_status");
        ship_status = getIntent().getStringExtra("ship_status");

        //设置删除订单等点击事件
        setClick();
        //隐藏所有button
        setVisble();
        //将请求的订单数据放入控件
        setData();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_order_details;
    }

    private void setData() {
        String token = MD5Util.getToken(ConstantUrl.OrederDetailTag);
        OkHttpUtils.post().url(ConstantUrl.OrederDetailUrl).addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("order_id", order_id)
                .addParams("token", token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = GetGson.init();
                try {
                    orderDetailsBean = gson.fromJson(s, OrderDetailsBean.class);
                    textView_getgoodspeople_address_detail.setText(orderDetailsBean.getO().getOrder().getConsignee());
                    textView_getgoodsphone_address_detail.setText(orderDetailsBean.getO().getOrder().getMobile());
                    textView_getgoodsaddress_address_detail.setText(orderDetailsBean.getO().getOrder().getProvince()+orderDetailsBean.getO().getOrder().getCity()+orderDetailsBean.getO().getOrder().getDistrict()+orderDetailsBean.getO().getOrder().getAddress());

                    textView_orderid_detail.setText(orderDetailsBean.getO().getOrder().getOrder_sn());
                    textView_goodstotolprice_detail.setText("¥ " + orderDetailsBean.getO().getOrder().getGoods_price());
                    textView_tranceportPrice_detail.setText("¥ " + orderDetailsBean.getO().getOrder().getShipping_price());
                    textView_ordertotolPrice_detail.setText("¥ " + orderDetailsBean.getO().getOrder().getTotal_amount());

                    textView_createTime_orderdetail.setText(orderDetailsBean.getO().getOrder().getAdd_time());
                    textView_paytime_orderdetail.setText(orderDetailsBean.getO().getOrder().getPay_time());
                    //设置商品列表
                    adapter = new OrderDetailsAdapter();
                    recycleView_goods_in_order.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
                    recycleView_goods_in_order.setAdapter(adapter);
                    adapter.refreshData(orderDetailsBean.getO().getGoods());
                    if ("2".equals(orderDetailsBean.getO().getOrder().getIs_deliver())) {
                        //如果已经提醒， 设置提醒发货的按钮状态
                        setBtnNoticeState();
                    }
                    pay_code = orderDetailsBean.getO().getOrder().getPay_code();
                } catch (Exception e) {
                }
            }
        });
    }

    private void setBtnNoticeState() {
        IS_DELEVER = true;
//        btn_notice_send_detail.setEnabled(false);
        btn_notice_send_detail.setBackgroundResource(R.drawable.sharp_background_btn_grey);
    }

    private void setVisble() {
        for (Button btn :
                buttons) {
            btn.setVisibility(View.GONE);
        }
        textView_payIsOver.setVisibility(View.GONE);
        if ("0".equals(order_status)) {
            btn_deleteOrder_detail.setVisibility(View.VISIBLE);
            btn_payOrder_detail.setVisibility(View.VISIBLE);
        } else if ("1".equals(order_status) && "0".equals(ship_status)) {
            btn_notice_send_detail.setVisibility(View.VISIBLE);
        } else if ("1".equals(order_status) && "1".equals(ship_status)) {
            btn_checkwhere_detail.setVisibility(View.VISIBLE);
            btn_getConform_detail.setVisibility(View.VISIBLE);
        } else if ("2".equals(order_status)) {
            textView_payIsOver.setVisibility(View.VISIBLE);
            textView_payIsOver.setText("交易完成");
            textView_payIsOver.setTextColor(getResources().getColor(R.color.red));
        }

    }

    private void setClick() {
        btn_deleteOrder_detail.setOnClickListener(this);
        btn_payOrder_detail.setOnClickListener(this);
        btn_notice_send_detail.setOnClickListener(this);
        btn_checkwhere_detail.setOnClickListener(this);
        btn_getConform_detail.setOnClickListener(this);
        buttons.add(btn_deleteOrder_detail);
        buttons.add(btn_payOrder_detail);
        buttons.add(btn_notice_send_detail);
        buttons.add(btn_checkwhere_detail);
        buttons.add(btn_getConform_detail);


    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_deleteOrder_detail:
                IS_DELETE = true;
                setDialog("确认删除订单？");
                break;
            case R.id.btn_payOrder_detail:
                startpay();
                break;
            case R.id.btn_notice_send_detail:
                startNotice();
                break;
            case R.id.btn_checkwhere_detail:
                Intent intent = new Intent(this, CheckWhereActivity.class);
                intent.putExtra("order_id", orderDetailsBean.getO().getOrder().getOrder_id());
                startActivity(intent);
                break;
            case R.id.btn_getConform_detail:
                IS_DELETE = false;
                setDialog("确认收货吗？");
                break;

        }
    }

    private void startpay() {
        String token = MD5Util.getToken(ConstantUrl.OrderPayTag);
        OkHttpUtils.post().url(ConstantUrl.OrderPayUrl)
                .addParams("token", token)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("order_id", order_id)
                .addParams("pay_code", pay_code).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                Gson gson = GetGson.init();
                try {
                    if (pay_code.equals("alipay")) {
                        aliPayBean = gson.fromJson(s, AliPayBean.class);
//                        ToastUtil.showToast(OrderDetailsActivity.this, aliPayBean.getM());
                        new Alipay(OrderDetailsActivity.this, OrderDetailsActivity.this).startPay(aliPayBean.getO().getAlipay());
                    } else {
                        WeixinPayModel weixinPayModel = gson.fromJson(s, WeixinPayModel.class);
                        startWeixinPay(weixinPayModel);
                    }
                } catch (Exception e) {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(OrderDetailsActivity.this, messageBean.getM());
                }
            }
        });


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

    private void startGetGoods() {
        String token = MD5Util.getToken(ConstantUrl.ConformTakeGoodsTag);
        OkHttpUtils.post().url(ConstantUrl.ConformTakeGoodsUrl)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("token", token)
                .addParams("order_sn", orderDetailsBean.getO().getOrder().getOrder_sn()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC() == 1) {
                    ToastUtil.showToast(OrderDetailsActivity.this, "已经确认收货");
                    sendBroadcast(new Intent(IntentDataKeyConstant.BROADCAST_COMMENTS_OK));
                    finish();
                } else {
                    ToastUtil.showToast(OrderDetailsActivity.this, "确认收货失败");
                }
            }
        });
    }

    private void startNotice() {
        if (orderDetailsBean.getO().getOrder().getIs_deliver().equals("2")) {
            ToastUtil.showToast(OrderDetailsActivity.this, "您已提醒发货，请勿重复提醒");
            return;
        }
        String token = MD5Util.getToken(ConstantUrl.NoticeSendTag);
        OkHttpUtils.post().url(ConstantUrl.NoticeSendUrl)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("token", token)
                .addParams("order_sn", orderDetailsBean.getO().getOrder().getOrder_sn()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC() == 1) {
                    btn_notice_send_detail.setBackgroundResource(R.drawable.sharp_background_btn_grey);
                    ToastUtil.showToast(OrderDetailsActivity.this, "提醒成功");

                } else {
                    ToastUtil.showToast(OrderDetailsActivity.this, "您已提醒发货，请勿重复提醒");
                }
            }
        });
    }

    private void startDeleteOrder() {
        String token = MD5Util.getToken(ConstantUrl.DeleteOrederTag);
        OkHttpUtils.post().url(ConstantUrl.DeleteOrederUrl)
                .addParams("order_sn", orderDetailsBean.getO().getOrder().getOrder_sn())
                .addParams("token", token)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC() == 1) {
                    ToastUtil.showToast(OrderDetailsActivity.this, "删除成功");
                    Intent intent =new Intent(IntentDataKeyConstant.BROADCAST_DELETE_OK);
                    intent.putExtra(ConstantString.USERID, orderDetailsBean.getO().getOrder().getOrder_sn());
                    sendBroadcast(intent);
                    finish();
                } else {
                    ToastUtil.showToast(OrderDetailsActivity.this, "删除失败");
                }
            }
        });
    }

//    private void setDialogView(String message) {
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
//        view.findViewById(R.id.btn_sure).setOnClickListener(this);
//        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
//        TextView textMessage = (TextView) view.findViewById(R.id.textView_dialog_message);
//        textMessage.setText(message);
//        view.getBackground().setAlpha(150);
//        dialog = new AlertDialog.Builder(this)
//                .setView(view)
//                .create();
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == event.KEYCODE_BACK) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//        dialog.show();
//    }

    private void setDialog(String message) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(this);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(IS_DELETE){
                    startDeleteOrder();
                }else {
                    startGetGoods();
                }
                dialog.dismiss();
            }
        });

        builder.setMessage(message);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    //支付宝支付成功
    @Override
    public void onSuccess(String msg) {
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
                ToastUtil.showToast(OrderDetailsActivity.this, "网络错误");
            }

            @Override
            public void onResponse(String s, int i) {
//                MessageBean messageBean = GetGson.parseMessageBean(s);
//                ToastUtil.showToast(OrderDetailsActivity.this, messageBean.getM());
            }
        });
    }

    @Override
    public void onFailure(PayResult payResult) {

    }

    @Override
    protected void onInitViewNew() {
        textView_payIsOver = (TextView) findViewById(R.id.textView_payIsOver);
        textView_createTime_orderdetail = (TextView) findViewById(R.id.textView_createTime_orderdetail);
        textView_paytime_orderdetail = (TextView) findViewById(R.id.textView_paytime_orderdetail);
        textView_getgoodsaddress_address_detail = (TextView) findViewById(R.id.textView_getgoodsaddress_address_detail);
        textView_ordertotolPrice_detail = (TextView) findViewById(R.id.textView_ordertotolPrice_detail);
        textView_tranceportPrice_detail = (TextView) findViewById(R.id.textView_tranceportPrice_detail);
        textView_goodstotolprice_detail = (TextView) findViewById(R.id.textView_goodstotolprice_detail);

        textView_orderid_detail = (TextView) findViewById(R.id.textView_orderid_detail);
        textView_getgoodsphone_address_detail = (TextView) findViewById(R.id.textView_getgoodsphone_address_detail);
        textView_getgoodspeople_address_detail = (TextView) findViewById(R.id.textView_getgoodspeople_address_detail);
        btn_deleteOrder_detail = (Button) findViewById(R.id.btn_deleteOrder_detail);
        btn_payOrder_detail = (Button) findViewById(R.id.btn_payOrder_detail);
        btn_notice_send_detail = (Button) findViewById(R.id.btn_notice_send_detail);
        btn_checkwhere_detail = (Button) findViewById(R.id.btn_checkwhere_detail);
        btn_getConform_detail = (Button) findViewById(R.id.btn_getConform_detail);
        recycleView_goods_in_order = (RecyclerView) findViewById(R.id.recycleView_goods_in_order);
    }
}
