package com.qixiu.intelligentcommunity.utlis;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Xanthium on 2016/10/28.
 * <p>
 * xutils相关的
 */

public class XutilsModel {
    /**
     * 初始化xutils
     *
     * @param application
     */
    public static void initXutil(Application application) {
        x.Ext.init(application);
        x.Ext.setDebug(Boolean.parseBoolean("true"));
    }
}
