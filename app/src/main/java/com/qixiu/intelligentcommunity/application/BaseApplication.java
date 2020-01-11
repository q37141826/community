package com.qixiu.intelligentcommunity.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;

import com.qixiu.intelligentcommunity.engine.jpush.JpushEngine;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.XutilsModel;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class BaseApplication extends MultiDexApplication {
    private static BaseApplication app;
    private static Context mContext;
    public static  String defualtPath;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //      initGlobeActivity();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = this;
        initSdk();
        setDefaultPath();
    }

    private void setDefaultPath() {
        defualtPath = getExternalCacheDir().getPath();
    }

    public static BaseApplication getApp() {
        return app;
    }

    public static Context getContext() {
        return mContext;
    }

    public static boolean isLoginToLongin(Activity activity) {
        if (TextUtils.isEmpty(Preference.get("id", ""))) {
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
            return false;

        } else {
            return true;
        }

    }

    private void initSdk() {
        //初始化ShareSDK
        try {
            ShareSDK.initSDK(getContext());
        } catch (Exception e) {
        }
        if(LoginUtils.isLogined()){
            JpushEngine.initJPush(getContext());
        }
        XutilsModel.initXutil(this);
    }

    private void initGlobeActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                Log.e("onActivityCreated===", activity + "");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

                Log.e("onActivityDestroyed===", activity + "");
            }

            /** Unused implementation **/
            @Override
            public void onActivityStarted(Activity activity) {

                Log.e("onActivityStarted===", activity + "");
            }

            @Override
            public void onActivityResumed(Activity activity) {

                Log.e("onActivityResumed===", activity + "");
            }

            @Override
            public void onActivityPaused(Activity activity) {

                Log.e("onActivityPaused===", activity + "");
            }

            @Override
            public void onActivityStopped(Activity activity) {

                Log.e("onActivityStopped===", activity + "");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
        });
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return app;
    }

    /**
     * 公开方法，外部可通过 MyApplication.getInstance().getCurrentActivity() 获取到当前最上层的activity
     */

}
