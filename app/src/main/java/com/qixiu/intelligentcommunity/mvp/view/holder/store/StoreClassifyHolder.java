package com.qixiu.intelligentcommunity.mvp.view.holder.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.store.classify.StoreClassifyBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;


/**
 * Created by Administrator on 2017/5/2 0002.
 */

public class StoreClassifyHolder extends RecyclerBaseHolder<StoreClassifyBean.StoreClassifyInfo.GoodsBean.GoodListBean> {

    private final TextView mTv_title;

    private final TextView mTv_calssify_price;
    private final ImageView mIv_classify_icon;

    public StoreClassifyHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mTv_title = (TextView) itemView.findViewById(R.id.tv_title);
        mTv_calssify_price = (TextView) itemView.findViewById(R.id.tv_calssify_price);
        mIv_classify_icon = (ImageView) itemView.findViewById(R.id.iv_classify_icon);
    }

    @Override
    public void bindHolder(int position) {
        if (mData != null) {
            mTv_title.setText(mData.getGoods_name());
            mTv_calssify_price.setText(StringConstants.STRING_RMB+mData.getShop_price());
            Glide.with(BaseApplication.getContext()).load(ConstantUrl.hosturl + mData.getThumb_url()).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIv_classify_icon);


        }
    }
}
