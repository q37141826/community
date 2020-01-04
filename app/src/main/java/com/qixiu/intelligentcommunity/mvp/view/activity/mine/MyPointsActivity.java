package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebActivity;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadGame;

public class MyPointsActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean> {
    private OKHttpRequestModel okHttpRequestModel;
    private TextView textView_myPoints;
    private LinearLayout  linearlayout_goto_game;
    @Override
    protected void onInitData() {
        mTitleView.setTitle("我的魔豆");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setRightText("魔豆明细");
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startIntent(MyPointsActivity.this, MyPointsListActivity.class);
            }
        });
        Map<String,String> map=new HashMap<>();
        map.put("uid", Preference.get(ConstantString.USERID,""));
        okHttpRequestModel.okhHttpPost(ConstantUrl.myPointsUrl,map,new BaseBean());
        linearlayout_goto_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameManager = new Intent(MyPointsActivity.this, HomeWebActivity.class);
                gameManager.setAction(IntentDataKeyConstant.HOME_GAME_ACTION);
                gameManager.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadGame + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                startActivity(gameManager);
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_points;
    }

    @Override
    protected void onInitViewNew() {
        textView_myPoints = (TextView) findViewById(R.id.textView_myPoints);
        okHttpRequestModel = new OKHttpRequestModel(this);
        linearlayout_goto_game= (LinearLayout) findViewById(R.id.linearlayout_goto_game);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        textView_myPoints.setText(data.getO().toString());
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        ToastUtil.toast(R.string.link_error);
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        ToastUtil.toast(c_codeBean.getM());
    }
}
