package com.qixiu.intelligentcommunity.my_interface.web;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class MWebViewClient extends WebViewClient {
    private final WebViewClientInterface webViewClientInterface;

    public MWebViewClient(WebViewClientInterface webViewClientInterface) {
        this.webViewClientInterface = webViewClientInterface;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        webViewClientInterface.shouldOverrideUrlLoading(view, url);
        return super.shouldOverrideUrlLoading(view, url);

    }



    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!view.getSettings().getLoadsImagesAutomatically()) {
            view.getSettings().setLoadsImagesAutomatically(true);

        }
        webViewClientInterface.onPageFinished(view, url);

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description,
                                String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        webViewClientInterface.onReceivedError(view, errorCode, description, failingUrl);
    }
}
