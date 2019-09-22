package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.qixiu.intelligentcommunity.R;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class PopuWindowFactory {

    public interface OnClickCameraOrSelectPhotoListener {
        void onClickCamera();

        void onSelectPhoto();

        void onClose();

    }

    public static PopupWindow showCameraOrSelectPhoto(Context context, View parent, int gravity, int x, int y, final OnClickCameraOrSelectPhotoListener onClickCameraOrSelectPhotoListener) {
        View contentView = null;
        //判断是点击产品详情的popuWindow还是加入购物车的

        contentView = View.inflate(context, R.layout.layout_web_upload_pp, null);
        View rl_camera = contentView.findViewById(R.id.rl_camera);
        View rl_photo_select = contentView.findViewById(R.id.rl_photo_select);
        View tv_close = contentView.findViewById(R.id.tv_close);


        contentView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_bottom_in_2));


        final PopupWindow pw = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        if (!pw.isShowing()) {
            pw.showAtLocation(parent
                    ,
                    gravity, x, y);
        }

        rl_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCameraOrSelectPhotoListener != null) {
                    onClickCameraOrSelectPhotoListener.onClickCamera();

                }
                if (pw.isShowing()) {
                    pw.dismiss();
                }
            }
        });
        rl_photo_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCameraOrSelectPhotoListener != null) {
                    onClickCameraOrSelectPhotoListener.onSelectPhoto();
                }
                if (pw.isShowing()) {
                    pw.dismiss();
                }
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCameraOrSelectPhotoListener != null) {
                    onClickCameraOrSelectPhotoListener.onClose();
                }
            }
        });

        return pw;
    }


}
