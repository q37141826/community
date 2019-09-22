package com.qixiu.intelligentcommunity.utlis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.PermissionNotice;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.service.UpdateVersionService;
import com.qixiu.intelligentcommunity.service.VersionBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/9/26.
 */

public class VersionCheckUtil {
    public static void checkVersion(final Activity activity) {
        OkHttpUtils.get().url(ConstantUrl.versionUrl)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    VersionBean bean = GetGson.init().fromJson(s, VersionBean.class);
                    if (!bean.getO().getName().equals(ArshowContextUtil.getVersionName(activity))) {
                        setDialog("检测到新版本", bean.getO(), activity, bean.getO().getType());
                    }


                } catch (Exception e) {
                }
            }
        });

    }

    public static void setDialog(String message, final VersionBean.OBean oBean, final Activity activity, String version_code) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(activity);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        if (version_code.equals("1")) {
            builder.setNegativeButton("退出应用", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AppManager.getAppManager().finishAllActivity();
                }
            });
        } else {
            builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });
        }

        builder.setPositiveButton("下载更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //
                String permissions[]={android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                if(!((BaseActivity)(activity)).hasPermission(permissions)){
                    EventBus.getDefault().post(new PermissionNotice());
                    return;
                }
                try {
                    dialog.dismiss();
                    Intent intent = new Intent(activity.getApplicationContext(), UpdateVersionService.class);
                    intent.putExtra("apkVersion", oBean);
                    activity.getApplicationContext().startService(intent);

                } catch (Exception e) {
                }
            }
        });

        builder.setMessage(message);
        builder.show();
    }
}
