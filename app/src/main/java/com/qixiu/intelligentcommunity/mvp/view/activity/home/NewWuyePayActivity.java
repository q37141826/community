package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class NewWuyePayActivity extends NewTitleActivity implements OKHttpUIUpdataListener {
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

    @Override
    protected void onInitData() {
        okHttpRequestModel = new OKHttpRequestModel(this);
        requestPayData();
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

        //每个需要显示的数据textview


        pointSelector = new PointSelector(this, ivbtn_yes, ivbtn_no);
        rl_pay_how_long.setOnClickListener(this);
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
            int months = PayTimeDataHelper.getMonths(o.toString());
            setPayMonth(months);
            textView_wuye_pay_time.setText(o.toString());
            Log.d(TAG, "showPick: data = " + o.toString());
        });
        singlePopPickView.show();
    }

    private void setPayMonth(int months) {
        currentPayMonths = months;
    }

    @Override
    public void onSuccess(Object data, int i) {
        if (data instanceof NewWuyePayBean) {
            wuyePayBean = (NewWuyePayBean) data;
            setPayDetails(wuyePayBean);
        }
    }

    private void setPayDetails(NewWuyePayBean wuyePayBean) {
        textView_wuye_mianji.setText(wuyePayBean.getO().getBarea());
        textView_wuye_price.setText(wuyePayBean.getO().getBprice());
        textView_wuye_last_pay_time.setText(wuyePayBean.getO().getEndtime_desc());

    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }
}
