package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;

/**
 * Created by Administrator on 2017/7/1 0001.九宫格图片
 */
public class OwenOwenPicHolder extends RecyclerBaseHolder<OwenOwenPicBean> {
    private ImageView imageView_owen_owen_pic;

    public OwenOwenPicHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_owen_owen_pic = (ImageView) itemView.findViewById(R.id.imageView_owen_owen_pic);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void bindHolder(int position) {
        BaseActivity activity = (BaseActivity) mContext;
        if (mData.getType() == 1) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(activity.windowWith, activity.windowWith * 3 / 5);
            params.setMargins(ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5));
            imageView_owen_owen_pic.setLayoutParams(params);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(activity.windowWith / 4, activity.windowWith / 4);
            params.setMargins(ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5));
            imageView_owen_owen_pic.setLayoutParams(params);
        }
        imageView_owen_owen_pic.setBackgroundResource(R.color.white);
        float a = (float) ArshowContextUtil.dp2px(4);
        imageView_owen_owen_pic.setElevation(a);
        Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImagestring()).into(imageView_owen_owen_pic);
    }


}
