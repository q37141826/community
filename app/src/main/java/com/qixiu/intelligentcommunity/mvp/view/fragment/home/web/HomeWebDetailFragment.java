package com.qixiu.intelligentcommunity.mvp.view.fragment.home.web;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface.JsInterfaceInfo;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.fragment.home.jsinterface.JsInterface;
import com.qixiu.intelligentcommunity.mvp.view.fragment.web.WebFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.my_interface.web.MWebViewClient;
import com.qixiu.intelligentcommunity.my_interface.web.WebViewClientInterface;
import com.qixiu.intelligentcommunity.utlis.ArshowDialogUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.SplitStringUtils;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant.TORELEASE_RESULTCODE;
import static com.qixiu.intelligentcommunity.constants.StringConstants.SEMICOLON_STRING;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class HomeWebDetailFragment extends WebFragment implements WebViewClientInterface, ArshowDialogUtils.ArshowDialogListener, View.OnClickListener, OKHttpUIUpdataListener {

    private WebView mWv_homewebdetail;
    private ZProgressHUD mZProgressHUD;
    private String mPhone;
    private MyPopForInput mMyPopForInput;
    private OKHttpRequestModel mOkHttpRequestModel;
    private String mSendUrl;
    private String mOb_uid;
    private String mPid;

    @Override
    protected WebView getWebView() {
        return mWv_homewebdetail;
    }

    @Override
    protected void onInitView(View view) {
        mZProgressHUD = new ZProgressHUD(getActivity());
        mWv_homewebdetail = (WebView) view.findViewById(R.id.wv_homewebdetail);
        mWv_homewebdetail.setWebViewClient(new MWebViewClient(this));
        mWv_homewebdetail.addJavascriptInterface(new BaseWebJsInterface(this), JsInterface.JsInterfaceTag);
        mWv_homewebdetail.setWebChromeClient(mWebChromeClient);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_homewebdetail;
    }

    @Override
    protected void popBtn(JsInterfaceInfo jsInterfaceInfo) {
        ToastUtil.toast("提交成功");
        getActivity().setResult(TORELEASE_RESULTCODE);
        finish();
    }

    @Override
    protected void textBtn(JsInterfaceInfo jsInterfaceInfo) {
        mSendUrl = jsInterfaceInfo.getSendUrl();
        mOb_uid = jsInterfaceInfo.getOb_uid();
        mPid = jsInterfaceInfo.getPid();
        mMyPopForInput = new MyPopForInput(getActivity());
        mMyPopForInput.setSendListener(this);
        mMyPopForInput.setHintText("输入内容");
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override
    protected void imageBtn(JsInterfaceInfo jsInterfaceInfo) {
        if (jsInterfaceInfo == null || TextUtils.isEmpty(jsInterfaceInfo.getImgs())) {
            ToastUtil.toast("没有当前图片资源");
            return;
        }
        ArrayList<String> imagesList = new ArrayList<>();
        String imgs = jsInterfaceInfo.getImgs();
        if (imgs.contains(SEMICOLON_STRING)) {
            List<String> strings = SplitStringUtils.startSplit(imgs, SEMICOLON_STRING);
            for (int i = 0; i < strings.size(); i++) {
                imagesList.add(ConstantUrl.nativehostImageurl + strings.get(i));
            }

        } else {
            imagesList.add(ConstantUrl.nativehostImageurl + imgs);
        }
        PhotoPreview.builder().setShowDeleteButton(false).setPhotos(imagesList).setCurrentItem(Integer.parseInt(jsInterfaceInfo.getIndex())).start(
                getContext(), this);
    }

    @Override
    protected void phoneNumber(JsInterfaceInfo jsInterfaceInfo) {
        mPhone = jsInterfaceInfo.getPhone();
        ArshowDialogUtils.showDialog(getActivity(), "确认打给" + jsInterfaceInfo.getName() + "吗？", this);
    }

    @Override
    protected void onInitData() {
        super.onInitData();
        Bundle arguments = getArguments();
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        if (arguments != null) {
            JsInterfaceInfo interfaceInfo = arguments.getParcelable(IntentDataKeyConstant.JSINTERFACEINFO_KEY);
            if (interfaceInfo != null) {

                String subUrl = interfaceInfo.getSubUrl();
                if (TextUtils.isEmpty(subUrl)) {
                    return;
                }
                mZProgressHUD.show();

                if (IntentDataKeyConstant.HOME_GAME_ACTION.equals(getActivity().getIntent().getAction())) {
                    mWv_homewebdetail.loadUrl( subUrl);
                } else {
                    if (subUrl.contains(StringConstants.QUESTIONMARK_STRING)) {
                        subUrl += StringConstants.WEB_WITH_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
                    } else {
                        subUrl += StringConstants.WEB_PARAMETER_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
                    }
                    mWv_homewebdetail.loadUrl(ConstantUrl.WEBURL_PREFIX + subUrl);
                }


            }

        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mZProgressHUD.dismiss();
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

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mSendUrl) || mMyPopForInput == null) {
            return;
        }
        String content = mMyPopForInput.getText().toString();
        if (TextUtils.isEmpty(content) && TextUtils.isEmpty(content.trim())) {
            ToastUtil.toast("请输入内容");
            return;
        }
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put(IntentDataKeyConstant.CONTENT, content);
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        if (!TextUtils.isEmpty(uid)) {
            stringMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }
        if (!TextUtils.isEmpty(mPid)) {
            stringMap.put(IntentDataKeyConstant.PID, mPid);
        }
        if (!TextUtils.isEmpty(mOb_uid)) {
            stringMap.put(IntentDataKeyConstant.OB_UID, mOb_uid);
        }
        try {
            mOkHttpRequestModel.okhHttpPost(ConstantUrl.hosturl + mSendUrl, stringMap, new BaseBean(), false);
            mZProgressHUD.show();
            mZProgressHUD.setMessage("提交中...");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onSuccess(Object data, int i) {
        mZProgressHUD.dismissWithSuccess("提交成功");
        mWv_homewebdetail.reload();
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        mZProgressHUD.dismissWithFailure("提交失败");
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        mZProgressHUD.dismissWithFailure(getString(R.string.not_netwroking));
    }
}
