package com.qixiu.intelligentcommunity.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.messagelist.MessageListActivity;
import com.qixiu.intelligentcommunity.utlis.ArshowLog;
import com.qixiu.intelligentcommunity.utlis.ConfigInfo;
import com.qixiu.intelligentcommunity.utlis.DeviceIdUtil;
import com.qixiu.intelligentcommunity.utlis.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.local.JPushAction;
import cn.jpush.android.service.JPushMessageReceiver;

import static com.qixiu.intelligentcommunity.receiver.JPushReceiver.JPushAction.KEY_MESSAGE;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class JPushReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        ArshowLog.d(getClass(), "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " +
                printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            ArshowLog.d(getClass(), "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            ArshowLog.d(getClass(), "[MyReceiver] 接收到推送下来的自定义消息: " +
                    bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            ArshowLog.d(getClass(), "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String titile = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            //通知来了之后误操作状态下的消息处理
            Intent exitIntent = new Intent("com.qixiu.example.broadcast.normal");
            if (titile.contains("关闭授权")||titile.contains("拒绝")) {
                exitIntent.putExtra("type","no_identify");
                context.sendBroadcast(exitIntent);
            }else if(titile.contains("授权认证成功")||titile.contains("开启授权")){
                exitIntent.putExtra("type","identify");
                context.sendBroadcast(exitIntent);
            }

            ArshowLog.d(getClass(), "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (TextUtils.isEmpty(Preference.get(IntentDataKeyConstant.ID, ConstantString.STRING_EMPTY))) {
                intent.setClass(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return;
            }
            //点击通知消息
            LinkedTreeMap<String, String> comment =
                    new Gson().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA),
                            LinkedTreeMap.class);

            intent.putExtra("message", bundle.getString(JPushInterface.EXTRA_ALERT));

            //其他消息的跳转
            intent.setClass(context, MessageListActivity.class);//// TODO: 2017/6/22 在这里放入需要推送到的位置
            intent.putExtra(IntentDataKeyConstant.JPUSH_NOTIFICATION_DATA_KEY, comment);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            ArshowLog.d(getClass(), "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " +
                    bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected =
                    intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            ArshowLog.w(getClass(),
                    "[MyReceiver]" + intent.getAction() + " connected state change to " +
                            connected);
        } else {
            ArshowLog.d(getClass(), "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 打印所有的 intent extra 数据
     */
    private String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    ArshowLog.i(JPushReceiver.class, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    ArshowLog.e(JPushReceiver.class, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    /**
     * 处理消息
     *
     * @param context
     * @param bundle
     */
    private void processCustomMessage(Context context, Bundle bundle) {
        if (!ConfigInfo.isBackground(context)) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(JPushAction.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(KEY_MESSAGE, message);
            if (!TextUtils.isEmpty(extras)) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(JPushAction.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }

    }

    public static class JPushAction {

        /**
         * 用于接收广播的Action
         */
        public static String MESSAGE_RECEIVED_ACTION;
        public static final String KEY_MESSAGE = "message";
        public static final String KEY_EXTRAS = "extras";

    }

}
