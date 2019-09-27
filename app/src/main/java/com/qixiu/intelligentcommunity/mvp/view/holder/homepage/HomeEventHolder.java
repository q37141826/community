package com.qixiu.intelligentcommunity.mvp.view.holder.homepage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.home.HomeBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

/**
 * Created by HuiHeZe on 2017/6/22.
 */

public class HomeEventHolder extends RecyclerBaseHolder<HomeBean.EBean> {
    private TextView textView_event_state_item;
    private ImageView imageView_event_home_item;
    private TextView textView_title_event_item;
    private RelativeLayout relativeLayout_event_item;

    public HomeEventHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_event_home_item = (ImageView) itemView.findViewById(R.id.imageView_event_home_item);
        textView_title_event_item = (TextView) itemView.findViewById(R.id.textView_title_event_item);
        textView_event_state_item = (TextView) itemView.findViewById(R.id.textView_event_state_item);
        relativeLayout_event_item = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_event_item);
    }


    @Override
    public void bindHolder(int position) {
        if (mData.getImgs().size() != 0) {
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).placeholder(R.mipmap.loading).dontAnimate().into(imageView_event_home_item);
        }
        textView_title_event_item.setText(mData.getTitle());
        textView_event_state_item.setText(mData.getAddtime_desc());

        BaseActivity activity = (BaseActivity) mContext;
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(activity.windowWith -3*ArshowContextUtil.dp2px(7), activity.windowWith /2-3*ArshowContextUtil.dp2px(7)/2);
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
////        imageView_event_home_item.setPadding(ArshowContextUtil.dp2px(3), ArshowContextUtil.dp2px(2), ArshowContextUtil.dp2px(3), ArshowContextUtil.dp2px(2));
//        params.setMargins(ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5), ArshowContextUtil.dp2px(5));
//        imageView_event_home_item.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView_event_home_item.setLayoutParams(params);
//        RelativeLayout.LayoutParams paramsOut = new RelativeLayout.LayoutParams(activity.windowWith , activity.windowWith/2);
//        relativeLayout_event_item.setLayoutParams(paramsOut);
//        relativeLayout_event_item.setBackgroundResource(R.mipmap.loukong);
//        switch (mData.getState()) {
//            case 0:
//                textView_event_state_item.setText("活动未开始");
//                break;
//            case 1:
//                textView_event_state_item.setText("活动开始");
//                break;
//            case 2:
//                textView_event_state_item.setText("活动结束");
//                break;
//        }
    }
}
