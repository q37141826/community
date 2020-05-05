package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.leolin.shortcutbadger.ShortcutBadger;

public class ShortCutHelper {



    public static void setShortCut(Context context,int num){
        setHuawei(context,num);
        setXiaoMiBadge(context,num);
        setSumsing(context,num);
    }


    public static void setHuawei(Context context, int num) {
        String launcherClassName = "com.qixiu.intelligentcommunity.mvp.view.activity.guidepage.StartPageActivity";//启动的Activity完整名称
        Bundle localBundle = new Bundle();//需要存储的数据
        localBundle.putString("package", context.getPackageName());//包名
        localBundle.putString("class", launcherClassName);
        localBundle.putInt("badgenumber", num);//未读信息条数
        context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, localBundle);
    }


    public static void setXiaoMiBadge(Context context, int number) {
        try {
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification, String.valueOf(number == 0 ? "" : number));
        } catch (Exception e) {
            e.printStackTrace();
            // miui6之前
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName() + "/." + "MainActivity");
            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(number == 0 ? "" : number));
            context.sendBroadcast(localIntent);

        }
    }


    public static void setSumsing(Context context, int num) {
        String launcherClassName = "com.redfinger.huaweichangebadge.MainActivity";
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", num);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }
}
