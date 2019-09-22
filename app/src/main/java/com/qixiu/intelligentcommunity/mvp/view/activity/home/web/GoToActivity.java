package com.qixiu.intelligentcommunity.mvp.view.activity.home.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;


public class GoToActivity extends NewTitleActivity {
    private String url, filePath;
    private WebView webView_goto;
    private ZProgressHUD zProgressHUD;
    private String title;
    @Override
    protected void onInitViewNew() {
        zProgressHUD = new ZProgressHUD(this);
        try {
            title=getIntent().getStringExtra(ConstantString.TITLE_NAME);
        } catch (Exception e) {
        }
        mTitleView.setTitle(title==null?"详情":title);
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        try {
            url = getIntent().getStringExtra(ConstantString.URL);
            filePath = getIntent().getStringExtra(ConstantString.FILEPATH);
        } catch (Exception e) {
        }

        webView_goto = (WebView) findViewById(R.id.webView_goto);
        //设置webview禁止缩放
        WebSettings settings = webView_goto.getSettings();
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        if (url != null && !TextUtils.isEmpty(url)) {
            webView_goto.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    zProgressHUD.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    zProgressHUD.dismiss();
                }
            });
        }
//        else if(filePath!=null&& !TextUtils.isEmpty(filePath)){
//            if(filePath.contains("pdf")){
//                webView_goto.getSettings().setJavaScriptEnabled(true);
//                webView_goto.getSettings().setSupportZoom(true);
//                webView_goto.getSettings().setDomStorageEnabled(true);
//                webView_goto.getSettings().setAllowFileAccess(true);
//                webView_goto.getSettings().setPluginState(WebSettings.PluginState.ON);
//                webView_goto.getSettings().setUseWideViewPort(true);
//                webView_goto.getSettings().setBuiltInZoomControls(true);
//                webView_goto.requestFocus();
//                webView_goto.getSettings().setLoadWithOverviewMode(true);
//                webView_goto.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
////                webView_goto.loadUrl("http://docs.google.com/gview?embedded=true&url=" +filePath);
//                String baseUrl = "file://"+filePath;
//                webView_goto.loadDataWithBaseURL(baseUrl, filePath, "text/html", "utf-8", null);
//            }
//        }


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        url = intent.getStringExtra(ConstantString.URL);
    }

    @Override
    protected void onInitData() {
        webView_goto.loadUrl(url);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_go_to;
    }
}
