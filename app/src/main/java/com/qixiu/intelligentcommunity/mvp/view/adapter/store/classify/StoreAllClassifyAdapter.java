package com.qixiu.intelligentcommunity.mvp.view.adapter.store.classify;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.StoreClassItemBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.DefaultBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;

/**
 * Created by Administrator on 2017/8/25 0025.
 */

public class StoreAllClassifyAdapter extends DefaultBaseAdapter {
    @Override
    public int getLayoutId() {
        return R.layout.item_allclassify;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        return new StoreAllClassifyHolder(itemView);
    }

    private class StoreAllClassifyHolder extends DefaultBaseHolder {

        private final ImageView mIv_allclassify;
        private final TextView mTv_allclassify;

        public StoreAllClassifyHolder(View contentView) {
            super(contentView);

            mIv_allclassify = (ImageView) contentView.findViewById(R.id.iv_allclassify);
            mTv_allclassify = (TextView) contentView.findViewById(R.id.tv_allclassify);
        }

        @Override
        public void setData(Object data) {
            if (data instanceof StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) {
                StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean classifyItemBean = (StoreClassItemBean.StoreClassItemInfo.ClassifyItemBean) data;
                mTv_allclassify.setText(classifyItemBean.getName());
                Glide.with(mIv_allclassify.getContext()).load(ConstantUrl.hosturl + classifyItemBean.getImage()).crossFade().into(mIv_allclassify);
            }
        }
    }
}
