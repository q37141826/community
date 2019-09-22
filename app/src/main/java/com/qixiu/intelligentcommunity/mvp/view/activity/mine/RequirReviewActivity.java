package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.login.RequireDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/*
* 业主审核界面
* */
public class RequirReviewActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout relativeLayout_titile_reqirereview;
    private TextView textView_estate_requireReview, textView_identity_requireReview, textView_name_requireReview,
            textView_period_requireReview, textView_building_requireReview, textView_unit_requireReview, textView_door_requireReview, textView_idcard_requireReview;
    private ImageView imageView_reqireReview;
    private Button btn_agree_require, btn_refuse_require;
    private RequireDetailsBean bean;

    @Override
    protected void onInitData() {
        setData();
    }

    private void setData() {
        OkHttpUtils.post().url(ConstantUrl.requireUrl)
                .addParams("id", getIntent().getStringExtra("id")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    bean = GetGson.init().fromJson(s, RequireDetailsBean.class);
                    textView_estate_requireReview.setText(bean.getO().getEstate());
                    // "utype": ,//（1.业主 2.亲友 3.租客）
                    String usertype = "业主";
                    if (bean.getO().getUtype() == 2) {
                        usertype = "亲友";
                    } else {
                        usertype = "租客";
                    }
                    textView_identity_requireReview.setText(usertype);
                    textView_name_requireReview.setText(bean.getO().getTrue_name()+"");
                    textView_period_requireReview.setText(bean.getO().getPeriod()+"");
                    textView_building_requireReview.setText(bean.getO().getBuilding()+"");
                    textView_unit_requireReview.setText(bean.getO().getUnit()+"");
                    textView_door_requireReview.setText(bean.getO().getHnum()+"");
                    textView_idcard_requireReview.setText(bean.getO().getIdcard()+"");
                    Glide.with(RequirReviewActivity.this).load(ConstantUrl.hostImageurl + bean.getO().getFace_img()).centerCrop().dontAnimate().placeholder(R.mipmap.loading).into(imageView_reqireReview);
                } catch (Exception e) {

                }
            }
        });


    }

    @Override
    protected void onInitView() {
        btn_refuse_require = (Button) findViewById(R.id.btn_refuse_require);
        btn_agree_require = (Button) findViewById(R.id.btn_agree_require);
        relativeLayout_titile_reqirereview = (RelativeLayout) findViewById(R.id.relativeLayout_titile_reqirereview);
        textView_estate_requireReview = (TextView) findViewById(R.id.textView_estate_requireReview);
        textView_identity_requireReview = (TextView) findViewById(R.id.textView_identity_requireReview);
        textView_name_requireReview = (TextView) findViewById(R.id.textView_name_requireReview);
        textView_period_requireReview = (TextView) findViewById(R.id.textView_period_requireReview);
        textView_building_requireReview = (TextView) findViewById(R.id.textView_building_requireReview);
        textView_unit_requireReview = (TextView) findViewById(R.id.textView_unit_requireReview);
        textView_door_requireReview = (TextView) findViewById(R.id.textView_door_requireReview);
        textView_idcard_requireReview = (TextView) findViewById(R.id.textView_idcard_requireReview);
        imageView_reqireReview = (ImageView) findViewById(R.id.imageView_reqireReview);
        initTitle();
        initclick();
    }

    private void initclick() {
        btn_refuse_require.setOnClickListener(this);
        btn_agree_require.setOnClickListener(this);
    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setTitle("资料认证");
        title.setRightText("");
        title.setRightImage(R.mipmap.authorization_material_close);
        title.setRightTextVisible(View.VISIBLE);
        relativeLayout_titile_reqirereview.addView(title.getView());
        title.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startIntent(RequirReviewActivity.this,LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_requir_review;
    }

    @Override
    public void onClick(View v) {
        if (bean == null||bean.getO().getAuth()==1) {
            ToastUtil.showToast(this, "该申请已经过期");
            return;
        }
        switch (v.getId()) {
            case R.id.btn_agree_require:
                agreeOrRefuse(1);
                break;
            case R.id.btn_refuse_require:
                agreeOrRefuse(3);
                break;
        }
    }

    private void agreeOrRefuse(final int state) {
        OkHttpUtils.post().url(ConstantUrl.agreeOrRefuseUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("id", bean.getO().getId() + "")
                .addParams("state", state + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                ToastUtil.showToast(RequirReviewActivity.this, state == 1 ? "同意该申请" : "拒绝该申请");
                finish();
            }
        });

    }
}
