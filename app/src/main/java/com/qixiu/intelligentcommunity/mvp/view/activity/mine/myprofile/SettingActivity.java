package com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.ChangePasswordActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Call;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout_clean_setting, relativeLayout_feed_back_setting, relativeLayout_title_setting;
    private Button btn_out_login;
    private RelativeLayout relativeLayout_changePswd;

    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView() {
        relativeLayout_title_setting = (RelativeLayout) findViewById(R.id.relativeLayout_title_setting);
        relativeLayout_feed_back_setting = (RelativeLayout) findViewById(R.id.relativeLayout_feed_back_setting);
        relativeLayout_clean_setting = (RelativeLayout) findViewById(R.id.relativeLayout_clean_setting);
        relativeLayout_changePswd = (RelativeLayout) findViewById(R.id.relativeLayout_changePswd);
        btn_out_login = (Button) findViewById(R.id.btn_out_login);
        initTitle();
        initclick();
    }

    private void initclick() {
        relativeLayout_feed_back_setting.setOnClickListener(this);
        relativeLayout_clean_setting.setOnClickListener(this);
        relativeLayout_changePswd.setOnClickListener(this);
        btn_out_login.setOnClickListener(this);
    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("设置");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_title_setting.addView(title.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_clean_setting:
                File filesDir = getFilesDir();
                cleanapp(filesDir);
                ToastUtil.showToast(this, "清理完毕");
                break;
            case R.id.relativeLayout_feed_back_setting:
                CommonUtils.startIntent(this, SuggestionCommittingActivity.class);
                break;
            case R.id.relativeLayout_changePswd:
                CommonUtils.startIntent(this, ChangePasswordActivity.class);
                break;
            case R.id.btn_out_login:
                OutLogin();
                break;
        }
    }

    private void OutLogin() {
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
                        CommonUtils.startIntent(SettingActivity.this, LoginActivity.class);
                        SettingActivity.this.finish();
                        Intent intent = new Intent();
                        intent.setAction(IntentDataKeyConstant.BROADCAST_MAIN_FINISH);
                        sendBroadcast(intent);
                    }
                } catch (Exception e) {
                }
            }
        });

    }

    private void cleanapp(File filesDir) {
        File[] files = filesDir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else {
                cleanapp(file);
            }
        }
    }
}
