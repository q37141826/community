package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout_title_changgepsw;
    private TextView edittext_phone_changepsw, edittext_code_changepsw, edittext_newPsw_changepsw;
    private Button textView_sendcode_psw, btn_confirm_pswchange;
    private String phone, psw, code;

    @Override
    protected void onInitData() {
        setClick();
    }

    private void setClick() {
        textView_sendcode_psw.setOnClickListener(this);
        btn_confirm_pswchange.setOnClickListener(this);
    }

    @Override
    protected void onInitView() {
        TitleView titleView = new TitleView(this);
        titleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("修改/忘记密码");
        titleView.setTitle_textColor(getResources().getColor(R.color.white));
        relativeLayout_title_changgepsw = (RelativeLayout) findViewById(R.id.relativeLayout_title_changgepsw);
        relativeLayout_title_changgepsw.addView(titleView.getView());
        textView_sendcode_psw = (Button) findViewById(R.id.textView_sendcode_psw);
        edittext_phone_changepsw = (TextView) findViewById(R.id.edittext_phone_changepsw);
        edittext_code_changepsw = (TextView) findViewById(R.id.edittext_code_changepsw);
        edittext_newPsw_changepsw = (TextView) findViewById(R.id.edittext_newPsw_changepsw);
        btn_confirm_pswchange = (Button) findViewById(R.id.btn_confirm_pswchange);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_changepassword;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_sendcode_psw:
                if (getData(true)) {
                    return;
                }
                sendCode(phone, this, 2 + "", textView_sendcode_psw);
                break;
            case R.id.btn_confirm_pswchange:
                if (getData(false)) {
                    return;
                }
                startChangePswd();
                break;

        }
    }

    private void startChangePswd() {
        OkHttpUtils.post().url(ConstantUrl.lostPswdUrl)
//                .addParams("id", Preference.get(ConstantString.USERID, ""))
                .addParams("phone", phone)
                .addParams("verify", code)
                .addParams("verify_id", verify_id)
                .addParams("password", MD5Util.MD5(psw)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    if (messageBean.getM().contains("verify")) {
                        ToastUtil.showToast(ChangePasswordActivity.this, "验证码不正确");
                        return;
                    }
                    if (messageBean.getC() == 1) {
                        ToastUtil.showToast(ChangePasswordActivity.this, messageBean.getM());
                        CommonUtils.startIntent(ChangePasswordActivity.this,LoginActivity.class);
                        Preference.clearAllFlag();
                        Preference.putBoolean(ConstantString.IS_FIRST_LOGIN,true);
                        ChangePasswordActivity.this.finish();
                    }
                    ToastUtil.showToast(ChangePasswordActivity.this, messageBean.getM());
                } catch (Exception e) {
                }
            }
        });


    }

    public boolean getData(boolean IS_SENDCODE) {
        phone = edittext_phone_changepsw.getText().toString();
        if (phone.equals("")) {
            ToastUtil.showToast(this, "请输入电话号码");
            return true;
        }
        if (!CommonUtils.isMobileNO(phone)) {
            ToastUtil.showToast(this, "请输入正确的电话号码");
            return true;
        }
        if (IS_SENDCODE) {
            return false;
        }
        code = edittext_code_changepsw.getText().toString();
        if (code.equals("")) {
            ToastUtil.showToast(this, "请输入验证码");
            return true;
        }
        if(verify_id==null|| TextUtils.isEmpty(verify_id)){
            ToastUtil.showToast(this, "请先发送验证码");
            return true;
        }
        psw = edittext_newPsw_changepsw.getText().toString();
        if (psw.equals("")) {
            ToastUtil.showToast(this, "请输入新密码");
            return true;
        }
        if(psw.length()<6){
            ToastUtil.showToast(this, "请输入长度为6-16的密码");
            return true;
        }
        if(psw.length()>16){
            ToastUtil.showToast(this, "密码不超过16位字符");
            return true;
        }
        return false;
    }
}
