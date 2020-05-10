package com.qixiu.intelligentcommunity.engine.jpush;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.utlis.ArshowLog;
import com.qixiu.intelligentcommunity.utlis.DeviceIdUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.helper.JCoreHelper;
import cn.jpush.android.local.JPushConstants;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class JpushEngine {
    public static final String TAG = "JpushEngine";

    /**
     * 初始化JPush
     *
     * @param context
     */
    public static void initJPush(Context context) {
        JPushInterface.init(context);
        String machineCode = getMachineCode();
        if (machineCode != null)
            setAlias(context, machineCode);
        Log.d(TAG, "initJPush: registerId"+ getRegistrationID(context));
    }

    public static String getRegistrationID(Context var0) {
        checkContext(var0);
        JCoreHelper.runActionWithService(var0, "JPUSH", "get_rid", (Bundle) null);
        return JCoreHelper.getRegistrationID(var0);
    }

    private static void checkContext(Context var0) {
        if (null == var0) {
            throw new IllegalArgumentException("NULL context");
        } else {
            JPushConstants.mApplicationContext = var0.getApplicationContext();
        }
    }

    /**
     * 设置别名，针对别名推送消息
     *
     * @param context
     * @param alias   别名
     */
    public static void setAlias(Context context, String alias) {
        JPushInterface.setAlias(context, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                ArshowLog.d(getClass(), i + "---" + s);
            }
        });
    }

    public static String getMachineCode() {

        final TelephonyManager tm = (TelephonyManager) BaseApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice;
        try {
            tmDevice = tm.getDeviceId();
            if (TextUtils.isEmpty(tmDevice)) {
                tmDevice = DeviceIdUtil.getDeviceId();
            }
        } catch (Exception e) {
            tmDevice = DeviceIdUtil.getDeviceId();
        }
        return tmDevice;
    }


}
