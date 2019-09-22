package com.qixiu.intelligentcommunity.utlis;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.yancy.imageselector.ImageLoader;

//优化处理并且能够调用摄像机拍照裁剪功能类
public class GlideLoader implements ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load(path)
//                .placeholder(com.yancy.imageselector.R.mipmap.imageselector_photo)
                .placeholder(R.mipmap.loading)
                .dontAnimate()
                .centerCrop()
//                .error(R.mipmap.loading)
                .into(imageView);
//        Glide.with(context).load(path).asBitmap().centerCrop().into(new MyBitmapImageViewTarget(imageView));
    }

}
