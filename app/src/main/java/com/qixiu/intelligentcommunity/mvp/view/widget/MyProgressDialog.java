package com.qixiu.intelligentcommunity.mvp.view.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;


public class MyProgressDialog extends AlertDialog {
    private static ProgressDialog mProgressDialog;
    //旋转动画
    private AnimationDrawable animation;
    private ImageView imageView;
    private TextView textView_progress_dialog;
    private View view;
    private static OnDismissListener call;

    public MyProgressDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    public static MyProgressDialog getDialog(Context context) {
        return new MyProgressDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        textView_progress_dialog = (TextView) findViewById(R.id.textView_progress_dialog);
//        //加载动画资源
//        animation = AnimationUtils.loadAnimation(getContext(), R.anim.progress_rotate);
//        //动画完成后，是否保留动画最后的状态，设为true
//        animation.setFillAfter(true);
        //加载动画资源
        animation = (AnimationDrawable) imageView.getDrawable();
        view = getLayoutInflater().inflate(R.layout.progressdialog_view, null);
        view.getBackground().setAlpha(150);
    }

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param message
     */
    public static void showProgressDialog(Context context, CharSequence message) {
        try {
            call = (OnDismissListener) context;
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, "", message);
                mProgressDialog.setCancelable(true);//设置进度条是否可以按退回键取消
                mProgressDialog.setCanceledOnTouchOutside(true);//设置点击进度对话框外的区域对话框消失
                mProgressDialog.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.onDismiss();
                    }
                });
            } else {
                mProgressDialog.show();
                mProgressDialog.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        call.onDismiss();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setCacleble(boolean f) {
        if (mProgressDialog == null) {
            mProgressDialog.setCanceledOnTouchOutside(f);
        }
    }

    ;

    /**
     * 关闭ProgressDialog
     */
    public static void dismissProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
        }
    }

    /**
     * 在AlertDialog的 onStart() 生命周期里面执行开始动画
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (animation != null) {
//            imageView.startAnimation(animation);
            animation.start();
        }
    }

    /**
     * 在AlertDialog的onStop()生命周期里面执行停止动画
     */
    @Override
    protected void onStop() {
        super.onStop();
//        imageView.clearAnimation();
        animation.stop();
    }


    public interface OnDismissListener {
        void onDismiss();
    }
}
