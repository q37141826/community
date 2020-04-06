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
import com.qixiu.intelligentcommunity.mvp.beans.home.PayTimeDataHelper;
import com.qixiu.intelligentcommunity.mvp.model.home_modle.PointSelector;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.SinglePopPickView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;

import java.util.List;

public class NewWuyePayActivity extends NewTitleActivity {
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

    //哥哥view
    private TextView textView_wuye_pay_time;

    @Override
    protected void onInitData() {

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

}
