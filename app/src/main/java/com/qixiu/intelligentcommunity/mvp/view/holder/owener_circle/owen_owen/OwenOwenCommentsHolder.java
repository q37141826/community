package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenCircleAllBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/7/2 0002.
 */
public class OwenOwenCommentsHolder extends RecyclerBaseHolder<Object> {
    private TextView textView_name1_comments, textView_callback_comments, textView_name2_comments,
            textView_content_comments,textView_time_comments_details,textView_content_use_for_details,textView_maohao;
    private CircleImageView circular_head_comments_owendetail;
    private View line_comments_list;
    public OwenOwenCommentsHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        line_comments_list=itemView.findViewById(R.id.line_comments_list);
        textView_maohao= (TextView) itemView.findViewById(R.id.textView_maohao);
        textView_name1_comments = (TextView) itemView.findViewById(R.id.textView_name1_comments);
        textView_callback_comments = (TextView) itemView.findViewById(R.id.textView_callback_comments);
        textView_name2_comments = (TextView) itemView.findViewById(R.id.textView_name2_comments);
        textView_content_comments = (TextView) itemView.findViewById(R.id.textView_content_comments);
        circular_head_comments_owendetail= (CircleImageView) itemView.findViewById(R.id.circular_head_comments_owendetail);
        textView_time_comments_details= (TextView) itemView.findViewById(R.id.textView_time_comments_details);
        textView_content_use_for_details= (TextView) itemView.findViewById(R.id.textView_content_use_for_details);
    }

    @Override
    public void bindHolder(int position) {
        OwenCircleAllBean.OBean.ListBean.CommentBean  bean1;
        OwenCircleAllBean.OBean.ListBean.PpuidBean bean2;
        OwenOwenDetailsBean.EBean.CommentBean bean3;
        OwenOwenDetailsBean.EBean.PpuidBean bean4;
        textView_content_use_for_details.setVisibility(View.GONE);
        textView_time_comments_details.setVisibility(View.GONE);
        textView_maohao.setVisibility(View.VISIBLE);
        line_comments_list.setVisibility(View.GONE);
        if(mData instanceof OwenCircleAllBean.OBean.ListBean.CommentBean){
            bean1= (OwenCircleAllBean.OBean.ListBean.CommentBean) mData;
            textView_name1_comments.setText(bean1.getUid().getNickname());
            textView_content_comments.setText(bean1.getContent());
            textView_callback_comments.setVisibility(View.GONE);
            circular_head_comments_owendetail.setVisibility(View.GONE);
        }
        if(mData instanceof OwenCircleAllBean.OBean.ListBean.PpuidBean){
            bean2= (OwenCircleAllBean.OBean.ListBean.PpuidBean) mData;
            textView_name1_comments.setText(bean2.getUid().getNickname());
            textView_callback_comments.setVisibility(View.VISIBLE);
            textView_name2_comments.setText(bean2.getOb_uid());
            textView_content_comments.setText(bean2.getContent());
            circular_head_comments_owendetail.setVisibility(View.GONE);
        }
        if(mData instanceof  OwenOwenDetailsBean.EBean.CommentBean){
            line_comments_list.setVisibility(View.VISIBLE);
            textView_maohao.setVisibility(View.GONE);
            bean3= (OwenOwenDetailsBean.EBean.CommentBean) mData;
            textView_name1_comments.setText(bean3.getUid().getNickname());
//            textView_content_comments.setText(bean3.getContent());
            textView_content_use_for_details.setText(bean3.getContent());
            textView_time_comments_details.setText(bean3.getAdd_time());
            textView_callback_comments.setVisibility(View.GONE);
            circular_head_comments_owendetail.setVisibility(View.VISIBLE);
            textView_content_use_for_details.setVisibility(View.VISIBLE);
            textView_time_comments_details.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl+bean3.getUid().getHead()).into(circular_head_comments_owendetail);
        }
        if(mData instanceof  OwenOwenDetailsBean.EBean.PpuidBean){
            line_comments_list.setVisibility(View.VISIBLE);
            textView_maohao.setVisibility(View.GONE);
            bean4= (OwenOwenDetailsBean.EBean.PpuidBean) mData;
            textView_name1_comments.setText(bean4.getUid().getNickname());
            textView_callback_comments.setVisibility(View.VISIBLE);
            textView_name2_comments.setText(bean4.getOb_uid());
//            textView_content_comments.setText(bean4.getContent());
            textView_content_use_for_details.setText(bean4.getContent());
            textView_time_comments_details.setText(bean4.getAdd_time());
            circular_head_comments_owendetail.setVisibility(View.VISIBLE);
            textView_content_use_for_details.setVisibility(View.VISIBLE);
            textView_time_comments_details.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl+bean4.getUid().getHead()).into(circular_head_comments_owendetail);
        }
    }
}
