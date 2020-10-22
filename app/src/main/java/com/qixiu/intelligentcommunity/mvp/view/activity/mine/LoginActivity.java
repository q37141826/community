package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.PlatformLoginEngine;
import com.qixiu.intelligentcommunity.engine.bean.UserInfo;
import com.qixiu.intelligentcommunity.engine.jpush.JpushEngine;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.PermissionNotice;
import com.qixiu.intelligentcommunity.mvp.beans.login.LoginBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD.OnDialogDismiss;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.DeviceIdUtil;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.VersionCheckUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

import static com.qixiu.intelligentcommunity.R.id.et_login_password;
import static com.qixiu.intelligentcommunity.R.id.et_login_phone;

/**
 * Created by Administrator on 2017/6/14 0014.  测试13397184320  123456789
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, TextWatcherAdapterInterface, View.OnFocusChangeListener, PlatformLoginEngine.PlatformResultListener, OnDialogDismiss {
    private View mRl_goto_regist;
    private EditText mEt_login_phone;
    private EditText mEt_login_password;
    private View mRl_clean_input_phone;
    private View mRl_clean_input_password;
    private IntelligentTextWatcher mLoginPhoneTextWatcher;
    private IntelligentTextWatcher mLoginPasswordTextWatcher;
    private Button mBt_loging;
    //手机号，密码，设备号
    String phone = "", password = "", DEVICE_ID = "";
    private TextView textView_forgetpassword, textView_weixin_login, textView_qq_login, textView_weibo_login;
    //第三方登录框架
    PlatformLoginEngine platformLoginEngine;
    //第三方登录标志，传给自己家服务器用的
    String register_type = "";
    //需要保护的点击事件装入集合
    List<View> listClick = new ArrayList<>();
    String permissions[] = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    //判断是否从第三方登录返回
    private boolean IS_FROM_APP = false;
    //新的弹窗
    ZProgressHUD zProgressHUD;
    //认证成功后跳转主页面
    BroadcastReceiver receiver;

    @Override
    protected void onInitData() {
        if (!hasPermission(permissions)) {
            hasRequse(1, permissions);
            return;
        }
        getPhoneDeviceId();
    }

    public void getPhoneDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            DEVICE_ID = tm.getDeviceId();
        } catch (Exception e) {
        }
        DEVICE_ID = DeviceIdUtil.getDeviceId();
        VersionCheckUtil.checkVersion(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasPermission(permissions)) {
            getPhoneDeviceId();
        }
    }

    @Override
    protected void onInitView() {
        EventBus.getDefault().register(this);
        zProgressHUD = new ZProgressHUD(this);
        zProgressHUD.setMessage("加载中");
        //注册第三方登录
        platformLoginEngine = new PlatformLoginEngine(this);
        textView_weibo_login = (TextView) findViewById(R.id.textView_weibo_login);
        textView_qq_login = (TextView) findViewById(R.id.textView_qq_login);
        textView_weixin_login = (TextView) findViewById(R.id.textView_weixin_login);
        textView_forgetpassword = (TextView) findViewById(R.id.textView_forgetpassword);
        mRl_goto_regist = findViewById(R.id.rl_goto_regist);
        mEt_login_phone = (EditText) findViewById(et_login_phone);
        mEt_login_phone = (EditText) findViewById(et_login_phone);

        mEt_login_password = (EditText) findViewById(et_login_password);
        mRl_clean_input_phone = findViewById(R.id.rl_clean_input_phone);
        mRl_clean_input_password = findViewById(R.id.rl_clean_input_password);
        mBt_loging = (Button) findViewById(R.id.bt_loging);
        initListener();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                phone = intent.getStringExtra(ConstantString.PHONE);
                password = intent.getStringExtra(ConstantString.PASSWORD);
                if(!hasDefultPermission()){
                    requestDefultPermission();

                }
                startLogin();
            }
        };
        IntentFilter filter = new IntentFilter(ConstantString.IDENTIFY_OK);
        registerReceiver(receiver, filter);
    }

    private void initListener() {
        textView_weixin_login.setOnClickListener(this);
        textView_qq_login.setOnClickListener(this);
        textView_weibo_login.setOnClickListener(this);
        textView_forgetpassword.setOnClickListener(this);
        mBt_loging.setOnClickListener(this);
        mRl_clean_input_phone.setOnClickListener(this);
        mRl_clean_input_password.setOnClickListener(this);
        mRl_goto_regist.setOnClickListener(this);
        listClick.add(textView_weixin_login);
        listClick.add(textView_qq_login);
        listClick.add(textView_weibo_login);
        listClick.add(textView_forgetpassword);
        listClick.add(mBt_loging);
        listClick.add(mRl_clean_input_phone);
        listClick.add(mRl_clean_input_password);
        listClick.add(mRl_goto_regist);
        mLoginPhoneTextWatcher = new IntelligentTextWatcher(mEt_login_phone.getId(), this);

        mLoginPasswordTextWatcher = new IntelligentTextWatcher(mEt_login_password.getId(), this);
        mLoginPasswordTextWatcher.setEmojiDisabled(true, mEt_login_password);
        mEt_login_phone.addTextChangedListener(mLoginPhoneTextWatcher);
        mEt_login_password.addTextChangedListener(mLoginPasswordTextWatcher);
        mEt_login_phone.setOnFocusChangeListener(this);
        mEt_login_password.setOnFocusChangeListener(this);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_goto_regist:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_clean_input_phone:
                mEt_login_phone.setText(StringConstants.EMPTY_STRING);
                break;
            case R.id.rl_clean_input_password:
                mEt_login_password.setText(StringConstants.EMPTY_STRING);
                break;
            case R.id.bt_loging:
                if(!hasDefultPermission()){
                    requestDefultPermission();
                    return;
                }
                getCurrentDatas();
                if (phone.equals("")) {
                    ToastUtil.showToast(this, "请输入账号");
                    return;
                }
                if (!CommonUtils.isMobileNO(phone)) {
                    ToastUtil.showToast(this, "请输入正确账号");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtil.toast(R.string.please_input_password);
                }
                startLogin();
                zProgressHUD.show();
                break;
            case R.id.textView_forgetpassword:
                CommonUtils.startIntent(this, ChangePasswordActivity.class);
                break;

            case R.id.textView_weixin_login:
                register_type = 1 + "";
                setEnable(false);
                platformLoginEngine.startAuthorize(Wechat.NAME);
//                MyProgressDialog.showProgressDialog(this, "加载中");
                zProgressHUD.setMessage("加载中").show();
                break;
            case R.id.textView_qq_login:
                register_type = 3 + "";
                setEnable(false);
                platformLoginEngine.startAuthorize(QQ.NAME);
//                MyProgressDialog.showProgressDialog(this, "加载中");
                zProgressHUD.setMessage("加载中").show();
                break;
            case R.id.textView_weibo_login:
                platformLoginEngine.startAuthorize(SinaWeibo.NAME);
                setEnable(false);
                register_type = 2 + "";
//                MyProgressDialog.showProgressDialog(this, "加载中");
                zProgressHUD.setMessage("加载中").show();
                break;
        }
    }

    private void startLogin() {
        OkHttpUtils.post().url(ConstantUrl.loginUrl)
                .addParams("device_type", "2")
                .addParams("phone", phone.trim())
                .addParams("password", MD5Util.MD5(password.trim()))
                .addParams("device", DEVICE_ID).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                ToastUtil.showToast(getApplication().getApplicationContext(), "登陆失败,请检查网络");
                zProgressHUD.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    LoginBean loginBean = GetGson.init().fromJson(s, LoginBean.class);
                    Preference.putBoolean("IS_FROM_APP", false);
//                    ToastUtil.showToast(getBaseContext(), loginBean.getM());
                    if (loginBean.getC() == 1) {
                        saverUserMessage(loginBean);
                        CommonUtils.startIntent(getApplicationContext(), MainActivity.class);
                        LoginActivity.this.finish();
                    }
                    IS_FROM_APP = false;
                } catch (Exception e) {
                    BaseBean bean = GetGson.init().fromJson(s, BaseBean.class);
                    ToastUtil.showToast(LoginActivity.this, bean.getM());
                }
                zProgressHUD.dismiss();
            }
        });


    }

    private void saverUserMessage(LoginBean loginBean) {
        Preference.put(ConstantString.USERID, loginBean.getO().getId());
        Preference.put(ConstantString.NICK_NAME, loginBean.getO().getNickname());
        Preference.put(ConstantString.HEAD, ConstantUrl.hostImageurl + loginBean.getO().getHead());
        Preference.put(ConstantString.PHONE, loginBean.getO().getPhone());
        Preference.put(ConstantString.UTYPE, loginBean.getO().getUtype());
        Preference.put(ConstantString.AUTH, loginBean.getO().getAuth() + "");
        Preference.put(ConstantString.PASSWORD, mEt_login_password.getText().toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mEt_login_phone != null) {
            mEt_login_phone.removeTextChangedListener(mLoginPhoneTextWatcher);
            mLoginPhoneTextWatcher = null;
        }
        if (mEt_login_password != null) {
            mEt_login_password.removeTextChangedListener(mLoginPasswordTextWatcher);
            mLoginPasswordTextWatcher = null;
        }
        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
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
            case et_login_phone:
                int phoneShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_input_phone.getVisibility() != phoneShowWay) {
                    mRl_clean_input_phone.setVisibility(phoneShowWay);
                }

                break;
            case et_login_password:
                int passwordShowWay = TextUtils.isEmpty(s.toString()) ? View.GONE : View.VISIBLE;
                if (mRl_clean_input_password.getVisibility() != passwordShowWay) {
                    mRl_clean_input_password.setVisibility(passwordShowWay);
                }

                break;
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case et_login_phone:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_input_password.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_password.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_login_phone.getText().toString())) {
                    mRl_clean_input_phone.setVisibility(View.VISIBLE);
                }
                break;
            case et_login_password:
                if (!hasFocus) {
                    return;
                }
                if (mRl_clean_input_phone.getVisibility() == View.VISIBLE) {
                    mRl_clean_input_phone.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mEt_login_password.getText().toString())) {
                    mRl_clean_input_password.setVisibility(View.VISIBLE);
                }
                break;
        }


    }

    public void getCurrentDatas() {
        phone = mEt_login_phone.getText().toString();
        password = mEt_login_password.getText().toString();
    }

    @Override
    public void onCancel() {
        setEnable(true);
        zProgressHUD.dismiss();
    }

    @Override
    public void onFailure() {
        setEnable(true);
        zProgressHUD.dismissWithFailure("登录出错");
    }

    @Override
    public void onSuccess(final UserInfo userInfo) {
        zProgressHUD.setMessage("加载中").show();
        IS_FROM_APP = true;
        OkHttpUtils.post().url(ConstantUrl.appLoginUrl)
                .addParams("party_key", userInfo.getUserId())
                .addParams("register_type", register_type)
                .addParams("head", userInfo.getUserIcon())
                .addParams("username", userInfo.getUserName())
                .addParams("sex", "1")
                .addParams("device", DEVICE_ID)
                .addParams("device_type", 2 + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                zProgressHUD.dismiss();
                IS_FROM_APP = false;
                setEnable(true);
            }

            @Override
            public void onResponse(String s, int i) {
                zProgressHUD.dismiss();
                setEnable(true);
                try {
                    LoginBean loginBean = GetGson.init().fromJson(s, LoginBean.class);
                    saverUserMessage(loginBean);
                    Preference.putBoolean("IS_FROM_APP", true);
                    Preference.put(ConstantString.HEAD, loginBean.getO().getHead() + ".jpg");
                    LoginUtils. saveLoginData(s);
//                    ToastUtil.showToast(getBaseContext(), loginBean.getM());
                    CommonUtils.startIntent(getApplicationContext(), MainActivity.class);
                    //初始化极光推送
                    JpushEngine.initJPush(getApplicationContext());
                    LoginActivity.this.finish();
                    IS_FROM_APP = false;
                } catch (Exception e) {
                    if (GetGson.parseMessageBean(s).getE().equals("2")) {
                        ToastUtil.showToast(LoginActivity.this, GetGson.parseMessageBean(s).getM());
                        return;
                    }
                    Intent intent = new Intent(LoginActivity.this, AuthorizationActivity.class);
                    intent.putExtra("key", GetGson.parseMessageBean(s).getO().toString());
                    startActivity(intent);
                }
            }
        });
    }

    //由于第三方登录时间比较长，所以做个保护
    public void setEnable(boolean enable) {
        for (int i = 0; i < listClick.size(); i++) {
            listClick.get(i).setEnabled(enable);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDismiss() {
        setEnable(true);
        if (IS_FROM_APP) {
            zProgressHUD.show();
        } else {
            zProgressHUD.dismiss();
        }
    }

    @Subscribe
    public void onEventReceived(PermissionNotice notice) {
        hasRequse(1, permissions);
    }

    @Override
    public void initWhenHasPermissions() {
        //初始化极光推送
        getPhoneDeviceId();
        JpushEngine.initJPush(getApplicationContext());
    }

    @Override
    public void initWhenReuqestPermisssionFailed() {
        super.initWhenReuqestPermisssionFailed();
    }


}
