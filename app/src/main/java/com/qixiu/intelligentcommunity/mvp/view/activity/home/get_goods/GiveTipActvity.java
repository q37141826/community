package com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.AliWeixinPayActivity;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

public class GiveTipActvity extends NewTitleActivity implements TextWatcher {
    private EditText edittext_howmuchTip;
    private TextView textView_show_bold;
    private Button btn_giveTip;
    private String payid = "";
    //接受打赏成功的广播
    private BroadcastReceiver receiver;

    @Override
    protected void onInitViewNew() {
        mTitleView.setTitle("打赏");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edittext_howmuchTip = (EditText) findViewById(R.id.edittext_howmuchTip);
        textView_show_bold = (TextView) findViewById(R.id.textView_show_bold);
        edittext_howmuchTip.addTextChangedListener(this);
        btn_giveTip = (Button) findViewById(R.id.btn_giveTip);
        btn_giveTip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_giveTip:
                String money = edittext_howmuchTip.getText().toString();
                if (TextUtils.isEmpty(money)) {
                    ToastUtil.toast("请输入打赏金额");
                    return;
                }
                try {
                    if (Double.parseDouble(money) <= 0) {
                        ToastUtil.toast("打赏金额不能为零");
                        return;
                    }
                } catch (Exception e) {
                }
                Intent intent = new Intent(this, AliWeixinPayActivity.class);
                intent.putExtra(ConstantString.TITLE_NAME, "打赏");
                intent.putExtra("type", 1);
                intent.putExtra("payid", payid);
                intent.putExtra("money", money);
                intent.putExtra(ConstantString.FROM_WHERE, GiveTipActvity.class.getSimpleName());
                startActivity(intent);
                Preference.put(ConstantString.payWhat, GiveTipActvity.class.getSimpleName());
                break;
        }
    }

    @Override
    protected void onInitData() {
        try {
            payid = getIntent().getStringExtra("payid");
        } catch (Exception e) {
        }
        IntentFilter filter = new IntentFilter(ConstantString.broadCastFinishPay);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_give_tip_actvity;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s)) {
            textView_show_bold.setText("¥" + "0.00");
        } else {
            textView_show_bold.setText("¥" + s + ".00");
        }
        if (s.toString().contains(".")) {
            textView_show_bold.setText("¥" + s);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
