package com.qixiu.intelligentcommunity.mvp.view.holder.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.GetGoodsRecordBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/9/8.
 */

public class GetGoodsRecorderHolder extends RecyclerBaseHolder<GetGoodsRecordBean.OBean.ListBean> {
    private TextView textView_goods_name_item_record, textView_goods_time_item_record;


    public GetGoodsRecorderHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_goods_name_item_record= (TextView) itemView.findViewById(R.id.textView_goods_name_item_record);
        textView_goods_time_item_record= (TextView) itemView.findViewById(R.id.textView_goods_time_item_record);
    }

    @Override
    public void bindHolder(int position) {
        textView_goods_name_item_record.setText(mData.getName());
        textView_goods_time_item_record.setText(mData.getAddtime());
    }
}
