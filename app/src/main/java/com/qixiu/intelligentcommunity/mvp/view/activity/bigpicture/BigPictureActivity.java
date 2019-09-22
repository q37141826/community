package com.qixiu.intelligentcommunity.mvp.view.activity.bigpicture;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;

/**
 * Created by Administrator on 2017/6/9 009.
 */

public class BigPictureActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mRpv_bigpicture;


    private String mStringExtra;
    private View rl_bigpicture;

    @Override
    protected void onInitData() {
        mStringExtra = getIntent().getStringExtra(IntentDataKeyConstant.BIGPICTURE_KEY);
        Glide.with(this).load(mStringExtra).into(mRpv_bigpicture);
    }

    @Override
    protected void onInitView() {

        mRpv_bigpicture = (ImageView) findViewById(R.id.rpv_bigpicture);
        rl_bigpicture = findViewById(R.id.rl_bigpicture);
        rl_bigpicture.setOnClickListener(this);

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_bigpicture;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
