package com.qixiu.intelligentcommunity.my_interface.web;

import android.webkit.WebView;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public interface WebViewClientInterface {
    boolean shouldOverrideUrlLoading(WebView view, String url);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, int errorCode, String description,
                         String failingUrl);
}
