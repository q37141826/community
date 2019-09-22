package com.qixiu.intelligentcommunity.mvp.view.activity.home.web;

import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;

/**
 * Created by HuiHeZe on 2017/7/13.
 */

public class BrowserInnerActivity extends BaseActivity {
    private RelativeLayout relative_layout_title_browserinner;
    private WebView webview_browser;
    private String url, title;
    //新的弹窗
    private ZProgressHUD zProgressHUD;
    //
    TitleView titleView;

    @Override
    protected void onInitData() {
        try {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            titleView.setTitle(title);
        } catch (Exception e) {
        }
        webview_browser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                zProgressHUD.dismiss();
            }
        });
        zProgressHUD = new ZProgressHUD(this);
        zProgressHUD.setMessage("加载中");

        webview_browser.requestFocusFromTouch();
        webview_browser.getSettings().setJavaScriptEnabled(true);
        webview_browser.getSettings().setDomStorageEnabled(true);
        webview_browser.getSettings().setDefaultTextEncodingName("UTF-8");

//        if (Build.VERSION.SDK_INT >= 19) {
//            webview_browser.getSettings().setLoadsImagesAutomatically(true);
//        } else {
//            webview_browser.getSettings().setLoadsImagesAutomatically(false);
//        }
        //设置滚动条的风格0代表不占用空间在网页的上面
        webview_browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webview_browser.getSettings().setUseWideViewPort(true);
        webview_browser.getSettings().setLoadWithOverviewMode(true);
        webview_browser.loadUrl(url);
        zProgressHUD.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview_browser != null) {
            ViewParent parent = webview_browser.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webview_browser);
            }
            webview_browser.stopLoading();
            webview_browser.getSettings().setJavaScriptEnabled(false);
            webview_browser.clearHistory();
            webview_browser.clearView();
            webview_browser.removeAllViews();
            try {
                webview_browser.destroy();
            } catch (Throwable throwable) {

            }
            webview_browser = null;
        }
    }

    @Override
    protected void onInitView() {
        relative_layout_title_browserinner = (RelativeLayout) findViewById(R.id.relative_layout_title_browserinner);
        webview_browser = (WebView) findViewById(R.id.webview_browser);
        initTitle();
    }

    private void initTitle() {
        titleView = new TitleView(this);
        titleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setTitle("详情");
        relative_layout_title_browserinner.addView(titleView.getView());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity;
    }
}
