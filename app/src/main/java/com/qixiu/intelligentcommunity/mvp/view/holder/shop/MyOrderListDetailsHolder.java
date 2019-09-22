package com.qixiu.intelligentcommunity.mvp.view.holder.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;


/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class MyOrderListDetailsHolder extends RecyclerBaseHolder<OrderBean.OBean.ListBean.GoodsBean> {
    ImageView imageView_orderIcon;
    TextView textView_goodsContent,textView_goodsDetails,textView_goodsPrice,textView_goodsCount;


    public MyOrderListDetailsHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_orderIcon= (ImageView) itemView.findViewById(R.id.imageView_orderIcon);
        textView_goodsContent= (TextView) itemView.findViewById(R.id.textView_goodsContent);
        textView_goodsDetails= (TextView) itemView.findViewById(R.id.textView_goodsDetails);
        textView_goodsPrice= (TextView) itemView.findViewById(R.id.textView_goodsPrice);
        textView_goodsCount= (TextView) itemView.findViewById(R.id.textView_goodsCount);

    }

    @Override
    public void bindHolder(int position) {
        Glide.with(mContext).load(ConstantUrl.hosturl+mData.getThumb_url()).into(imageView_orderIcon);
        textView_goodsContent.setText(mData.getGoods_name());
        textView_goodsDetails.setText(mData.getSpec_key_name());
        textView_goodsPrice.setText(mData.getGoods_price());
        textView_goodsCount.setText("x "+mData.getGoods_num());

    }
}
