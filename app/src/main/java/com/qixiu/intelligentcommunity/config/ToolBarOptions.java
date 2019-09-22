/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */

package com.qixiu.intelligentcommunity.config;


import android.graphics.drawable.Drawable;

import com.qixiu.intelligentcommunity.R;


/**
 * Created by xxy on 2016/6/16.
 */
public class ToolBarOptions {

    public int toolbarId = R.id.toolbar;
    /**
     * toolbar的title资源id
     */
    public int titleId = 0;
    /**
     * toolbar的title
     */
    public String titleString;
    /**
     * toolbar的logo资源id
     */
    public int logoId = 0;
//    public int logoId = R.mipmap.ic_launcher;
    /**
     * toolbar的返回按钮资源id，默认开启的资源nim_actionbar_dark_back_icon
     */
    public int navigateId = 0;
//    public int navigateId = R.mipmap.ic_nav_back;
    /**
     * toolbar的返回按钮，默认开启
     */
    public boolean isNeedNavigate = true;
    /**
     * toolbar颜色
     */
    public Drawable backgroundDrawable;
}
