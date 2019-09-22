package com.qixiu.intelligentcommunity.mvp.view.holder.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.Alipay;
import com.alipay.PayResult;
import com.alipay.interf.IPay;
import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.PlatformConfigConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.AliPayBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.WeixinPayModel;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.CheckWhereActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.OrderDetailsActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.EvaluateOrderActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.MyOrderAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.shop.OrderListDetailsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wxpay.WxPay;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class MyOrderHolder extends RecyclerBaseHolder<OrderBean.OBean.ListBean> implements View.OnClickListener, IPay {
    AlertDialog dialog;
    Intent intent;
    RelativeLayout framelayout_myorder;
    OrderListDetailsAdapter adapterl;
    RecyclerView recycleView_myoreder_inner;
    Button btn_giveComments, btn_deleteOrder, btn_payThisOrder, btn_notice_send, btn_checkwhere_list, btn_getConform_list;
    List<Button> buttons = new ArrayList<>();
    TextView textView_order_finish;
    private MyOrderAdapter.MyOrderRefreshListener myOrderRefreshListener;
    //支付参数
    String order_id, pay_code;
    AliPayBean aliPayBean;
    IWXAPI wxapi;
    //弹窗的操作针对谁，是收货还是删除订单？
    boolean IS_DELETE = false;
    private int position;

    public MyOrderHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        recycleView_myoreder_inner = (RecyclerView) itemView.findViewById(R.id.recycleView_myoreder_inner);
        btn_giveComments = (Button) itemView.findViewById(R.id.btn_giveComments);
        btn_deleteOrder = (Button) itemView.findViewById(R.id.btn_deleteOrder);
        btn_payThisOrder = (Button) itemView.findViewById(R.id.btn_payThisOrder);
        btn_notice_send = (Button) itemView.findViewById(R.id.btn_notice_send);
        btn_checkwhere_list = (Button) itemView.findViewById(R.id.btn_checkwhere_list);
        btn_getConform_list = (Button) itemView.findViewById(R.id.btn_getConform_list);
        textView_order_finish = (TextView) itemView.findViewById(R.id.textView_order_finish);
        framelayout_myorder = (RelativeLayout) itemView.findViewById(R.id.relativelayout_myorder);
        buttons.add(btn_giveComments);
        buttons.add(btn_deleteOrder);
        buttons.add(btn_payThisOrder);
        buttons.add(btn_notice_send);
        buttons.add(btn_checkwhere_list);
        buttons.add(btn_getConform_list);
    }

    public void setMyOrderRefreshListener(MyOrderAdapter.MyOrderRefreshListener myOrderRefreshListener) {
        this.myOrderRefreshListener = myOrderRefreshListener;

    }

    @Override
    public void bindHolder(int position) {
        this.position = position;
        adapterl = new OrderListDetailsAdapter();
        //
        adapterl.refreshData(mData.getGoods());
        adapterl.setOnItemClickListener(new OnRecyclerItemListener<OrderBean.OBean.ListBean.GoodsBean>() {
            @Override
            public void onItemClick(View v, OrderBean.OBean.ListBean.GoodsBean data) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("order_id", data.getOrder_id());
                intent.putExtra("order_status", mData.getOrder_status());
                intent.putExtra("ship_status", mData.getShipping_status());
                mContext.startActivity(intent);
            }
        });
        recycleView_myoreder_inner.setLayoutManager(new LinearLayoutManager(mContext));
//        recycleView_myoreder_inner.setOnItemClickListener
        recycleView_myoreder_inner.setAdapter(adapterl);
        btn_giveComments.setOnClickListener(this);
        btn_deleteOrder.setOnClickListener(this);
        btn_payThisOrder.setOnClickListener(this);
        btn_notice_send.setOnClickListener(this);
        framelayout_myorder.setOnClickListener(this);
        btn_checkwhere_list.setOnClickListener(this);
        btn_getConform_list.setOnClickListener(this);
        order_id = mData.getOrder_id();
        pay_code = mData.getPay_code();
        setButtonVisible();
    }

    private void setButtonVisible() {
        for (Button btn : buttons) {
            btn.setVisibility(View.GONE);
        }
        textView_order_finish.setVisibility(View.GONE);
        if (("0").equals(mData.getOrder_status())) {
            btn_deleteOrder.setVisibility(View.VISIBLE);
            btn_payThisOrder.setVisibility(View.VISIBLE);
        } else if ("1".equals(mData.getOrder_status()) && "0".equals(mData.getShipping_status())) {
            btn_notice_send.setVisibility(View.VISIBLE);
        } else if ("1".equals(mData.getOrder_status()) && "1".equals(mData.getShipping_status())) {
            btn_checkwhere_list.setVisibility(View.VISIBLE);
            btn_getConform_list.setVisibility(View.VISIBLE);
        } else if ("2".equals(mData.getOrder_status())) {
            btn_giveComments.setVisibility(View.VISIBLE);
            btn_deleteOrder.setVisibility(View.VISIBLE);
            textView_order_finish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativelayout_myorder:
                break;
            case R.id.btn_giveComments:
                if (mData.getIs_common().equals("1")) {
                } else {
                    ToastUtil.showToast(mContext, "不能重复评论");
                    return;
                }
                intent = new Intent(mContext, EvaluateOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("order_id", mData.getOrder_id());
                bundle.putString("goods_id", mData.getGoods().get(0).getGoods_id() + "");
                bundle.putParcelable("data", (Parcelable) mData);
                intent.putExtras(bundle);
                intent.putExtra("goods_id", mData.getGoods().get(0).getGoods_id() + "");
                intent.putExtra("order_id", mData.getOrder_id());
                mContext.startActivity(intent);
                break;
            case R.id.btn_deleteOrder:
                IS_DELETE = true;
                setDialog("确认删除订单？");
                break;
            case R.id.btn_payThisOrder:
                startPay();
                break;
            case R.id.btn_notice_send:
                if (mData.getIs_deliver().equals("2")) {
                    ToastUtil.showToast(mContext,"您已经提醒发货，请勿重复提醒");
                    return;
                }
                startNotice();
                break;
            case R.id.btn_checkwhere_list:
                Intent intent = new Intent(mContext, CheckWhereActivity.class);
                intent.putExtra("order_id", mData.getOrder_id());
                mContext.startActivity(intent);
                break;
            case R.id.btn_getConform_list:
                IS_DELETE = false;
                setDialog("确认收货吗？");
                break;

//            case R.id.btn_sure:
//                //// TODO: 2017/5/23 删除订单接口和确认收货的判断
//                if (IS_DELETE) {
//                    startDeleteOrder();
//                } else {
//                    startGetGoods();
//                }
//                dialog.dismiss();
//                break;
//            case R.id.btn_cancel:
//                dialog.dismiss();
//                break;
        }
    }

    private void startPay() {
        String token = MD5Util.getToken(ConstantUrl.OrderPayTag);
        OkHttpUtils.post().url(ConstantUrl.OrderPayUrl)
                .addParams("token", token)
//                .addParams("userId", Preference.get(StringConstantsUrl.STRING_ID, ""))
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
//                        aliPayBean = gson.fromJson(testbean.jsonStr, AliPayBean.class);
//                        ToastUtil.showToast(mContext, aliPayBean.getM());
                        new Alipay((Activity) mContext, MyOrderHolder.this).startPay(aliPayBean.getO().getAlipay());
                    } else {
                        WeixinPayModel weixinPayModel = gson.fromJson(s, WeixinPayModel.class);
                        startWeixinPay(weixinPayModel);
                        WxPay.payOrderPosition = position;
                    }
                } catch (Exception e) {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(mContext,messageBean.getM());

                }
            }
        });
    }

    private void startNotice() {
        String token = MD5Util.getToken(ConstantUrl.NoticeSendTag);
        OkHttpUtils.post().url(ConstantUrl.NoticeSendUrl)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("token", token)
                .addParams("order_sn", mData.getOrder_sn()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC() == 1) {
                    ToastUtil.showToast(mContext, "提醒成功");
                } else {
                    ToastUtil.showToast(mContext, "您已提醒发货，请勿重复提醒");
                }
            }
        });

    }

    private void startDeleteOrder() {
        String token = MD5Util.getToken(ConstantUrl.DeleteOrederTag);
        OkHttpUtils.post().url(ConstantUrl.DeleteOrederUrl)
                .addParams("order_sn", mData.getOrder_sn())
                .addParams("token", token)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    Gson gson = GetGson.init();
                    MessageBean messageBean = gson.fromJson(s, MessageBean.class);
                    if (messageBean.getC() == 1) {
                        ToastUtil.showToast(mContext, "删除成功");
                        myOrderRefreshListener.onOrderRefresh(mData);
                    } else {
                        ToastUtil.showToast(mContext, "删除失败");
                    }
                } catch (Exception e) {

                }
            }
        });

    }


//    private void setDialogView(String message) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_view, null);
//        view.findViewById(R.id.btn_sure).setOnClickListener(this);
//        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
//        TextView textMessage = (TextView) view.findViewById(R.id.textView_dialog_message);
//        textMessage.setText(message);
//        view.getBackground().setAlpha(150);
//        dialog = new AlertDialog.Builder(mContext)
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
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
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
    private void startGetGoods() {
        String token = MD5Util.getToken(ConstantUrl.ConformTakeGoodsTag);
        OkHttpUtils.post().url(ConstantUrl.ConformTakeGoodsUrl)
                .addParams("userId", Preference.get(StringConstants.STRING_ID, ""))
                .addParams("token", token)
                .addParams("order_sn", mData.getOrder_sn()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                MessageBean messageBean = GetGson.parseMessageBean(s);
                if (messageBean.getC() == 1) {
                    ToastUtil.showToast(mContext, "已经确认收货");
                    myOrderRefreshListener.onOrderRefresh(mData);
                } else {
                    ToastUtil.showToast(mContext, "确认收货失败");
                }
            }
        });
    }

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
                ToastUtil.showToast(mContext,"网络错误");
            }

            @Override
            public void onResponse(String s, int i) {
//                MessageBean messageBean = GetGson.parseMessageBean(s);
//                ToastUtil.showToast(mContext, messageBean.getM());
                myOrderRefreshListener.onOrderRefresh(mData);
            }
        });

    }

    @Override
    public void onFailure(PayResult payResult) {

    }


    private void startWeixinPay(WeixinPayModel baseModel) {
        wxapi = WXAPIFactory.createWXAPI(mContext, PlatformConfigConstant.WEIXIN_APP_ID);
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
        if(payReq.prepayId.equals("")){
            ToastUtil.showToast(mContext,"支付失败,请联系客服");
        }
        Log.e("prepayid", payReq.prepayId + "---");
        Preference.put(StringConstants.PAY_NAME, pay_code);
        Preference.put(StringConstants.OR_DERID, baseModel.getO().getOrder_id());

    }

}
