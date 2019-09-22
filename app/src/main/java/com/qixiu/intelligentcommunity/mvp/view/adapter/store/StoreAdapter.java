package com.qixiu.intelligentcommunity.mvp.view.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.impl.GoodsListImpl;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;

/**
 * Created by Administrator on 2017/8/24 0024.
 */

public class StoreAdapter extends RecyclerBaseAdapter {


    @Override
    public int getLayoutId() {
        return R.layout.item_store_list;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
        layoutParams.leftMargin = ArshowContextUtil.dp2px(6);
        layoutParams.topMargin = ArshowContextUtil.dp2px(10);
        layoutParams.rightMargin = ArshowContextUtil.dp2px(6);
        itemView.setLayoutParams(layoutParams);
        return new StoreHolder(itemView, context, this);
    }

    private class StoreHolder extends RecyclerBaseHolder {

        private final ImageView mTv_store_picture;
        private final TextView mTv_item_title;
        private final TextView mTv_item_price;

        public StoreHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
            super(itemView, context, adapter);
            mTv_store_picture = (ImageView) itemView.findViewById(R.id.tv_store_picture);
            mTv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            mTv_item_price = (TextView) itemView.findViewById(R.id.tv_item_price);

        }

        @Override
        public void bindHolder(int position) {
            if (mData instanceof GoodsListImpl) {
                GoodsListImpl goodsListBean = (GoodsListImpl) mData;
                Glide.with(itemView.getContext()).load(ConstantUrl.hosturl + goodsListBean.getThumb_url()).crossFade().into(mTv_store_picture);
                mTv_item_title.setText(goodsListBean.getGoods_name());
                mTv_item_price.setText("¥" + goodsListBean.getShop_price());
            }
        }
    }

}
