package com.qixiu.intelligentcommunity.mvp.view.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.ServiceBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.BrowserInnerActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;


/**
 * Created by HuiHeZe on 2017/6/26.
 */

public class ServiceHolder extends RecyclerBaseHolder<ServiceBean.OBean> implements View.OnClickListener {
    private TextView textView_service_item, textView_serviceIntroduce_item;
    private ImageView imageView_serviceback_item;
    private RelativeLayout relative_layout_service_item;
    private CallPermission call;

    public ServiceHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);

//        relative_layout_service_item = (RelativeLayout) itemView.findViewById(R.id.relative_layout_service_item);
//        textView_service_item = (TextView) itemView.findViewById(R.id.textView_service_item);
        imageView_serviceback_item = (ImageView) itemView.findViewById(R.id.imageView_serviceback_item);
//        textView_serviceIntroduce_item = (TextView) itemView.findViewById(R.id.textView_serviceIntroduce_item);

    }

    @Override
    public void bindHolder(int position) {
//        textView_service_item.setText(mData.getTitle());
        Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getLogo()).placeholder(R.mipmap.loading).into(imageView_serviceback_item);
//        textView_service_item.setOnClickListener(this);
        imageView_serviceback_item.setOnClickListener(this);
//        relative_layout_service_item.setOnClickListener(this);
//        textView_serviceIntroduce_item.setText(mData.getText());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageView_serviceback_item:

//                Uri content_url = Uri.parse(mData.getUrl().contains("http://")?mData.getUrl():"http://"+mData.getUrl());
//                Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
                if (mData.getType().equals(1 + "")) {
                    Intent intent = new Intent(mContext, BrowserInnerActivity.class);
                    intent.putExtra("url", mData.getUrl());
                    intent.putExtra("title", mData.getTitle());
                    if (!mData.getUrl().contains("/")) {
                        return;
                    }
                    mContext.startActivity(intent);
                } else {
                   if(call!=null){
                       call.call(mData.getUrl());
                   }
                }
                break;
        }
    }

        public void  setCallListenner(CallPermission call){
            this.call=call;
        }
    public interface CallPermission{
        void call(String phone);
    }
}
