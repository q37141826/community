package com.qixiu.intelligentcommunity.mvp.view.holder.store.goods;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;


/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodDetailCommentHolder extends DefaultBaseHolder<String> {

    private final ImageView mIv_picture;

    public GoodDetailCommentHolder(View contentView) {
        super(contentView);
        mIv_picture = (ImageView) contentView.findViewById(R.id.iv_picture);

    }

    @Override
    public void setData(String data) {
        Glide.with(BaseApplication.getContext()).load(ConstantUrl.hosturl + data).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv_picture);
    }
}
