package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer.OwenAnserListAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class OwenAnswerListHolder extends RecyclerBaseHolder<OwenAnswerListBean.OBean.ListBean> {
    private TextView textView_title_owen_answerlist, textView_title_time_answerlist, textView_content_anserlist, textView_nickname_anserlist, textView_delete_answer_list, textView_leavepic_num;
    private ImageView imageView_anserlist01, imageView_anserlist02, imageView_anserlist03, imageView_comments_answerlist;
    private CircleImageView circular_head_anserlist;
    List<ImageView> listimages = new ArrayList<>();
    public OwenAnserListAdapter.OnAnserRefresh refreshListenner;

    public void setRefreshListenner(OwenAnserListAdapter.OnAnserRefresh refreshListenner){
        this.refreshListenner=refreshListenner;
    }
    public OwenAnswerListHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        textView_leavepic_num = (TextView) itemView.findViewById(R.id.textView_leavepic_num);
        textView_title_owen_answerlist = (TextView) itemView.findViewById(R.id.textView_title_owen_answerlist);
        textView_title_time_answerlist = (TextView) itemView.findViewById(R.id.textView_title_time_answerlist);
        textView_content_anserlist = (TextView) itemView.findViewById(R.id.textView_content_anserlist);
        textView_nickname_anserlist = (TextView) itemView.findViewById(R.id.textView_nickname_anserlist);
        textView_delete_answer_list = (TextView) itemView.findViewById(R.id.textView_delete_answer_list);
        imageView_anserlist01 = (ImageView) itemView.findViewById(R.id.imageView_anserlist01);
        imageView_anserlist02 = (ImageView) itemView.findViewById(R.id.imageView_anserlist02);
        imageView_anserlist03 = (ImageView) itemView.findViewById(R.id.imageView_anserlist03);
        imageView_comments_answerlist = (ImageView) itemView.findViewById(R.id.imageView_comments_answerlist);
        circular_head_anserlist = (CircleImageView) itemView.findViewById(R.id.circular_head_anserlist);

    }

    @Override
    public void bindHolder(final int position) {
        textView_title_owen_answerlist.setText(mData.getTitle());
        textView_title_time_answerlist.setText(mData.getAddtime());
        textView_content_anserlist.setText(mData.getContent());
        textView_nickname_anserlist.setText(mData.getUids().getNickname());
//        //// TODO: 2017/7/4 设置删除按钮是否可见
        textView_delete_answer_list.setVisibility(mData.getDel() == 1 ? View.VISIBLE : View.GONE);
        BaseActivity activity = (BaseActivity) mContext;
        RelativeLayout.LayoutParams params01 = new RelativeLayout.LayoutParams(activity.windowWith / 4, activity.windowWith / 4);
        RelativeLayout.LayoutParams params02 = new RelativeLayout.LayoutParams(activity.windowWith / 4, activity.windowWith / 4);
        params02.addRule(RelativeLayout.CENTER_HORIZONTAL);
        RelativeLayout.LayoutParams params03 = new RelativeLayout.LayoutParams(activity.windowWith / 4, activity.windowWith / 4);
        params03.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        RelativeLayout.LayoutParams paramsAll = new RelativeLayout.LayoutParams(activity.windowWith, activity.windowWith * 3 / 5);
        params01.setMargins(20, 20, 20, 20);
        params02.setMargins(20, 20, 20, 20);
        params03.setMargins(20, 20, 20, 20);
        textView_leavepic_num.setVisibility(View.GONE);
        imageView_anserlist01.setLayoutParams(params01);
        imageView_anserlist02.setLayoutParams(params02);
        imageView_anserlist03.setLayoutParams(params03);
        textView_leavepic_num.setLayoutParams(params03);
        if (mData.getImgs().size() == 0) {
            imageView_anserlist01.setVisibility(View.GONE);
            imageView_anserlist02.setVisibility(View.GONE);
            imageView_anserlist03.setVisibility(View.GONE);
        } else if (mData.getImgs().size() == 1) {
            imageView_anserlist01.setVisibility(View.VISIBLE);
            imageView_anserlist01.setLayoutParams(paramsAll);
            imageView_anserlist02.setVisibility(View.GONE);
            imageView_anserlist03.setVisibility(View.GONE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).into(imageView_anserlist01);
        } else if (mData.getImgs().size() == 2) {
            imageView_anserlist01.setVisibility(View.VISIBLE);
            imageView_anserlist02.setVisibility(View.VISIBLE);
            imageView_anserlist03.setVisibility(View.GONE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).into(imageView_anserlist01);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(1)).into(imageView_anserlist02);
        } else if (mData.getImgs().size() == 3) {
            imageView_anserlist01.setVisibility(View.VISIBLE);
            imageView_anserlist02.setVisibility(View.VISIBLE);
            imageView_anserlist03.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).into(imageView_anserlist01);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(1)).into(imageView_anserlist02);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(2)).into(imageView_anserlist03);
        }
        if (mData.getImg_num() != 0) {
            imageView_anserlist01.setVisibility(View.VISIBLE);
            imageView_anserlist02.setVisibility(View.VISIBLE);
            imageView_anserlist03.setVisibility(View.GONE);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(0)).into(imageView_anserlist01);
            Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getImgs().get(1)).into(imageView_anserlist02);
            textView_leavepic_num.setVisibility(View.VISIBLE);
            textView_leavepic_num.setText("+" + (mData.getImg_num()));
        }
        Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getUids().getHead()).into(circular_head_anserlist);
        textView_delete_answer_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(null, "是否删除", null, position);
            }
        });
    }

    private void setDialog(Object o, String notice, Object o1, int position) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startDelete();
                dialog.dismiss();
            }
        });

        builder.setMessage(notice);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void startDelete() {
        OkHttpUtils.post().url(ConstantUrl.deleteAnswerDetailUrl)
                .addParams("id", mData.getId() + "")
                .addParams("uid", Preference.get(ConstantString.USERID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                    if( baseBean.getC()==1){
                       //通知刷新
                        refreshListenner.refresh(mData);
                    }
                    ToastUtil.showToast(mContext,baseBean.getM());
                } catch (Exception e) {
                }
            }
        });
    }



}
