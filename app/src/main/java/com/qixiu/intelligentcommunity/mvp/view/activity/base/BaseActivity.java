package com.qixiu.intelligentcommunity.mvp.view.activity.base;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.config.ToolBarOptions;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.login.SendCodeBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.utlis.AppManager;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.DeviceIdUtil;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    public String verify_id = "";
    private FragmentTransaction mFragmentTransaction;
    protected FragmentManager mSupportFragmentManager;
    protected String[] photoPermission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE};

    public int windowHeight, windowWith;
    private Toolbar toolbar;
    private TextView tvTitle;
    BroadcastReceiver receiver;
    public String deviceId;

    //检查权限
    public boolean hasPermission(String... permission) {
        for (String permissiom : permission) {
            if (ActivityCompat.checkSelfPermission(this, permissiom) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //添加权限
    public void hasRequse(int code, String... permission) {
        ActivityCompat.requestPermissions(this, permission, 1);
    }

    /**
     * 切换Fragment，与上面的方法不同的是判断是否需要添加堆栈中
     *
     * @param fragment
     * @param resId
     * @param arguments
     * @param isBack
     */
    public void switchFragment(Fragment thisFragment, Fragment fragment, int resId,
                               Bundle arguments, boolean isBack) {
        switchFragment(thisFragment, fragment, resId, arguments, isBack, false);
    }

    /**
     * @param fragment  要替换的Fragment
     * @param arguments 需要携带的参数
     * @param isBack    是否添加到回退栈
     * @param isHide    是否隐藏
     */
    public void switchFragment(Fragment thisFragment, Fragment fragment, int resId,
                               Bundle arguments, boolean isBack, boolean isHide) {
        switchFragment(thisFragment, fragment, resId, arguments, isBack, null, isHide);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param resId
     * @param arguments
     * @param isBack
     * @param tag       Fragment的标签
     * @param isHide
     */
    public void switchFragment(Fragment thisFragment, Fragment fragment, int resId,
                               Bundle arguments, boolean isBack, String tag, boolean isHide) {
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        mFragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);// 设置动画效果
        mFragmentTransaction.replace(resId, fragment);
        if (isBack)
            mFragmentTransaction.addToBackStack(tag);
        if (isHide)
            mFragmentTransaction.hide(thisFragment);
        mFragmentTransaction.commit();
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param resId
     * @param arguments
     */
    public void switchFragment(Fragment fragment, int resId, Bundle arguments) {
        switchFragment(null, fragment, resId, arguments, false, null, false);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param resId
     * @param arguments
     * @param tag
     */
    public void switchFragment(Fragment fragment, int resId, Bundle arguments, String tag) {
        switchFragment(null, fragment, resId, arguments, false, tag, false);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param resId
     */
    public void switchFragment(Fragment fragment, int resId) {
        switchFragment(null, fragment, resId, null, false, null, false);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     * @param resId
     * @param tag
     */
    public void switchFragment(Fragment fragment, int resId, String tag) {
        switchFragment(null, fragment, resId, null, false, tag, false);
    }

    /**
     * 添加Fragment
     *
     * @param layoutId
     * @param fragment
     * @param tag      Fragment的标签
     */
    protected void addFragment(int layoutId, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().add(layoutId, fragment, tag).commit();
    }

    protected void addFragment(int layoutId, Fragment fragment, String tag, Bundle bundle) {
        if (fragment != null) {

            fragment.setArguments(bundle);

        }
        addFragment(layoutId, fragment, tag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        DisplayMetrics mtr = new DisplayMetrics();
        defaultDisplay.getMetrics(mtr);
        mContext = this;
        ButterKnife.bind(this);
        windowHeight = mtr.heightPixels;
        windowWith = mtr.widthPixels;
        Log.e("windowheight:windowWith", windowHeight + "---" + windowWith);
        mSupportFragmentManager = getSupportFragmentManager();
        onInitView();
        onInitData();
        if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            getDeviceId();
        } else {
            hasRequse(1, Manifest.permission.READ_PHONE_STATE);
        }
        setScreenOrentation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        Log.e("where", this.getClass().getSimpleName());
    }

    protected void getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
            DeviceIdUtil.saveDeviceId(deviceId);
        } catch (Exception e) {
        }
        deviceId = DeviceIdUtil.getDeviceId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播接受者
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getStringExtra("type").equals("no_identify")) {
                    Preference.put(ConstantString.UTYPE, "4");
                } else {
                    Preference.put(ConstantString.UTYPE, "2");
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter("com.qixiu.example.broadcast.normal");
        intentFilter.setPriority(600);
        registerReceiver(receiver, intentFilter);
        JPushInterface.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(this).clearMemory();
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        JPushInterface.onPause(this);

    }

    protected abstract void onInitData();

    protected abstract void onInitView();


    protected abstract int getLayoutResource();


    TextView codeText;
    public Timer timer;

    public void sendCode(String phone, Context context, String type, TextView codeText) {
        this.codeText = codeText;
//        String token = MD5Util.getToken(ConstantUrl.SendCodeTag);
        //3表示是验证码登录
        timer = new Timer();
        codeText.setEnabled(false);
        useOkhttpforSendCode(phone, type, context);
    }

    public void useOkhttpforSendCode(String phone, String type, final Context context) {
        OkHttpUtils.post().url(ConstantUrl.sendCodeUrl)
                .addParams("phone", phone)
                .addParams("type", type).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                codeText.setEnabled(true);
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    SendCodeBean sendCodeBean = GetGson.init().fromJson(s, SendCodeBean.class);
                    ToastUtil.showToast(getApplicationContext(), sendCodeBean.getM());
                    verify_id = sendCodeBean.getO().getVerify_id();
                    timer.start();
                    ToastUtil.showToast(context, sendCodeBean.getM());
                } catch (Exception e) {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(getApplicationContext(), messageBean.getM());
                    ToastUtil.showToast(getApplicationContext(), messageBean.getM());
                    ToastUtil.showToast(context, messageBean.getM());
                }

            }
        });
    }

    public void setToolBar(ToolBarOptions options) {
        toolbar = (Toolbar) findViewById(options.toolbarId);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (options.titleId != 0) {
//            tvTitle.setText(options.titleId);
            toolbar.setTitle(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
//            tvTitle.setText(options.titleString);
            toolbar.setTitle(options.titleString);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        if (options.backgroundDrawable != null)
            toolbar.setBackground(options.backgroundDrawable);

        setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            if (options.navigateId != 0)
                toolbar.setNavigationIcon(options.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigateUpClicked();
                }
            });
        }
    }

    public void setToolBar(int toolBarId, ToolBarOptions options) {
        toolbar = (Toolbar) findViewById(toolBarId);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (options.titleId != 0) {
            tvTitle.setText(options.titleId);
        }
        if (!TextUtils.isEmpty(options.titleString)) {
            tvTitle.setText(options.titleString);
        }
        if (options.logoId != 0) {
            toolbar.setLogo(options.logoId);
        }
        if (options.backgroundDrawable != null)
            toolbar.setBackground(options.backgroundDrawable);
        setSupportActionBar(toolbar);

        if (options.isNeedNavigate) {
            if (options.navigateId != 0)
                toolbar.setNavigationIcon(options.navigateId);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigateUpClicked();
                }
            });
        }
    }

    public void onNavigateUpClicked() {
        onBackPressed();
    }

    public void setToolBar(int toolbarId, int titleId, int logoId) {
        toolbar = (Toolbar) findViewById(toolbarId);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(titleId);
        toolbar.setLogo(logoId);
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolBar() {
        return toolbar;
    }


    private class Timer extends CountDownTimer {

        public Timer() {
            super(60 * 1000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.i("test", "??");
            codeText.setText(millisUntilFinished / 1000 + "秒后重发");
            codeText.setEnabled(false);
        }

        @Override
        public void onFinish() {
            codeText.setText("发送验证码");
            codeText.setEnabled(true);

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            codeText.setText("发送验证码");
            codeText.setEnabled(true);
        } catch (Exception e) {
        }
    }

    /*
     * 退出方法结尾
     * */
    //推送强制退出登录的方法
    private void OutLogin(final Context context) {
        View contentView = View.inflate(context, R.layout.popwindow_exit, null);
        PopupWindow popupWindow = new PopupWindow(contentView);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        Button button_confirm_exit = (Button) contentView.findViewById(R.id.button_confirm_exit);
        button_confirm_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starOut();
            }
        });
        popupWindow.dismiss();
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private void starOut() {
        OkHttpUtils.post().url(ConstantUrl.logoutUrl)
                .addParams("id", Preference.get(ConstantString.USERID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean bean = GetGson.parseBaseBean(s);
                    if (bean.getC() == 1) {
                        Preference.clearAllFlag();
                        Preference.putBoolean(ConstantString.IS_FIRST_LOGIN, true);
                        CommonUtils.startIntent(BaseActivity.this, LoginActivity.class);
                        BaseActivity.this.finish();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    //提示，参数在ActivityInfo里面
    public void setScreenOrentation(int screenOrentation) {
        setRequestedOrientation(screenOrentation);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (hasPermission(permissions)) {
            initWhenHasPermissions();
        } else {
            initWhenReuqestPermisssionFailed();
        }
        if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            getDeviceId();
        }
    }

    public void initWhenReuqestPermisssionFailed() {

    }


    public void initWhenHasPermissions() {

    }

    public Context getContext() {
        return this;
    }

    public Activity getActivity() {
        return this;
    }
}
