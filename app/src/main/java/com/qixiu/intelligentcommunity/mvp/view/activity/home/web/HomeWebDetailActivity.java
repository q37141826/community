package com.qixiu.intelligentcommunity.mvp.view.activity.home.web;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.ShareLikeEngine;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface.JsInterfaceInfo;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.web.WebActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.home.web.HomeWebDetailFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant.TORELEASE_RESULTCODE;


/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class HomeWebDetailActivity extends WebActivity implements OKHttpUIUpdataListener {

    private String mAction;
    private ShareLikeEngine mShareLikeEngine;
    private JsInterfaceInfo mInterfaceInfo;
    private OKHttpRequestModel mOkHttpRequestModel;
    private ZProgressHUD mZProgressHUD;

    @Override
    protected void onOtherClick(View view) {

    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mZProgressHUD = new ZProgressHUD(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(StringConstants.EMPTY_STRING);
        mTv_more.setText(StringConstants.EMPTY_STRING);
        mTv_more.setVisibility(View.GONE);
        mInterfaceInfo = getIntent().getParcelableExtra(IntentDataKeyConstant.JSINTERFACEINFO_KEY);
        if (mInterfaceInfo != null) {
            tv_title.setText(mInterfaceInfo.getSubTitle());
            mAction = getIntent().getAction();

            if (StringConstants.SECONDHAND.equals(mInterfaceInfo.getSubTitle())) {
                mTv_more.setVisibility(View.GONE);
            } else {
                if (IntentDataKeyConstant.HOME_RELEASESALE_ACTION.equals(mAction) || IntentDataKeyConstant.HOME_RELEASEGOOD_ACTION.equals(mAction) || IntentDataKeyConstant.HOME_SECONDHAND_ACTION.equals(mAction)) {
                    mTv_more.setText(StringConstants.EMPTY_STRING);
                    mTv_more.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

                        mTv_more.setCompoundDrawablesWithIntrinsicBounds(!IntentDataKeyConstant.HOME_SECONDHAND_ACTION.equals(mAction) ? R.mipmap.delete2x : R.mipmap.share_icon, 0, 0, 0);
                    } else {
                        mTv_more.setCompoundDrawables(getResources().getDrawable(!IntentDataKeyConstant.HOME_SECONDHAND_ACTION.equals(mAction) ? R.mipmap.delete2x : R.mipmap.share_icon), null, null, null);
                    }
                } else {
                    mTv_more.setVisibility(View.GONE);
                }
            }

        } else {
            mTv_more.setVisibility(View.GONE);
            ViewGroup parent = (ViewGroup) mTv_more.getParent();
            parent.setEnabled(false);

        }
        switchFragment(new HomeWebDetailFragment(), R.id.fl_home_detail, getIntent().getExtras(), HomeWebDetailFragment.class.getSimpleName());
    }

    @Override
    protected void onTitleRightClick() {
        if (IntentDataKeyConstant.HOME_SECONDHAND_ACTION.equals(mAction) &&!StringConstants.SECONDHAND.equals(mInterfaceInfo.getSubTitle())) {
            if (mShareLikeEngine == null) {
                mShareLikeEngine = new ShareLikeEngine();
            }
            String subUrl = mInterfaceInfo.getSubUrl();
            if (subUrl.contains(StringConstants.QUESTIONMARK_STRING)) {
                subUrl += StringConstants.WEB_WITH_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
            } else {
                subUrl += StringConstants.WEB_PARAMETER_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
            }
            mShareLikeEngine.releaseShareData(this, null, R.string.app_name+"欢迎您", ConstantUrl.WEBURL_PREFIX + subUrl);
        } else if (IntentDataKeyConstant.HOME_RELEASESALE_ACTION.equals(mAction)) {
            mZProgressHUD.show();
            String subId = mInterfaceInfo.getSubId();
            Map<String, String> stringMap = new HashMap<>();
            if (!TextUtils.isEmpty(subId)) {
                stringMap.put(IntentDataKeyConstant.ID, subId);

            }
            stringMap.put("state", "2");
            mOkHttpRequestModel.okhHttpPost(ConstantUrl.personalHouseSaveUrl, stringMap, new BaseBean(), false);
        } else if (IntentDataKeyConstant.HOME_RELEASEGOOD_ACTION.equals(mAction)) {
            if (!mZProgressHUD.isShowing()) {
                mZProgressHUD.show();
            }
            String subId = mInterfaceInfo.getSubId();
            Map<String, String> stringMap = new HashMap<>();
            if (!TextUtils.isEmpty(subId)) {
                stringMap.put(IntentDataKeyConstant.ID, subId);
            }
            stringMap.put("state", "2");
            mOkHttpRequestModel.okhHttpPost(ConstantUrl.secondHandSaveUrl, stringMap, new BaseBean(), false);
        }
    }

    @Override
    protected void onInitData() {
        mOkHttpRequestModel = new OKHttpRequestModel(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_homewebdetail;
    }

    @Override
    public void onWebShow(JsInterfaceInfo jsInterfaceInfo) {

    }

    @Override
    public void onSuccess(Object data, int i) {
        if (mZProgressHUD.isShowing()) {
            mZProgressHUD.dismiss();
        }
        ToastUtil.toast("删除成功");
        setResult(TORELEASE_RESULTCODE);
        finish();
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (mZProgressHUD.isShowing())
            mZProgressHUD.dismissWithFailure(getString(R.string.not_netwroking));
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        if (mZProgressHUD.isShowing())
            mZProgressHUD.dismissWithFailure("删除失败");
    }
}
