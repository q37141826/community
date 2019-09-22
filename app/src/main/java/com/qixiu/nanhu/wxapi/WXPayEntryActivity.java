package com.qixiu.nanhu.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.PlatformConfigConstant;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.PayRecordActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods.GiveTipActvity;
import com.qixiu.intelligentcommunity.mvp.view.activity.store.order.ConfirmOrderActivity;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.select_dialog_item);
        api = WXAPIFactory.createWXAPI(this, PlatformConfigConstant.WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {


    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Intent intent = new Intent(PlatformConfigConstant.PAY_ACTION);
            if (resp.errCode == Integer.valueOf(PlatformConfigConstant.WXPAY_SUCCESS)) {

                intent.putExtra(PlatformConfigConstant.WXPAY_SUCCESS_KEY, PlatformConfigConstant.WXPAY_SUCCESS);


                //成功应该处理的事情
                //自己调用自己家的接口,告诉卖东西的，老子付款成功了，赶紧把状态改成已经付款，否则他就不承认你付了钱
                startNoticeMarket(resp,true);
            } else if (resp.errCode == Integer.valueOf(PlatformConfigConstant.WXPAY_FAILURE)) {
                intent.putExtra(PlatformConfigConstant.WXPAY_FAILURE_KEY, PlatformConfigConstant.WXPAY_FAILURE);
                startNoticeMarket(resp,false);
            } else if (resp.errCode == Integer.valueOf(PlatformConfigConstant.WXPAY_CANCEL)) {
                intent.putExtra(PlatformConfigConstant.WXPAY_CANCEL_KEY, PlatformConfigConstant.WXPAY_CANCEL);
                startNoticeMarket(resp,false);
            }
            sendOrderedBroadcast(intent, null);
        }
        finish();
    }

    private void startNoticeMarket(BaseResp resp, boolean succcess) {
        String payWhat = Preference.get(ConstantString.payWhat, "");
        if (payWhat.equals(GiveTipActvity.class.getSimpleName())||payWhat.equals(ConfirmOrderActivity.class.getSimpleName())) {
            Intent intent = new Intent();
            intent.setAction(IntentDataKeyConstant.BROADCAST_CONFIRM_FINISH);
            sendBroadcast(intent);
        } else {
            if(succcess){
                Bundle bundle = new Bundle();
                //type是本项目中车费类型
                bundle.putInt("type", Preference.get(ConstantString.IS_WUYE_OR_CAR, 1));
                CommonUtils.startIntent(getApplicationContext(), PayRecordActivity.class, bundle);
            }
        }
    }

}