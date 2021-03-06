package com.qixiu.intelligentcommunity.mvp.view.widget.titleview;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;


public class TitleView extends BaseView {
    TextView title_text, right_text;
    TextView backImageView;
    private RelativeLayout rl_num_right;

    public TitleView(Context context) {
        super(context);
    }

    public View getLeftView() {
        return backImageView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.title_layout;
    }

    @Override
    protected void initView() {
        title_text = (TextView) mView.findViewById(R.id.title_text);

        right_text = (TextView) mView.findViewById(R.id.right_text);
        backImageView = (TextView) mView.findViewById(R.id.back_image);
        rl_num_right = mView.findViewById(R.id.rl_num_right);
    }

    public void setTitle(String name) {
        title_text.setText(name);
    }

    public void setTitle(int resId) {
        title_text.setText(resId);
    }

    public void setBackListener(View.OnClickListener pClickListener) {
        backImageView.setOnClickListener(pClickListener);
    }

    public void setRightListener(View.OnClickListener pClickListener) {
        right_text.setOnClickListener(pClickListener);
    }

    public void setTitleClickListenner(View.OnClickListener pClickListener) {
        title_text.setOnClickListener(pClickListener);
    }

    public void setBackVisibility(int v) {
        backImageView.setVisibility(v);
    }

    public void setRightTextVisible( int v) {
        right_text.setVisibility(v);
    }

    public void setLeftText(String text) {
        backImageView.setText(text);
    }

    public void setRightText(String text) {
        right_text.setText(text);
    }

    public void setRightImage(int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            right_text.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
        } else {
            right_text.setCompoundDrawables(BaseApplication.getContext().getResources().getDrawable(resource), null, null, null);
        }
    }

    public void setLeftImage(int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            backImageView.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
        } else {
            backImageView.setCompoundDrawables(BaseApplication.getContext().getResources().getDrawable(resource), null, null, null);
        }

    }

    public void setTitleImage(int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            title_text.setCompoundDrawablesWithIntrinsicBounds(0, 0, resource, 0);
        } else {
            title_text.setCompoundDrawables(null, null, BaseApplication.getContext().getResources().getDrawable(resource), null);
        }
    }

    public void setRightValue(String text, View.OnClickListener pClickListener) {
        right_text.setText(text);
        right_text.setOnClickListener(pClickListener);
    }

    public int getLeftId() {
        return backImageView.getId();
    }

    public int getRightId() {
        return right_text.getId();
    }

    public void setTitle_textColor(int color) {
        title_text.setTextColor(color);
    }

    public View getTitle() {
        return title_text;

    }

    public RelativeLayout getRl_num_right(){
        return rl_num_right;
    }
}
