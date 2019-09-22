package com.qixiu.intelligentcommunity.mvp.view.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.ClassifyIntef;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.ToSeeAllBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.GlideLoader;

public class StoreClassifyAdapterNew extends RecyclerBaseAdapter {

    @Override
    public int getLayoutId() {
        return R.layout.home_store_classify_layout;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new StoreClassifyHolderNew(itemView, context, this);
    }

    public class StoreClassifyHolderNew extends RecyclerBaseHolder {
        ImageView ivStoreClassifyItem;
        TextView tvStoreClassifyItem;

        public StoreClassifyHolderNew(View itemView, Context context, RecyclerView.Adapter adapter) {
            super(itemView, context, adapter);
            ivStoreClassifyItem = itemView.findViewById(R.id.ivStoreClassifyItem);
            tvStoreClassifyItem = itemView.findViewById(R.id.tvStoreClassifyItem);
        }

        @Override
        public void bindHolder(int position) {
            if (mData instanceof ToSeeAllBean) {
                ToSeeAllBean toSeeAllBean = (ToSeeAllBean) mData;
                Glide.with(mContext).load(toSeeAllBean.getImageRes()).into(ivStoreClassifyItem);
                tvStoreClassifyItem.setText(toSeeAllBean.getName());
            }
            if(mData instanceof StoreBean.StoreInfo.ClassifyItemBean){
                StoreBean.StoreInfo.ClassifyItemBean bean= (StoreBean.StoreInfo.ClassifyItemBean) mData;
                Glide.with(mContext).load(bean.getImage().startsWith("http")?bean.getImage(): ConstantUrl.hosturl +bean.getImage()).into(ivStoreClassifyItem);
                tvStoreClassifyItem.setText(bean.getName());
            }
        }
    }
}
