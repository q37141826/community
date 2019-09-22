package com.qixiu.intelligentcommunity.mvp.view.holder.shop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.CheckWhereBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/5/24.
 */

public class CheckWhereHolder extends RecyclerBaseHolder<CheckWhereBean.DataBean> {
    ImageView imageView_isselected;
    TextView textView_content_checkwhere,textView_time_chechwhere;

    public CheckWhereHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_isselected= (ImageView) itemView.findViewById(R.id.imageView_isselected);
        textView_content_checkwhere= (TextView) itemView.findViewById(R.id.textView_content_checkwhere);
        textView_time_chechwhere= (TextView) itemView.findViewById(R.id.textView_time_chechwhere);
    }

    @Override
    public void bindHolder(int position) {
        if(position==0){
            imageView_isselected.setImageResource(R.mipmap.circle_selected);
        }else {
            imageView_isselected.setImageResource(R.mipmap.circle_noselected);
        }
        textView_content_checkwhere.setText(mData.getContext());
        textView_time_chechwhere.setText(mData.getTime());

    }
}
