package com.qixiu.intelligentcommunity;

import android.support.multidex.MultiDexApplication;

/**
 * Description：全局
 * Author：XieXianyong
 * Date： 2017/6/20 20:21
 */
public class GlobalApplication extends MultiDexApplication {
    private static GlobalApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    //获取当前应用程序的Context
    public static GlobalApplication getInstance() {
        return mInstance;
    }
}
