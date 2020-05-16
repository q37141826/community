package com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_owen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenCircleAllBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenItemBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenAllAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenCommentsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenPicAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.LoginUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.widget.TimeLineContentTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

/**
 * Created by HuiHeZe on 2017/6/30.
 */

public class OwenerOwenAllHolder extends RecyclerBaseHolder<OwenCircleAllBean.OBean.ListBean> {
    MyPopForInput popForInput, pop_feedback;
    private TimeLineContentTextView tv_content_owen_all;
    private TextView tv_time_owen_all, tv_deltete_owen_all, btn_comment_owen_all, textView_nickname_owen_all, textView_who_priase_owen;
    private ImageView btn_like_owen_all;
    private CircleImageView circular_head_owen_all;
    private LinearLayout linearlayout_comments_all, linearlayout_own_wholike_all, ll_comment;//谁点了赞，谁评论了什么
    //图片部分
    private RecyclerView recyclerview_owen_owenpic;
    private OwenOwenPicAdapter adapter;
    //评论部分
    private RecyclerView recyclerView_owen_comments;
    private OwenOwenCommentsAdapter adapterCommments;

    List<Object> listComments;
    private List<OwenCircleAllBean.OBean.ListBean.CommentBean> listCommentsItem;
    private List<List<OwenCircleAllBean.OBean.ListBean.PpuidBean>> listPupuids;
    //刷新接口
    private OwenOwenAllAdapter.MyRefreshListener refreshListenner;
    //刷新的业务
    private final int DELETE_ALL = 1, DELETE_COMMENTS = 2, COMMENT = 3, FEED_COMMENRT = 4, ZAN = 5;


    public void setRefreshListener(OwenOwenAllAdapter.MyRefreshListener listenner) {
        this.refreshListenner = listenner;
    }


    public OwenerOwenAllHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        ll_comment = (LinearLayout) itemView.findViewById(R.id.ll_comment);
        recyclerView_owen_comments = (RecyclerView) itemView.findViewById(R.id.recyclerView_owen_comments);
        textView_who_priase_owen = (TextView) itemView.findViewById(R.id.textView_who_priase_owen);
        linearlayout_own_wholike_all = (LinearLayout) itemView.findViewById(R.id.linearlayout_own_wholike_all);
        linearlayout_comments_all = (LinearLayout) itemView.findViewById(R.id.linearlayout_comments_all);
        circular_head_owen_all = (CircleImageView) itemView.findViewById(R.id.circular_head_owen_all);
        textView_nickname_owen_all = (TextView) itemView.findViewById(R.id.textView_nickname_owen_all);
        tv_content_owen_all = (TimeLineContentTextView) itemView.findViewById(R.id.tv_content_owen_all);
        tv_time_owen_all = (TextView) itemView.findViewById(R.id.tv_time_owen_all);
        tv_deltete_owen_all = (TextView) itemView.findViewById(R.id.tv_deltete_owen_all);
        btn_comment_owen_all = (TextView) itemView.findViewById(R.id.btn_comment_owen_all);
        btn_like_owen_all = (ImageView) itemView.findViewById(R.id.btn_like_owen_all);
        recyclerview_owen_owenpic = (RecyclerView) itemView.findViewById(R.id.recyclerview_owen_owenpic);
    }

    @Override
    public void bindHolder(final int position) {
        Glide.with(mContext).load(mData.getUids().getHead().startsWith("http") ? mData.getUids().getHead() : ConstantUrl.hosturl + mData.getUids().getHead()).into(circular_head_owen_all);
        textView_nickname_owen_all.setText(mData.getUids().getNickname());
        tv_content_owen_all.setText(mData.getContent().replace("\n", "  "));
        tv_time_owen_all.setText(mData.getAddtime());
        //设置点赞按钮状态
        btn_like_owen_all.setVisibility(mData.getZan() == 2 ? View.GONE : View.VISIBLE);
        if (mData.getZan() == 3) {
            btn_like_owen_all.setImageResource(R.mipmap.icon_zan_selected);
        } else {
            btn_like_owen_all.setImageResource(R.mipmap.icon_not_praise);
        }
        //设置删除是否可以看得见
        tv_deltete_owen_all.setVisibility(mData.getDel() == 1 ? View.VISIBLE : View.GONE);
        //删除
        tv_deltete_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(null, "是否删除", null, position);
            }
        });
        //点击回复按钮之后的事件
        btn_comment_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popForInput = new MyPopForInput(mContext);
                popForInput.setSendListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发送消息
                        if (popForInput.getText().toString().equals("")) {
                            popForInput.dismissWindow();
                            return;
                        }
                        sendCommnit(popForInput.getText().toString(), position);
                        popForInput.dismissWindow();
                    }
                });
            }
        });
        //点赞事件
        btn_like_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                sendPriase(mData.getZan(), position);
            }
        });
        //谁点了赞
        StringBuffer finnalWhoPriase = new StringBuffer("");
        if (mData.getZan_name().size() != 0) {
            linearlayout_own_wholike_all.setVisibility(View.VISIBLE);
            for (int i = 0; i < mData.getZan_name().size(); i++) {
                String s = "";
                if (i == mData.getZan_name().size() - 1) {
                    s = "";
                } else {
                    s = ",";
                }
                finnalWhoPriase.append(mData.getZan_name().get(i).getZan_name() + s);
            }
        } else {
            linearlayout_own_wholike_all.setVisibility(View.GONE);
        }
        textView_who_priase_owen.setText(finnalWhoPriase.toString());

        //装填图片
        adapter = new OwenOwenPicAdapter();
        recyclerview_owen_owenpic.setAdapter(adapter);
        if (mData.getImgs().size() != 0) {
            List<OwenOwenPicBean> list = new ArrayList<>();
            int type = 1;//是哪种图片布局，一张图平摊，四张图是2x2，其他的都是九宫格
            if (mData.getImgs().size() == 4) {
                type = 2;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 2));
            } else if (mData.getImgs().size() == 1) {
                type = 1;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 1));
            } else {
                type = 3;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 3));
            }
            for (int i = 0; i < mData.getImgs().size(); i++) {
                OwenOwenPicBean bean = new OwenOwenPicBean();
                bean.setImagestring(mData.getImgs().get(i));
                bean.setType(type);
                list.add(bean);
            }
            adapter.refreshData(list);
            adapter.setOnItemClickListener(new OnRecyclerItemListener<OwenOwenPicBean>() {
                @Override
                public void onItemClick(View v, OwenOwenPicBean data) {
                    ArrayList<String> datas = new ArrayList<String>();
                    for (int i = 0; i < mData.getImgs().size(); i++) {
                        datas.add(ConstantUrl.hostImageurl + mData.getTb_imgs().get(i));
                    }
                    PhotoPreview.builder().setShowDeleteButton(false).setPhotos(datas).setCurrentItem(recyclerview_owen_owenpic.getChildLayoutPosition(v)).start(
                            (MainActivity) mContext);
                }
            });
        }
        //加载评论列表
        recyclerView_owen_comments.setLayoutManager(new LinearLayoutManager(mContext));
        adapterCommments = new OwenOwenCommentsAdapter();
        recyclerView_owen_comments.setAdapter(adapterCommments);
        listComments = new ArrayList<>();
        listCommentsItem = new ArrayList<>();
        listPupuids = new ArrayList<>();
//        if (mData.getComment() != null && mData.getComment().size() != 0) {
//            listComments.addAll(mData.getComment());
//        }
//        if (mData.getPpuid() != null && mData.getPpuid().size() != 0) {
//            for (int i = 0; i < mData.getPpuid().size(); i++) {
//                if (mData.getPpuid().size() != 0) {
//                    listComments.addAll(mData.getPpuid().get(i));
//                }
//            }
//        }
        //修改为一个评论对应多个回复的模式
        if (mData.getComment() != null) {
            listCommentsItem.addAll(mData.getComment());
        }
        if (mData.getPpuid() != null) {
            listPupuids.addAll(mData.getPpuid());
        }
        if (mData.getComment() != null && mData.getComment().size() != 0) {
            for (int i = 0; i < mData.getComment().size(); i++) {
                listComments.add(mData.getComment().get(i));
                if (mData.getPpuid().get(i) != null && mData.getPpuid().get(i).size() != 0) {
                    listComments.addAll(mData.getPpuid().get(i));
                }
            }
        }
        adapterCommments.refreshData(listComments);
        if (adapterCommments.getDatas().size() != 0) {
            ll_comment.setVisibility(View.VISIBLE);
        } else {
            ll_comment.setVisibility(View.GONE);
        }
        adapterCommments.setOnItemClickListener(new OnRecyclerItemListener<Object>() {
            @Override
            public void onItemClick(View v, Object data) {
                if (data instanceof OwenCircleAllBean.OBean.ListBean.CommentBean) {
                    OwenCircleAllBean.OBean.ListBean.CommentBean bean = (OwenCircleAllBean.OBean.ListBean.CommentBean) data;
                    if ((bean.getUid().getId() + "").equals(LoginUtils.getLoginId())) {
                        //弹出选项弹窗
//                        ToastUtil.showToast(mContext, "弹出删除选项的弹窗");
                        setDialog(bean.getId() + "", "是否删除该评论", data, position);
                    } else {
                        //发送回复
                        sendFeedCommnit(bean.getId(), bean.getUid().getId(), position);
//                        ToastUtil.showToast(mContext, "弹出回复选项的弹窗");
                    }
                } else {
                    OwenCircleAllBean.OBean.ListBean.PpuidBean bean = (OwenCircleAllBean.OBean.ListBean.PpuidBean) data;
                    if ((bean.getUid().getId() + "").equals(LoginUtils.getLoginId())) {
                        //弹出选项弹窗
                        setDialog(bean.getId() + "", "是否删除该评论", data, position);
//                        ToastUtil.showToast(mContext, "弹出删除选项的弹窗");
                    } else {
                        //发送回复
                        sendFeedCommnit(bean.getAid(), bean.getUid().getId(), position);
//                        ToastUtil.showToast(mContext, "弹出回复选项的弹窗");
                    }
                }

            }
        });
    }

    private void sendFeedCommnit(final int pid, final int id, final int position) {
        pop_feedback = new MyPopForInput(mContext);
        pop_feedback.setSendListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (pop_feedback.getText().toString().equals("")) {
                    pop_feedback.dismissWindow();
                    return;
                }
                OkHttpUtils.post().url(ConstantUrl.leaveBackUrl)
                        .addParams("pid", pid + "")
                        .addParams("uid", LoginUtils.getLoginId())
                        .addParams("ob_uid", id + "")
                        .addParams("content", pop_feedback.getText().toString()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        pop_feedback.dismissWindow();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        BaseBean baseBean = GetGson.parseBaseBean(s);
                        if (baseBean.getC() == 1) {
//                            //刷新局部的数据
//                            getItemNewData();
                            getItemNewData(FEED_COMMENRT, position);
                        }
                        ToastUtil.showToast(mContext, baseBean.getM());
                        pop_feedback.dismissWindow();
                    }
                });
            }
        });
    }

    private void sendCommnit(String text, final int position) {
        OkHttpUtils.post().url(ConstantUrl.leaveUrl)
                .addParams("pid", mData.getId() + "")
                .addParams("uid", LoginUtils.getLoginId())
                .addParams("ob_uid", mData.getUid() + "")
                .addParams("content", text).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                ToastUtil.showToast(mContext, GetGson.parseMessageBean(s).getM());
//                //刷新局部的数据
                getItemNewData(COMMENT, position);
            }
        });
    }


    private void sendPriase(final int zan, final int position) {

        String url = ConstantUrl.zanOwenUrl;
        if (zan == 1) {
            url = ConstantUrl.zanOwenUrl;
        } else if (zan == 3) {
            url = ConstantUrl.cancleZanOwenUrl;
        }
        OkHttpUtils.post().url(url)
                .addParams("id", mData.getId() + "")
                .addParams("uid", LoginUtils.getLoginId()).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                btn_like_owen_all.setEnabled(true);
            }

            @Override
            public void onResponse(String s, int i) {

                if (mData.getZan() == 1) {
                    mData.setZan(3);
                } else {
                    mData.setZan(1);
                }
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        SystemClock.sleep(500);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        btn_like_owen_all.setEnabled(true);
                    }
                };
                asyncTask.execute();
                MessageBean messageBean = GetGson.parseMessageBean(s);
                ToastUtil.showToast(mContext, messageBean.getM());
                getItemNewData(ZAN, position);
            }
        });

    }


    private void setDialog(final String pid, final String notice, final Object data, final int position) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (notice.contains("评论")) {
                    //删除评论
                    startdelete(pid, data, position);
                } else {
                    //删除帖子
                    startDelete();
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

    private void startDelete() {
        OkHttpUtils.post().url(ConstantUrl.deleteblogUrl)
                .addParams("uid", LoginUtils.getLoginId())
                .addParams("id", mData.getId() + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                BaseBean baseBean = GetGson.parseBaseBean(s);
                if (baseBean.getC() == 1) {
                    refreshListenner.OnDeleteItem(mData);
                }
                ToastUtil.showToast(mContext, baseBean.getM());
            }
        });

    }

    private void startdelete(String pid, final Object data, final int position) {
        OkHttpUtils.post().url(ConstantUrl.deleteCommentsUrl)
                .addParams("id", pid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                BaseBean baseBean = GetGson.parseBaseBean(s);
                if (baseBean.getC() == 1) {
                    //刷新局部的数据
//                    if (data instanceof OwenCircleAllBean.OBean.ListBean.CommentBean) {
//                        listComments.remove(data);
//                        for (int j = 0; j < listCommentsItem.size(); j++) {
//                            if (((OwenCircleAllBean.OBean.ListBean.CommentBean) data).getId() == listCommentsItem.get(j).getId()) {
//                                    listComments.removeAll(listPupuids.get(j));
//                            }
//                        }
//                    } else {
//                        listComments.remove(data);
//                    }
//                    adapterCommments.notifyDataSetChanged();
//                    refreshListenner.OnRefresh(mData, position);
                    getItemNewData(DELETE_COMMENTS, position);
                }
                ToastUtil.showToast(mContext, baseBean.getM());
            }
        });
    }


    public void getItemNewData(final int type, final int position) {
        OkHttpUtils.post().url(ConstantUrl.refreshOwenItemUrl)
                .addParams("uid", LoginUtils.getLoginId())
                .addParams("id", mData.getId() + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    OwenItemBean owenItemBean = GetGson.init().fromJson(s, OwenItemBean.class);
                    mData.setZan(owenItemBean.getO().get(0).getZan());
                    mData.setPpuid(owenItemBean.getO().get(0).getPpuid());
                    mData.setComment(owenItemBean.getO().get(0).getComment());
                    mData.setZan_name(owenItemBean.getO().get(0).getZan_name());
                    refreshListenner.OnRefresh(mData, position);
                } catch (Exception e) {
                }
            }
        });
    }
}
