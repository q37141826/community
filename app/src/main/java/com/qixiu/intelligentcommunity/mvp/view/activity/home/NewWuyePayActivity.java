package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.NewWuyePayBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.PayTimeDataHelper;
import com.qixiu.intelligentcommunity.mvp.model.home_modle.PointSelector;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.SinglePopPickView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.TimeDataUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class NewWuyePayActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean> {
    public static final String INTRODUCE_ONE = "说明：您的可用积分为";
    public static final String INTRODUCE_TWO = "个，";
    public static final String INTRODUCE_THREE = "积分=1元。积分可以通过商城购买获得";
    public static final String TITLE = "物业缴费";
    public static final String TAG = "NewWuyePayActivity";
    public static final String TITLE_RIGHT = "缴费记录";
    //传给下个界面的参数
    int type = 1;
    private ImageButton ivbtn_yes;
    private ImageButton ivbtn_no;
    private RelativeLayout rl_pay_how_long;
    private PointSelector pointSelector;
    private SinglePopPickView singlePopPickView;
    private int currentPayMonths;

    private OKHttpRequestModel okHttpRequestModel;


    //哥哥view
    private TextView textView_wuye_pay_time;
    private TextView textView_wuye_mianji;
    private TextView textView_wuye_price;
    private TextView textView_wuye_last_pay_time;
    private NewWuyePayBean wuyePayBean;
    private TextView extView_introduce_point;
    private TextView textView_wuye_this_time_pay;
    private TextView textView_wuye_pay_how_much;
    private EditText edittext_use_point;

    @Override
    protected void onInitData() {
        okHttpRequestModel = new OKHttpRequestModel(this);
        requestPayData();
        refreshEdittext();
    }

    private void refreshEdittext() {
        edittext_use_point.setEnabled(pointSelector.isSelectOk() ? true : false);
    }

    private void requestPayData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", Preference.get(ConstantString.USERID, ""));
        okHttpRequestModel.okhHttpPost(ConstantUrl.newWuyePayDetailsUrl, map, new NewWuyePayBean());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_new_wuye_pay;
    }

    @Override
    protected void onInitViewNew() {
        mTitleView.setTitle(TITLE);
        mTitleView.setRightText(TITLE_RIGHT);
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("type", type);
            CommonUtils.startIntent(NewWuyePayActivity.this, PayRecordActivity.class, bundle);
        });
        findView();
        mTitleView.setBackListener(v -> {
            finish();
        });
    }

    private void findView() {
        ivbtn_yes = findViewById(R.id.ivbtn_yes);
        ivbtn_no = findViewById(R.id.ivbtn_no);
        rl_pay_how_long = findViewById(R.id.rl_pay_how_long);
        textView_wuye_pay_time = findViewById(R.id.textView_wuye_pay_time);
        textView_wuye_mianji = findViewById(R.id.textView_wuye_mianji);
        textView_wuye_price = findViewById(R.id.textView_wuye_price);
        textView_wuye_last_pay_time = findViewById(R.id.textView_wuye_last_pay_time);
        textView_wuye_this_time_pay = findViewById(R.id.textView_wuye_this_time_pay);
        extView_introduce_point = findViewById(R.id.textView_introduce_point);
        textView_wuye_pay_how_much = findViewById(R.id.textView_wuye_pay_how_much);
        edittext_use_point = findViewById(R.id.edittext_use_point);
        //每个需要显示的数据textview


        pointSelector = new PointSelector(this, ivbtn_yes, ivbtn_no);
        rl_pay_how_long.setOnClickListener(this);
        pointSelector.setSelectedListenner(() -> refreshEdittext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_pay_how_long:
                showPick();
                break;
        }
    }

    private void showPick() {
        if (singlePopPickView != null) {
            singlePopPickView.show();
            return;
        }
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_single_picker, null);
        List<String> timeList = PayTimeDataHelper.getTimeList();
        singlePopPickView = new SinglePopPickView(this, contentView, timeList, o -> {
            setSelectedPayState(o);
        });
        singlePopPickView.show();
    }

    private void setSelectedPayState(Object o) {
        int months = PayTimeDataHelper.getMonths(o.toString());
        long seconds = PayTimeDataHelper.getSeconds(o.toString());
        setPayMonth(months);
        textView_wuye_pay_time.setText(o.toString());
        if (wuyePayBean != null) {
            long time = wuyePayBean.getO().getLast_wuye_endtime();
            textView_wuye_this_time_pay.setText(TimeDataUtil.timeStamp2Date((time + seconds) + "", "yyyy年MM月dd日"));
            currentPayMonths = months;
            textView_wuye_pay_how_much.setText(months * wuyePayBean.getO().getYearprice() / 12 + "元");
        }
        Log.d(TAG, "showPick: data = " + o.toString());
    }

    private void setPayMonth(int months) {
        currentPayMonths = months;
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        if (data instanceof NewWuyePayBean) {
            wuyePayBean = (NewWuyePayBean) data;
            setPayDetails(wuyePayBean);
            setSelectedPayState(PayTimeDataHelper.PAYTIME_ONE_YEAR);
        }
        if (data.getUrl().equals(ConstantUrl.newWuyePayCreatOrderUrl)) {
            String payid = data.getO().toString();
            Intent intent = new Intent(this, AliWeixinPayActivity.class);
            //做个付款类型标记
            Preference.put(ConstantString.payWhat, WuyePayActivity.class.getSimpleName());
            intent.putExtra("type", type);
            intent.putExtra("money", currentPayMonths*wuyePayBean.getO().getYearprice() / 12+"");
            intent.putExtra("payid", payid);
            startActivity(intent);
        }
    }

    private void setPayDetails(NewWuyePayBean wuyePayBean) {
        textView_wuye_mianji.setText(wuyePayBean.getO().getBarea());
        textView_wuye_price.setText(wuyePayBean.getO().getBprice());
        textView_wuye_last_pay_time.setText(wuyePayBean.getO().getEndtime_desc());
        setInroduce(wuyePayBean);
    }

    private void setInroduce(NewWuyePayBean wuyePayBean) {
        String inter = wuyePayBean.getO().getInter();
        int score_to_money = wuyePayBean.getO().getScore_to_money();
        String inroduce = INTRODUCE_ONE + inter + INTRODUCE_TWO + score_to_money + INTRODUCE_THREE;
        int i = inroduce.indexOf(inter);
        Spannable spannable = new SpannableString(inroduce);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        ForegroundColorSpan foregroundColorSpanAll = new ForegroundColorSpan(getResources().getColor(R.color.wuye_btn_color));
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.text_orange));
        spannable.setSpan(underlineSpan, i, i + inter.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(foregroundColorSpanAll, 0, inroduce.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(foregroundColorSpan, i, i + inter.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        extView_introduce_point.setText(spannable);
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }
}
