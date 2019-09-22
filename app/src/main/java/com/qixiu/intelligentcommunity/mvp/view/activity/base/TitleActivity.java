package com.qixiu.intelligentcommunity.mvp.view.activity.base;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class TitleActivity extends BaseActivity implements View.OnClickListener {

    protected ImageButton ib_back;
    public View rl_title_right;
    protected TextView mTv_more;
    private TextView tv_title;

    /**
     * 在这里添加公共的标题
     */
    @Override
    protected void onInitView() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        rl_title_right = findViewById(R.id.rl_title_right);
        mTv_more = (TextView) findViewById(R.id.tv_more);
        tv_title =  findViewById(R.id.tv_title);
        if (ib_back != null) {
            ib_back.setOnClickListener(this);

        }
        if (rl_title_right != null) {

            rl_title_right.setOnClickListener(this);
        }
    }

    public void  setTitleText(String text){
        tv_title.setText(text);
    }
    public void  setTitleText(int resId){
        tv_title.setText(resId);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                onBackClick();
                break;
            case R.id.rl_title_right:
                onTitleRightClick();
                break;
            default:
                onOtherClick(v);
                //可以去抽取个方法也可以去扩展 随意
                break;
        }
    }

    protected abstract void onOtherClick(View view);

    protected abstract void onTitleRightClick();

    protected abstract void onBackClick();


}
