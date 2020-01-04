package com.qixiu.intelligentcommunity.engine.jpush;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.utlis.ArshowLog;
import com.qixiu.intelligentcommunity.utlis.DeviceIdUtil;

import java.util.Set;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class JpushEngine {


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
            tmDevice = "" + tm.getDeviceId();
        }catch (Exception e){
            tmDevice = DeviceIdUtil.getDeviceId();
        }
        return tmDevice;
    }


}
