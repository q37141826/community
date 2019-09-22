package com.qixiu.intelligentcommunity.mvp.view.fragment.base;

import android.view.View;
import android.view.ViewGroup;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;


/**
 * Created by Administrator on 2017/7/18 0018.
 */

public abstract class TitleFragment extends BaseFragment {

    protected TitleView mTitleView;

    @Override
    protected void onInitView(View view) {
        mTitleView = new TitleView(view.getContext());
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.vg_title);
        if (viewGroup != null) {
            viewGroup.addView(mTitleView.getView());

        } else {
            ToastUtil.toast(R.string.main_notfindTitle);
        }
        onInitViewNew(view);
    }

    protected abstract void onInitViewNew(View view);
}
