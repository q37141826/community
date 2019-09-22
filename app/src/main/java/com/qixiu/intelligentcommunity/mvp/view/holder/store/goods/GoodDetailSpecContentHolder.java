package com.qixiu.intelligentcommunity.mvp.view.holder.store.goods;

import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;


/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class GoodDetailSpecContentHolder extends DefaultBaseHolder<GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean> {

    private final TextView mTv_goodsdetail_popuspec;

    public GoodDetailSpecContentHolder(View contentView) {
        super(contentView);
        mTv_goodsdetail_popuspec = (TextView) contentView.findViewById(R.id.tv_goodsdetail_popuspec);
    }

    @Override
    public void setData(GoodsDetailBean.GoodsDetailInfos.SpecGoodsPriceBean.SpecBean data) {
        mTv_goodsdetail_popuspec.setText(data.getItem());
    }
}
