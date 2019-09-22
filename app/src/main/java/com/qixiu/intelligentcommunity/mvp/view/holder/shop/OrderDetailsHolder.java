package com.qixiu.intelligentcommunity.mvp.view.holder.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderDetailsHolder extends RecyclerBaseHolder<OrderDetailsBean.OBean.GoodsBean> {
    ImageView imageView_orderdetails_icon;
    TextView textView_goodsContent_orderdetails, textView_price_orderdetails, textView_count_orderdetails;

    public OrderDetailsHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_orderdetails_icon= (ImageView) itemView.findViewById(R.id.imageView_orderdetails_icon);
        textView_goodsContent_orderdetails= (TextView) itemView.findViewById(R.id.textView_goodsContent_orderdetails);
        textView_price_orderdetails= (TextView) itemView.findViewById(R.id.textView_price_orderdetails);
        textView_count_orderdetails= (TextView) itemView.findViewById(R.id.textView_count_orderdetails);

    }

    @Override
    public void bindHolder(int position) {
        Glide.with(mContext).load(ConstantUrl.hosturl+mData.getThumb_url()).into(imageView_orderdetails_icon);
        textView_goodsContent_orderdetails.setText(mData.getGoods_name());
        textView_price_orderdetails.setText("¥ "+mData.getGoods_price());
        textView_count_orderdetails.setText("x "+mData.getGoods_num());


    }
}
