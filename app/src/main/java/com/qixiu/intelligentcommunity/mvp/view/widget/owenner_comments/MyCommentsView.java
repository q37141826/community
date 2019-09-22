package com.qixiu.intelligentcommunity.mvp.view.widget.owenner_comments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.BaseView;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class MyCommentsView extends BaseView {
    private TextView textView_name1_comments, textView_feed, textView_name2_comments, textView_content_comments;
    private LinearLayout linearlayout_comments_addview;

    public MyCommentsView(Context context) {
        super(context);
    }

    public void setName1(String text) {
        textView_name1_comments.setText(text);
    }

    public void setName2(String text) {
        textView_name2_comments.setText(text);
    }

    public void setContent(String text) {
        textView_content_comments.setText(text);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.my_comments_view;
    }

    public void setClick(View.OnClickListener listener) {
    }

    @Override
    protected void initView() {
        textView_name1_comments = (TextView) mView.findViewById(R.id.textView_name1_comments);
        textView_feed = (TextView) mView.findViewById(R.id.textView_feed);
        textView_name2_comments = (TextView) mView.findViewById(R.id.textView_name2_comments);
        textView_content_comments = (TextView) mView.findViewById(R.id.textView_content_comments);
        linearlayout_comments_addview = (LinearLayout) mView.findViewById(R.id.linearlayout_comments_addview);
    }

}
