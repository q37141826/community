package com.qixiu.intelligentcommunity.mvp.view.activity.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.Preference;

public class ConfirmCarPayActivity extends BaseActivity {
    TextView textView_input_carNum_car, textView_input_payhaomuch_car;
    RelativeLayout relativeLayout_title_confirmcar;
    Button btn_gotopay_car;
    String carNum, payid, money;

    @Override
    protected void onInitData() {
        carNum = getIntent().getStringExtra("carNum");
        payid = getIntent().getStringExtra("payid");
        money = getIntent().getStringExtra("money");
        textView_input_carNum_car.setText(carNum);
        textView_input_payhaomuch_car.setText(money);
    }

    @Override
    protected void onInitView() {
        textView_input_payhaomuch_car = (TextView) findViewById(R.id.textView_input_payhaomuch_car);
        btn_gotopay_car = (Button) findViewById(R.id.btn_gotopay_car);
        relativeLayout_title_confirmcar = (RelativeLayout) findViewById(R.id.relativeLayout_title_confirmcar);
        textView_input_carNum_car = (TextView) findViewById(R.id.textView_input_carNum_car);
        inittile();
        btn_gotopay_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPay();
            }
        });
    }

    private void gotoPay() {
        Intent intent = new Intent(this, AliWeixinPayActivity.class);
        intent.putExtra("carNum", carNum);
        intent.putExtra("payid", payid);
        intent.putExtra("money", money);
        intent.putExtra("type", 2);
        startActivity(intent);
        //做一个标志
        Preference.put(ConstantString.payWhat, ConfirmCarPayActivity.class.getSimpleName());
    }

    private void inittile() {
        TitleView titleView = new TitleView(this);
        titleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("确认缴纳");
        titleView.setTitle_textColor(getResources().getColor(R.color.white));
        relativeLayout_title_confirmcar.addView(titleView.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_confirm_car_pay;
    }
}
