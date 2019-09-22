package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerDetailsBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer.AnswerDetailsCommentsInnerAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class AnswerDetailsCommentsHolder extends RecyclerBaseHolder<OwenAnswerDetailsBean.EBean> {
    private CircleImageView circuler_head_answer_comments_details;
    private TextView textView_nickname_answer_details_comments, textView_comments_main_content, textView_time_comments_answer;
    private RecyclerView recyclerView_comments_details_inner;
    private AnswerDetailsCommentsInnerAdapter adapter;
    private LinearLayout linearlayout_comments_answerdetails;
    private MyPopForInput popInput;
    private RefreshListenner call;

    public AnswerDetailsCommentsHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        circuler_head_answer_comments_details = (CircleImageView) itemView.findViewById(R.id.circuler_head_answer_comments_details);
        textView_nickname_answer_details_comments = (TextView) itemView.findViewById(R.id.textView_nickname_answer_details_comments);
        recyclerView_comments_details_inner = (RecyclerView) itemView.findViewById(R.id.recyclerView_comments_details_inner);
        textView_comments_main_content = (TextView) itemView.findViewById(R.id.textView_comments_main_content);
        textView_time_comments_answer = (TextView) itemView.findViewById(R.id.textView_time_comments_answer);
        linearlayout_comments_answerdetails = (LinearLayout) itemView.findViewById(R.id.linearlayout_comments_answerdetails);
    }

    @Override
    public void bindHolder(int position) {
        Glide.with(mContext).load(ConstantUrl.hostImageurl + mData.getUids().getHead()).into(circuler_head_answer_comments_details);
        textView_nickname_answer_details_comments.setText(mData.getUids().getNickname());
        textView_comments_main_content.setText(mData.getContent());
        textView_time_comments_answer.setText(mData.getAdd_time());
        //// TODO: 2017/7/4 加上子评论列表
        adapter = new AnswerDetailsCommentsInnerAdapter();
        recyclerView_comments_details_inner.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView_comments_details_inner.setAdapter(adapter);
        adapter.refreshData(mData.getPpuid());
        call= (RefreshListenner) mContext;
        adapter.setOnItemClickListener(new OnRecyclerItemListener<OwenAnswerDetailsBean.EBean.PpuidBean>() {
            @Override
            public void onItemClick(View v, final OwenAnswerDetailsBean.EBean.PpuidBean data) {
                if ((data.getAuid() + "").equals(Preference.get(ConstantString.USERID, ""))) {
                    setDialog(data.getId() + "", "确认删除该评论");
                }else {
                    popInput = new MyPopForInput(mContext);
                    popInput.setSendListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            send(data.getId(),mData.getUid());
                        }
                    });
                }

            }
        });
        linearlayout_comments_answerdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((mData.getUid() + "").equals(Preference.get(ConstantString.USERID, ""))) {
                    setDialog(mData.getId() + "", "确认删除该评论");
                } else {
                    popInput=new MyPopForInput(mContext);
                    popInput.setSendListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            send(mData.getId(),mData.getUid());
                        }
                    });
                }
            }
        });
    }

    private void send(int mDataId, int id) {
        if(popInput.getText().toString().equals("")){
            popInput.dismissWindow();
            return;
        }
            OkHttpUtils.post().url(ConstantUrl.answerReplyUrl)
                    .addParams("uid",Preference.get(ConstantString.USERID,""))
                    .addParams("pid",mDataId+"")
                    .addParams("ob_uid",id+"")
                    .addParams("content",popInput.getText()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int i) {

                }
                @Override
                public void onResponse(String s, int i) {
                    try {
                        BaseBean baseBean = GetGson.parseBaseBean(s);
                        if (baseBean.getC() == 1) {
                            call.refresh();
                        }
                        ToastUtil.showToast(mContext, baseBean.getM());
                    } catch (Exception e) {
                    }
                }
            });
    }

    public interface RefreshListenner {
        void refresh();
    }


    private void setDialog(final String pid, final String notice) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notice.contains("评论")) {
                    //删除评论
                    startdelete(pid);
                }
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

    private void startdelete(String pid) {
        OkHttpUtils.post().url(ConstantUrl.deleteCommentsUrl)
                .addParams("id", pid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                    if (baseBean.getC() == 1) {
                        call.refresh();
                    }
                    ToastUtil.showToast(mContext, baseBean.getM());
                } catch (Exception e) {
                }
            }
        });
    }
}
