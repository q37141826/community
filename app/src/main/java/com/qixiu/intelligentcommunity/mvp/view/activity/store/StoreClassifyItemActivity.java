package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.StoreClassifyItemFragment;

/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class StoreClassifyItemActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView() {
        String stringExtra = getIntent().getStringExtra(IntentDataKeyConstant.STORECLASSITEM_NAME_KET);
        View relativelayout_back_myProfile = findViewById(R.id.relativelayout_back_myProfile);
        TextView tv_classhour_head_title = (TextView) findViewById(R.id.tv_classhour_head_title);
        if (!TextUtils.isEmpty(stringExtra)) {
            tv_classhour_head_title.setText(stringExtra);
        }

        relativelayout_back_myProfile.setOnClickListener(this);
        StoreClassifyItemFragment storeClassifyItemFragment = new StoreClassifyItemFragment();

        addFragment(R.id.fl_classify, storeClassifyItemFragment, this.getClass().getSimpleName(), getIntent().getExtras());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_storeclassify;
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
