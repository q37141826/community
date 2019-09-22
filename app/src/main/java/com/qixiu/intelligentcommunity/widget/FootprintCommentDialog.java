package com.qixiu.intelligentcommunity.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.qixiu.intelligentcommunity.R;


/**
 * Author XieXianyong
 * Date: 2017.06.29
 * Description: 评论显示框
 */

public class FootprintCommentDialog extends Dialog implements View.OnClickListener {
    Context context;
    LinearLayout dialogContainerLL;
    LinearLayout inputContainerLL;
    EditText commentET;
    public Button btnSend;
    FootprintCommentListener footprintCommentListener;

    public FootprintCommentDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment_editview);
        setCancelable(true);
        init();
    }

    private void init() {
        dialogContainerLL = (LinearLayout) findViewById(R.id.input_comment_dialog_container);
        inputContainerLL = (LinearLayout) findViewById(R.id.input_comment_container);
        commentET = (EditText) findViewById(R.id.input_comment);
        btnSend = (Button) findViewById(R.id.iv_send);
        dialogContainerLL.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    public void setEditTextHint(String hint) {
        commentET.setHint(hint);
    }

    public void setMemoryCommentListener(FootprintCommentListener footprintCommentListener) {
        this.footprintCommentListener = footprintCommentListener;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (footprintCommentListener != null) {
                    int[] coord = new int[2];
                    inputContainerLL.getLocationOnScreen(coord);
                    // 传入 输入框距离屏幕顶部（不包括状态栏）的长度
                    footprintCommentListener.onShow(coord);
                }
            }
        }, 300);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.input_comment_dialog_container:
                dismiss();
                break;

            case R.id.iv_send:
                String comment = commentET.getText().toString().trim();
                if (TextUtils.isEmpty(comment)) {
                    Toast.makeText(context, "评论不能为空哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (footprintCommentListener != null)
                    footprintCommentListener.comment(comment);
                dismiss();
                break;
        }
    }

    public interface FootprintCommentListener {
        //评论
        public void comment(String comment);

        //dialog输入框显示区域
        void onShow(int[] inputViewCoordinatesOnScreen);
    }
}

