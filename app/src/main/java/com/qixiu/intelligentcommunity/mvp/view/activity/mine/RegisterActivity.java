package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.GoToActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.web.WebActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.CheckStringUtils;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class RegisterActivity extends TitleActivity implements TextWatcherAdapterInterface, View.OnFocusChangeListener {


    private EditText mEt_retist_phone;
    private EditText mEt_checkcode;
    private EditText mEt_regist_password;
    private Button mBt_send_checkcode;
    private Button mBt_regist;
    private IntelligentTextWatcher mRegsitPhoneTextWatcher;
    private IntelligentTextWatcher mRegsitPasswordTextWatcher;
    private View mRl_clean_input_phone;
    private View mRl_clean_input_password;
    private ZProgressHUD zProgressHUD;
    private ImageButton imagebt_rules;
    private TextView textViewRules;
    private boolean isAgreeRules = false;

    @Override
    protected void onInitView() {
        super.onInitView();
        zProgressHUD = new ZProgressHUD(this);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setTextColor(getResources().getColor(R.color.white));
        tv_title.setText(R.string.regist_txt);
        if (ib_back != null) {
            ib_back.setImageResource(R.mipmap.back_white);

        }
        if (mTv_more != null) {
            mTv_more.setVisibility(View.GONE);
        }
        mEt_retist_phone = (EditText) findViewById(R.id.et_retist_phone);
        mEt_checkcode = (EditText) findViewById(R.id.et_checkcode);
        mEt_regist_password = (EditText) findViewById(R.id.et_regist_password);
        mBt_send_checkcode = (Button) findViewById(R.id.bt_send_checkcode);
        mBt_regist = (Button) findViewById(R.id.bt_regist);
        mRl_clean_input_phone = findViewById(R.id.rl_clean_input_phone);
        mRl_clean_input_password = findViewById(R.id.rl_clean_input_password);

        imagebt_rules = findViewById(R.id.imagebt_rules);
        textViewRules = findViewById(R.id.textViewRules);
        refreshSelectRuleState(isAgreeRules);
        iniListener();
    }

    private void iniListener() {
        mRl_clean_input_phone.setOnClickListener(this);
        mRl_clean_input_password.setOnClickListener(this);
        mBt_send_checkcode.setOnClickListener(this);
        mBt_regist.setOnClickListener(this);
        mRegsitPhoneTextWatcher = new IntelligentTextWatcher(mEt_retist_phone.getId(), this);
        mRegsitPhoneTextWatcher.setEmojiDisabled(true, mEt_retist_phone);
        mRegsitPasswordTextWatcher = new IntelligentTextWatcher(mEt_regist_password.getId(), this);
        mRegsitPasswordTextWatcher.setEmojiDisabled(true, mEt_regist_password);
        mEt_retist_phone.addTextChangedListener(mRegsitPhoneTextWatcher);
        mEt_regist_password.addTextChangedListener(mRegsitPasswordTextWatcher);
        mEt_retist_phone.setOnFocusChangeListener(this);
        mEt_regist_password.setOnFocusChangeListener(this);
        mEt_checkcode.setOnFocusChangeListener(this);
        imagebt_rules.setOnClickListener(this);
        textViewRules.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_regist;
    }

    @Override
    protected void onOtherClick(View view) {
        switch (view.getId()) {
            case R.id.rl_clean_input_phone:
                mEt_retist_phone.setText(StringConstants.EMPTY_STRING);
                break;
            case R.id.rl_clean_input_password:
                mEt_regist_password.setText(StringConstants.EMPTY_STRING);
                break;
            case R.id.bt_send_checkcode:
                String phoneText = mEt_retist_phone.getText().toString();
                if (TextUtils.isEmpty(phoneText)) {
                    ToastUtil.toast(R.string.phone_notnull);
                    return;
                }
                if (!CheckStringUtils.isMobileNO(phoneText)) {
                    ToastUtil.toast(R.string.phone_format_error);
                    return;
                }

                //发送验证码功能
                sendCode(phoneText, this, 1 + "", mBt_send_checkcode);
                break;
            case R.id.bt_regist:
                if (!hasPermission(photoPermission)) {

                    hasRequse(1, photoPermission);
                    return;
                }
                if (TextUtils.isEmpty(mEt_retist_phone.getText().toString())) {
                    ToastUtil.toast(R.string.phone_notnull);
                    return;
                }
                if (!CommonUtils.isMobileNO(mEt_retist_phone.getText().toString())) {
                    ToastUtil.toast("手机号格式不正确");
                    return;
                }
                if (TextUtils.isEmpty(mEt_checkcode.getText().toString())) {
                    ToastUtil.toast(R.string.checkcode_notnull);
                    return;
                }
                if (verify_id == null || TextUtils.isEmpty(verify_id)) {
                    ToastUtil.toast("请先发送验证码");
                    return;
                }

                if (TextUtils.isEmpty(mEt_regist_password.getText().toString())) {
                    ToastUtil.toast(R.string.pasword_notnull);
                    return;
                }
                if ((mEt_regist_password.getText().toString()).length() < 6) {
                    ToastUtil.toast("请输入长度为6-16的密码");
                    return;
                }
                if ((mEt_regist_password.getText().toString()).length() > 16) {
                    ToastUtil.toast("密码不超过16位字符");
                    return;
                }

//                Intent intent = new Intent(this, AuthorizationActivity.class);
//                startActivity(intent);
                //注册功能

                if (!isAgreeRules) {
                    ToastUtil.toast("请阅读并同意隐私政策");
                }
                startRegister();
                break;

            case R.id.imagebt_rules:
                setImagebt();
                break;

            case R.id.textViewRules:
                GotoWeb();
                break;

        }
    }


    private void GotoWeb() {
        Intent intent =new Intent(this,GoToActivity.class);
        intent.putExtra(ConstantString.TITLE_NAME,"隐私政策");
        intent.putExtra(ConstantString.URL,ConstantUrl.priviteUrl);
        startActivity(intent);
    }

    private void setImagebt() {
        isAgreeRules = !isAgreeRules;
        refreshSelectRuleState(isAgreeRules);
    }

    private void refreshSelectRuleState(boolean isAgreeRules) {
        imagebt_rules.setImageResource(!isAgreeRules ? R.mipmap.wuye_point_no_selected_2x : R.mipmap.wuye_point_selected_2x);
    }

    private void startRegister() {
        zProgressHUD.show();
        OkHttpUtils.post().url(ConstantUrl.registerUrl)
                .addParams("phone", mEt_retist_phone.getText().toString().trim())
                .addParams("verify_id", verify_id.trim())
                .addParams("verify", mEt_checkcode.getText().toString().trim()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                zProgressHUD.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                Log.e("ss", s);
                zProgressHUD.dismiss();
                try {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(getApplicationContext(), messageBean.getM());
                    if (messageBean.getC() == 0) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", mEt_retist_phone.getText().toString());
                    bundle.putString("password", MD5Util.MD5(mEt_regist_password.getText().toString()));
                    Preference.put(ConstantString.PHONE, mEt_retist_phone.getText().toString());
                    Preference.put(ConstantString.PASSWORD, MD5Util.MD5(mEt_regist_password.getText().toString()));
                    CommonUtils.startIntent(RegisterActivity.this, AuthorizationActivity.class, bundle, 100);
                } catch (Exception e) {

                }
            }
        });


    }

    @Override
    protected void onTitleRightClick() {

    }

    @Override
    protected void onBackClick() {
        finish();
    }

    @Override
    protected void onInitData() {
        textViewRules.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {

        switch (editTextId) {
            case R.id.et_retist_phone:
                int phoneShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_input_phone.getVisibility() != phoneShowWay) {
                    mRl_clean_input_phone.setVisibility(phoneShowWay);
                }

                break;
            case R.id.et_regist_password:
                int passwordShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_input_password.getVisibility() != passwordShowWay) {
                    mRl_clean_input_password.setVisibility(passwordShowWay);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEt_retist_phone != null) {
            mEt_retist_phone.removeTextChangedListener(mRegsitPhoneTextWatcher);
            mRegsitPhoneTextWatcher = null;
        }
        if (mEt_regist_password != null) {
            mEt_regist_password.removeTextChangedListener(mRegsitPasswordTextWatcher);
            mRegsitPasswordTextWatcher = null;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_retist_phone:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_input_password.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_password.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_retist_phone.getText().toString())) {
                    mRl_clean_input_phone.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.et_regist_password:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_input_phone.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_phone.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_regist_password.getText().toString())) {
                    mRl_clean_input_password.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.et_checkcode:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_input_phone.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_phone.setVisibility(View.GONE);
                }
                if (mRl_clean_input_password.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_password.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            setResult(100);
            finish();
        }
    }
}
