package com.qixiu.intelligentcommunity.mvp.view.holder.homepage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.home.MessageListBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.HomeWebActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.RequirReviewActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.AnswerDetailsActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle.MyOwenDetailsActivity;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadHelpDetailsUrl;
import static com.qixiu.intelligentcommunity.constants.ConstantUrl.loadNeighbourDetailsUrl;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class MessageListHolder extends RecyclerBaseHolder<MessageListBean.OBean.ListBean> {
    private ImageView imageView_points_messagelist;
    private TextView textView_messgelist;
    private TextView textView_whosent_messagelist;
    private RelativeLayout relativeLayout_messagelist_item;
    private OnDataRefresh call;

    public MessageListHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        imageView_points_messagelist = (ImageView) itemView.findViewById(R.id.imageView_points_messagelist);
        textView_messgelist = (TextView) itemView.findViewById(R.id.textView_messgelist);
        textView_whosent_messagelist = (TextView) itemView.findViewById(R.id.textView_whosent_messagelist);
        relativeLayout_messagelist_item = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_messagelist_item);
    }

    @Override
    public void bindHolder(int position) {
        //type 消息类型（1.系统消息 2.业主圈 3问答 4求助 5.邻里圈 6.授权） status:1.查看 2.未查看
        if (mData.getStatus() == 2) {
            imageView_points_messagelist.setImageResource(R.mipmap.payrecord_red_point);
        } else {
            imageView_points_messagelist.setImageResource(R.mipmap.payrecord_grey_point);
        }
        textView_messgelist.setText(mData.getMessage());
        switch (mData.getType()) {
            case 1:
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                    }
                });
                textView_whosent_messagelist.setText("【系统消息】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.yancy_red500));
                break;
            case 2:
                textView_whosent_messagelist.setText("【业主圈】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                        Intent intent = new Intent(mContext, MyOwenDetailsActivity.class);
                        intent.putExtra("id", mData.getBuid());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 3:
                textView_whosent_messagelist.setText("【问答】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                        Intent intent = new Intent(mContext, AnswerDetailsActivity.class);
                        intent.putExtra("id", mData.getBuid());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 4:
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                        Intent seekhelpIntent = new Intent(mContext, HomeWebActivity.class);
                        seekhelpIntent.setAction(IntentDataKeyConstant.HOME_HELP_ACTION);
                        seekhelpIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadHelpDetailsUrl + mData.getBuid() + "&" + StringConstants.WEB_WITH_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                        mContext.startActivity(seekhelpIntent);
                    }
                });
                textView_whosent_messagelist.setText("【求助】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                break;
            case 5:
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                        Intent neighIntent = new Intent(mContext, HomeWebActivity.class);
                        neighIntent.setAction(IntentDataKeyConstant.HOME_NEIGH_ACTION);
                        neighIntent.putExtra(IntentDataKeyConstant.WEB_URL_KEY, loadNeighbourDetailsUrl + mData.getBuid() + "&" + StringConstants.WEB_WITH_UIDPREFIX + Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING));
                        mContext.startActivity(neighIntent);

                    }
                });
                textView_whosent_messagelist.setText("【邻里圈】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                break;
            case 6:
                textView_whosent_messagelist.setText("【授权】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                        Intent intent = new Intent(mContext, RequirReviewActivity.class);
                        intent.putExtra("id", mData.getBuid() + "");
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 7:
                textView_whosent_messagelist.setText("【授权详情】");
                textView_whosent_messagelist.setTextColor(mContext.getResources().getColor(R.color.font_grey));
                relativeLayout_messagelist_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readMessage(mData.getId());
                    }
                });
                break;

        }

    }

    private void readMessage(int id) {
        imageView_points_messagelist.setImageResource(R.mipmap.payrecord_grey_point);
        mData.setStatus(3);
        call = (OnDataRefresh) mContext;
        call.refreshData();
        OkHttpUtils.post().url(ConstantUrl.readMessageUrl)
                .addParams("id", id + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {

            }
        });
    }

    public interface OnDataRefresh {
        void refreshData();
    }

    ;
}
