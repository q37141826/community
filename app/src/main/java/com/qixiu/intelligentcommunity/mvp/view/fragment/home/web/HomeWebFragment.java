package com.qixiu.intelligentcommunity.mvp.view.fragment.home.web;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface.JsInterfaceInfo;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebDetailActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.web.WebFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.my_interface.web.MWebViewClient;
import com.qixiu.intelligentcommunity.my_interface.web.WebViewClientInterface;
import com.qixiu.intelligentcommunity.utlis.ArshowDialogUtils;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import static com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant.TORELEASE_REQUESTCODE;
import static com.qixiu.intelligentcommunity.mvp.view.fragment.home.jsinterface.JsInterface.JsInterfaceTag;


/**
 * Created by Administrator on 2017/6/26 0026.
 */
@SuppressLint("SetJavaScriptEnabled")
public class HomeWebFragment extends WebFragment implements WebViewClientInterface, ArshowDialogUtils.ArshowDialogListener {

    private WebView mWv_neighborhood;
    private String parameterPrefix = "?uid=";
    private String loadUrl;
    private ZProgressHUD zProgressHUD;
    private String mPhone;

    @Override
    protected void onInitData() {
        super.onInitData();

        loadUrl = getArguments().getString(IntentDataKeyConstant.WEB_URL_KEY);
        if (TextUtils.isEmpty(loadUrl)) {
            return;
        }

        loadWebUrl(loadUrl);
    }

    private void loadWebUrl(String loadUrl) {
        zProgressHUD.show();
        mWv_neighborhood.loadUrl(loadUrl);

    }

    @Override
    protected void phoneNumber(JsInterfaceInfo jsInterfaceInfo) {
        mPhone = jsInterfaceInfo.getPhone();
        ArshowDialogUtils.showDialog(getActivity(), "确认打给" + jsInterfaceInfo.getName() + "吗？", this);
    }

    @Override
    protected void popBtn(JsInterfaceInfo jsInterfaceInfo) {
        if (IntentDataKeyConstant.HOME_ONLINE_REPAIRS_ACTION.equals(getActivity().getIntent().getAction())) {
            ToastUtil.toast("提交成功");
            finish();
        }

    }

    public void refreshWebView() {
        mWv_neighborhood.reload();
    }

    @Override
    protected void onInitView(View view) {
        zProgressHUD = new ZProgressHUD(this.getActivity());
        mWv_neighborhood = (WebView) view.findViewById(R.id.wv_neighborhood);
        mWv_neighborhood.setWebViewClient(new MWebViewClient(this));
        mWv_neighborhood.addJavascriptInterface(new BaseWebJsInterface(this), JsInterfaceTag);
        mWv_neighborhood.setWebChromeClient(mWebChromeClient);
    }

    @Override
    protected WebView getWebView() {
        return mWv_neighborhood;
    }


    @Override
    protected void titleName(JsInterfaceInfo jsInterfaceInfo) {


    }

    @Override
    protected void pushBtn(JsInterfaceInfo jsInterfaceInfo) {
        Intent intent = new Intent(getActivity(), HomeWebDetailActivity.class);
        intent.putExtra(IntentDataKeyConstant.JSINTERFACEINFO_KEY, jsInterfaceInfo);
        String action = getActivity().getIntent().getAction();
        intent.setAction(action);
        getActivity().startActivityForResult(intent, TORELEASE_REQUESTCODE);

    }

    @Override
    protected int getLayoutResource() {

        return R.layout.fragment_homeweb;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        zProgressHUD.dismiss();

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onClickPositive(DialogInterface dialogInterface, int which) {

        ArshowContextUtil.callPhone(getActivity(), mPhone);


    }

    @Override
    public void onClickNegative(DialogInterface dialogInterface, int which) {

    }
}
