package com.qixiu.intelligentcommunity.mvp.view.activity.home.web;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.engine.ShareLikeEngine;
import com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface.JsInterfaceInfo;
import com.qixiu.intelligentcommunity.mvp.view.activity.web.WebActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.home.web.HomeWebFragment;

import cn.sharesdk.wechat.friends.Wechat;

import static com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant.TORELEASE_REQUESTCODE;
import static com.qixiu.intelligentcommunity.constants.IntentRequestCodeConstant.TORELEASE_RESULTCODE;


/**
 * Created by gyh on 2017/6/26 0026.
 */

public class HomeWebActivity extends WebActivity {
    private JsInterfaceInfo mJsInterfaceInfo;
    private TextView mTv_title;
    private HomeWebFragment mHomeWebFragment;
    private String mAction;
    private ShareLikeEngine mShareLikeEngine;

    @Override
    protected void onOtherClick(View view) {

    }

    @Override
    protected void onInitView() {
        super.onInitView();

        // mTv_more.setText(R.string.release);
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText(StringConstants.EMPTY_STRING);
        mTv_more.setVisibility(View.GONE);
        mAction = getIntent().getAction();
        // tv_title.setText(R.string.seekhelp);
        //  setIsWebGoBack(true);
        mHomeWebFragment = new HomeWebFragment();
        switchFragment(mHomeWebFragment, R.id.fl_neighborhood, getIntent().getExtras(), HomeWebFragment.class.getSimpleName());
    }

    @Override
    protected void onTitleRightClick() {
        if (IntentDataKeyConstant.HOME_MYCARD_ACTION.equals(mAction)) {
            if (mShareLikeEngine == null) {
                mShareLikeEngine = new ShareLikeEngine();
            }
            String stringExtra = getIntent().getStringExtra(IntentDataKeyConstant.WEB_URL_KEY);
            //  mShareLikeEngine.releaseShareData(this, shareIconUrl, "华夏生活欢迎您", stringExtra);
            mShareLikeEngine.shareSdkShare(null, null, stringExtra, Wechat.NAME, getContext().getString(R.string.app_name)+"欢迎您", false);
        } else {
            Intent intent = new Intent(this, HomeWebDetailActivity.class);
            intent.putExtra(IntentDataKeyConstant.JSINTERFACEINFO_KEY, mJsInterfaceInfo);
            startActivityForResult(intent, TORELEASE_REQUESTCODE);
        }

    }


    @Override
    protected void onInitData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_homeweb;
    }

    @Override
    public void onWebShow(JsInterfaceInfo jsInterfaceInfo) {
        this.mJsInterfaceInfo = jsInterfaceInfo;
        mTv_title.setText(mJsInterfaceInfo.getTitle());
        if (IntentDataKeyConstant.HOME_NEIGH_ACTION.equals(mAction) || IntentDataKeyConstant.HOME_HELP_ACTION.equals(mAction) || IntentDataKeyConstant.HOME_MYCARD_ACTION.equals(mAction)) {

            mTv_more.setVisibility(View.VISIBLE);

            mTv_more.setText(IntentDataKeyConstant.HOME_MYCARD_ACTION.equals(mAction) ? StringConstants.EMPTY_STRING : getString(R.string.release));


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mTv_more.setCompoundDrawablesWithIntrinsicBounds(IntentDataKeyConstant.HOME_MYCARD_ACTION.equals(mAction) ? R.mipmap.share_icon : 0, 0, 0, 0);
            } else {
                mTv_more.setCompoundDrawables(getResources().getDrawable(IntentDataKeyConstant.HOME_MYCARD_ACTION.equals(mAction) ? R.mipmap.share_icon : 0), null, null, null);
            }

        } else {
            View parent = (View) mTv_more.getParent();
            parent.setEnabled(false);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TORELEASE_REQUESTCODE && resultCode == TORELEASE_RESULTCODE) {
            mHomeWebFragment.refreshWebView();

        }
    }
}
