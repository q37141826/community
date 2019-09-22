package com.qixiu.intelligentcommunity.mvp.view.holder.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomePayRecordBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class HomePayRecordHolder extends RecyclerBaseHolder<HomePayRecordBean.OBean.ListBean> {
    private TextView textView_payrecord_item;
    private ImageView imageView_point_payrecord_item;

    public HomePayRecordHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_payrecord_item = (TextView) itemView.findViewById(R.id.textView_payrecord_item);
        imageView_point_payrecord_item= (ImageView) itemView.findViewById(R.id.imageView_point_payrecord_item);
    }

    @Override
    public void bindHolder(int position) {
//        if(mData.IS_LAST()){
//            imageView_point_payrecord_item.setImageResource(R.mipmap.payrecord_red_point);
//        }else {
            imageView_point_payrecord_item.setImageResource(R.mipmap.payrecord_grey_point);
//        }
        textView_payrecord_item.setText("您在"+mData.getChange_time()+"缴费"+(Double.parseDouble(mData.getUser_money()))+"元");
    }
}
