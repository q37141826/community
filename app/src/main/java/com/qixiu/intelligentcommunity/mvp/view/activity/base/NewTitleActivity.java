package com.qixiu.intelligentcommunity.mvp.view.activity.base;

import android.view.View;
import android.view.ViewGroup;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;


/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class NewTitleActivity extends BaseActivity implements View.OnClickListener {

    protected TitleView mTitleView;

    /**
     * 在这里添加公共的标题
     */
    @Override
    protected void onInitView() {
        mTitleView = new TitleView(this);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.vg_title);
        if (viewGroup != null) {
            viewGroup.addView(mTitleView.getView());

        } else {
            ToastUtil.toast(R.string.main_notfindTitle);
        }
        onInitViewNew();
    }






    protected abstract void onInitViewNew();


}
