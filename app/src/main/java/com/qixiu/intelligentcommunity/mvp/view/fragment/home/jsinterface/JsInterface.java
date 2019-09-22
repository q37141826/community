package com.qixiu.intelligentcommunity.mvp.view.fragment.home.jsinterface;

import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public interface JsInterface {

    String JsInterfaceTag = JsInterface.class.getSimpleName();


    /**
     * 跳到下个界面
     *
     * @param jsInterfaceInfo
     */
    @JavascriptInterface
     void pushBtn(String jsInterfaceInfo);


    /**
     * 查看大图片
     *
     * @param jsInterfaceInfo
     */
    @JavascriptInterface
     void imageBtn(String jsInterfaceInfo);


    /**
     * 修改标题，及包含下个界面的标题及url
     *
     * @param jsInterfaceInfo
     */
    @JavascriptInterface
      void titleName(String jsInterfaceInfo);

    /**
     * 发布界面发布后的上一级界面刷新
     *
     * @param jsInterfaceInfo
     */
    @JavascriptInterface
      void popBtn(String jsInterfaceInfo);
    /**
     * 发布界面发布后的上一级界面刷新
     *
     * @param jsInterfaceInfo
     */
    @JavascriptInterface
      void phoneNumber(String jsInterfaceInfo);

}
