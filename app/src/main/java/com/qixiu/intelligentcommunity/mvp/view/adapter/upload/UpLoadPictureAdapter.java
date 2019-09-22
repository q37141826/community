package com.qixiu.intelligentcommunity.mvp.view.adapter.upload;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class UpLoadPictureAdapter extends RecyclerBaseAdapter {

    private int width_grid;
    private int maxCount = 9;
    private int number = 4;


    @Override
    public int getLayoutId() {
        return R.layout.item_upload_pictrue;
    }

    public void setMaxPictureCount(int maxCount) {
        if (maxCount <= 0) {
            this.maxCount = 9;
        } else {
            this.maxCount = maxCount;
        }


    }

    public void setColumnNumber(int number) {

        this.number = number;
    }

    public int getColumnNumber() {
        return number;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        setTileWith(itemView, context);
        return new UpLoadPictureHolder(itemView, context, this);
    }

    private void setTileWith(View itemView, Context context) {
        if (width_grid == 0) {
            width_grid = (int) ((AndroidUtils.getDeviceWidth(context)));
        }
        int width = width_grid / number - AndroidUtils.dip2px(context, 12);
        int height = width;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        itemView.setLayoutParams(layoutParams);
    }

    @Override

    public void onBindViewHolder(RecyclerBaseHolder holder, int position) {
        if (position == getDatas().size()) {
            holder.bindHolder(position);
        } else {
            super.onBindViewHolder(holder, position);
        }


    }

    private class UpLoadPictureHolder extends RecyclerBaseHolder<String> {


        private final ImageView mIv_picture;
        private final View mRl_item;


        public UpLoadPictureHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
            super(itemView, context, adapter);
            mIv_picture = (ImageView) itemView.findViewById(R.id.iv_picture);
            mRl_item = itemView.findViewById(R.id.rl_item);

        }

        @Override
        public void bindHolder(int position) {
            if (position == ((RecyclerBaseAdapter) mAdapter).getDatas().size()) {// 添加末尾
                Glide.with(itemView.getContext()).load(R.mipmap.icon_addpic_unfocused).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().centerCrop().into(mIv_picture);
                mRl_item.setVisibility(position == maxCount ? View.GONE : View.VISIBLE);

            } else {// 添加图片
                if (mRl_item.getVisibility() == View.GONE) {
                    mRl_item.setVisibility(View.VISIBLE);
                }
                Glide.with(itemView.getContext()).load(mData).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).crossFade().centerCrop().into(mIv_picture);
            }
        }
    }
}
