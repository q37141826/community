package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenMineBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.EditDynamicActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.MyOwenDetailsActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenOwenMineHolder extends RecyclerBaseHolder<OwenOwenMineBean.OBean.ListBean.DataBeanX.DataBean> {
    private LinearLayout linearlayout_photoselect_owen_mine, linearlayout_goto_owenDetail;
    private TextView textView_month_owen_mine, textView_date_owen_mine, tv_content_owen_mine;
    private ImageView iv_dynamic_photo_owen_mine, iv_photoselect_owen_mine;
    private RelativeLayout relativeLayout_back_owen_mine;

    public OwenOwenMineHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        linearlayout_photoselect_owen_mine = (LinearLayout) itemView.findViewById(R.id.linearlayout_photoselect_owen_mine);
        textView_month_owen_mine = (TextView) itemView.findViewById(R.id.textView_month_owen_mine);
        textView_date_owen_mine = (TextView) itemView.findViewById(R.id.textView_date_owen_mine);
        tv_content_owen_mine = (TextView) itemView.findViewById(R.id.tv_content_owen_mine);
        iv_dynamic_photo_owen_mine = (ImageView) itemView.findViewById(R.id.iv_dynamic_photo_owen_mine);
        iv_photoselect_owen_mine = (ImageView) itemView.findViewById(R.id.iv_photoselect_owen_mine);
        linearlayout_goto_owenDetail = (LinearLayout) itemView.findViewById(R.id.linearlayout_goto_owenDetail);
        relativeLayout_back_owen_mine = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_back_owen_mine);
    }

    @Override
    public void bindHolder(int position) {
        iv_photoselect_owen_mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//// TODO: 2017/7/3 跳转发布界面
                CommonUtils.startIntent(mContext, EditDynamicActivity.class);
            }
        });
        if (mData.getAddtime().equals("-1")) {
            linearlayout_photoselect_owen_mine.setVisibility(View.VISIBLE);
            linearlayout_goto_owenDetail.setVisibility(View.GONE);
            return;
        }
        if (position == 0) {
            linearlayout_goto_owenDetail.setVisibility(View.VISIBLE);
            linearlayout_photoselect_owen_mine.setVisibility(View.VISIBLE);
        } else {
            linearlayout_photoselect_owen_mine.setVisibility(View.GONE);
        }
        if (mData.IS_FiRST()) {
            if (mData.getAddtime().contains("/")) {
                textView_month_owen_mine.setText(getMySpit(mData.getAddtime(), "/")[0] + "/");
                textView_date_owen_mine.setText((getMySpit(mData.getAddtime(), "/")[1]));
            } else {
                textView_month_owen_mine.setText(mData.getAddtime());
                textView_date_owen_mine.setText("");
            }
        }else {
            textView_month_owen_mine.setText("");
            textView_date_owen_mine.setText("");
        }
        tv_content_owen_mine.setText(CommonUtils.getLimitNumText(mData.getContent(), 200));
        if (mData.getImgs().size() != 0) {
            iv_dynamic_photo_owen_mine.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).placeholder(R.mipmap.loading).into(iv_dynamic_photo_owen_mine);
            relativeLayout_back_owen_mine.setBackgroundResource(R.color.white);
//            tv_content_owen_mine.setBackgroundResource(R.color.white);
        } else {
            iv_dynamic_photo_owen_mine.setVisibility(View.GONE);
//            tv_content_owen_mine.setBackgroundResource(R.color.theme_color);
            relativeLayout_back_owen_mine.setBackgroundResource(R.color.weixin_textback);
        }
        linearlayout_goto_owenDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyOwenDetailsActivity.class);
                intent.putExtra("id", mData.getId());
                mContext.startActivity(intent);
            }
        });
    }

    public String[] getMySpit(String s, String sign) {
        String[] result = new String[2];
        int start = s.indexOf(sign);
        String substringstart = s.substring(0, start);
        String substringend = s.substring(start, s.length());
        result[0] = substringstart.replace("/", "");
        result[1] = substringend.replace("/", "");
        return result;
    }
}
