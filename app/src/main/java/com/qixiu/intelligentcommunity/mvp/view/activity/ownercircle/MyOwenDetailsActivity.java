package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenDetailsBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenCommentsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenPicAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
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

/*
*
* 业主圈详情
* */
public class MyOwenDetailsActivity extends BaseActivity {
    private RelativeLayout relativeLayout_title_owen_details, ll_comment;
    private LinearLayout linearlayout_own_wholike_all;
    //本帖子的ID
    int id = 0;
    //头像等控件
    private CircleImageView circular_head_owen_all;
    private TextView textView_nickname_owen_all, tv_time_owen_all;
    private RecyclerView recyclerview_owen_owenpic, recyclerview_head_priase_owen_detail, recyclerView_owen_comments;
    private ImageView btn_like_owen_all;
    private TextView btn_comment_owen_all, tv_deltete_owen_all;
    private TimeLineContentTextView tv_content_owen_all;
    //九宫格图片适配器
    OwenOwenPicAdapter adapterPic;
    //点赞的人
    TextView textView_who_priase_owen;
    private OwenOwenCommentsAdapter adapterCommments;
    private MyPopForInput pop_feedback, popForInput;
    //标题
    TitleView title;
    //回传数据
    private OwenOwenDetailsBean owenOwenDetailsBean;

    @Override
    protected void onInitData() {
        id = getIntent().getIntExtra("id", 0);
        getData();
    }

    @Override
    protected void onInitView() {
        linearlayout_own_wholike_all = (LinearLayout) findViewById(R.id.linearlayout_own_wholike_all);
        ll_comment = (RelativeLayout) findViewById(R.id.ll_comment);
        tv_content_owen_all = (TimeLineContentTextView) findViewById(R.id.tv_content_owen_all);
        relativeLayout_title_owen_details = (RelativeLayout) findViewById(R.id.relativeLayout_title_owen_details);
        circular_head_owen_all = (CircleImageView) findViewById(R.id.circular_head_owen_all);
        textView_nickname_owen_all = (TextView) findViewById(R.id.textView_nickname_owen_all);
        tv_time_owen_all = (TextView) findViewById(R.id.tv_time_owen_all);
        recyclerview_owen_owenpic = (RecyclerView) findViewById(R.id.recyclerview_owen_owenpic);
//        recyclerview_head_priase_owen_detail = (RecyclerView) findViewById(R.id.recyclerview_head_priase_owen_detail);
        textView_who_priase_owen = (TextView) findViewById(R.id.textView_who_priase_owen);
        recyclerView_owen_comments = (RecyclerView) findViewById(R.id.recyclerView_owen_comments);
        btn_like_owen_all = (ImageView) findViewById(R.id.btn_like_owen_all);
        btn_comment_owen_all = (TextView) findViewById(R.id.btn_comment_owen_all);
        tv_deltete_owen_all = (TextView) findViewById(R.id.tv_deltete_owen_all);
        initTile();
    }

    private void initTile() {
        title = new TitleView(this);
        title.setTitle("业主圈");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setRightImage(R.mipmap.delete2x);
        title.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(id + "", "确认删除");
            }
        });
        relativeLayout_title_owen_details.addView(title.getView());
    }


    private void startDelete() {
        OkHttpUtils.post().url(ConstantUrl.deleteblogUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("id", id + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                BaseBean baseBean = GetGson.parseBaseBean(s);
                if (baseBean.getC() == 1) {
                    finish();
                    Preference.put(ConstantString.IS_FROM_DELETE, "YES");
                    Intent intent = new Intent(IntentDataKeyConstant.NOTIFY_OWNERCIRCLE_RELEASESUCCESS_ACTION);
                    sendBroadcast(intent);
                }
                ToastUtil.showToast(mContext, baseBean.getM());
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_owen_details;
    }

    public void getData() {
        OkHttpUtils.post().url(ConstantUrl.OwenDetailUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("id", id + "").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    owenOwenDetailsBean = GetGson.init().fromJson(s, OwenOwenDetailsBean.class);
                    owenOwenDetailsBean.getO().getImgs();
                    //设置头像等
                    setHeadNameContent(owenOwenDetailsBean);
                    //装填图片
                    setPic(owenOwenDetailsBean.getO().getImgs());
                    //点赞的人头
                    setHeadPriase(owenOwenDetailsBean.getO().getZan_name());
                    //加载评论列表
                    setComments(owenOwenDetailsBean.getE());
                } catch (Exception e) {
                    try {
                        ToastUtil.showToast(MyOwenDetailsActivity.this, GetGson.parseBaseBean(s).getM());
                    } catch (Exception e2) {
                    }
                }
            }
        });
    }


    public void setPic(final List<String> pic) {
        adapterPic = new OwenOwenPicAdapter();
        recyclerview_owen_owenpic.setAdapter(adapterPic);
        if (pic.size() != 0) {
            List<OwenOwenPicBean> list = new ArrayList<>();
            int type = 1;//是哪种图片布局，一张图平摊，四张图是2x2，其他的都是九宫格
            if (pic.size() == 4) {
                type = 2;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 2));
            } else if (pic.size() == 1) {
                type = 1;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 1));
            } else {
                type = 3;
                recyclerview_owen_owenpic.setLayoutManager(new GridLayoutManager(mContext, 3));
            }
            for (int i = 0; i < pic.size(); i++) {
                OwenOwenPicBean bean = new OwenOwenPicBean();
                bean.setImagestring(pic.get(i));
                bean.setType(type);
                list.add(bean);
            }
            adapterPic.refreshData(list);
            adapterPic.setOnItemClickListener(new OnRecyclerItemListener<OwenOwenPicBean>() {
                @Override
                public void onItemClick(View v, OwenOwenPicBean data) {
                    ArrayList<String> datas = new ArrayList<String>();
                    for (int i = 0; i < pic.size(); i++) {
                        datas.add(ConstantUrl.hostImageurl + owenOwenDetailsBean.getO().getTb_imgs().get(i));
                    }
                    PhotoPreview.builder().setShowDeleteButton(false).setPhotos(datas).setCurrentItem(recyclerview_owen_owenpic.getChildLayoutPosition(v)).start(
                            MyOwenDetailsActivity.this);
                }
            });
        }
    }

    public void setHeadNameContent(final OwenOwenDetailsBean headNameContent) {
        title.setRightTextVisible(headNameContent.getO().getDel() == 1 ? View.VISIBLE : View.GONE);
        Glide.with(this).load(ConstantUrl.hostImageurl + headNameContent.getO().getUids().getHead()).into(circular_head_owen_all);
        textView_nickname_owen_all.setText(headNameContent.getO().getUids().getNickname());
        tv_time_owen_all.setText(headNameContent.getO().getAddtime());
        tv_content_owen_all.setText(headNameContent.getO().getContent());
        tv_deltete_owen_all.setVisibility(View.GONE);
        tv_deltete_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(id + "", "确认删除");
            }
        });
        btn_like_owen_all.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new MyRunnalbe(), 500);
        btn_like_owen_all.setImageResource(headNameContent.getO().getZan() == 3 ? R.mipmap.icon_zan_selected : R.mipmap.icon_not_praise);
        btn_like_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_like_owen_all.setEnabled(false);

                String url;
                if (headNameContent.getO().getZan() == 1) {
                    url = ConstantUrl.zanOwenUrl;
                } else {
                    url = ConstantUrl.cancleZanOwenUrl;
                }
                OkHttpUtils.post().url(url)
                        .addParams("uid", Preference.get(ConstantString.USERID, ""))
                        .addParams("id", headNameContent.getO().getId() + "").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        BaseBean baseBean = GetGson.parseBaseBean(s);
                        ToastUtil.showToast(mContext, baseBean.getM());
                        getData();
                    }
                });
            }
        });
        btn_comment_owen_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popForInput = new MyPopForInput(mContext);
                popForInput.setSendListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popForInput.getText().equals("")) {
                            popForInput.dismissWindow();
                            return;
                        }
                        //发送消息
                        sendCommnit(popForInput.getText().toString());
                        popForInput.dismissWindow();
                    }
                });
            }
        });
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

    private void startdelete(String pid) {
        OkHttpUtils.post().url(ConstantUrl.deleteCommentsUrl)
                .addParams("id", pid).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
            }

            @Override
            public void onResponse(String s, int i) {
                BaseBean baseBean = GetGson.parseBaseBean(s);
                ToastUtil.showToast(mContext, baseBean.getM());
                getData();
            }
        });
    }

    public void setHeadPriase(List<OwenOwenDetailsBean.OBean.ZanNameBean> headPriase) {
        if (headPriase.size() == 0) {
            linearlayout_own_wholike_all.setVisibility(View.GONE);
            return;
        } else {
            linearlayout_own_wholike_all.setVisibility(View.VISIBLE);
        }
        StringBuffer buffer = new StringBuffer("");
        for (int i = 0; i < headPriase.size(); i++) {
            if (i == headPriase.size() - 1) {
                buffer.append(headPriase.get(i).getZan_name());
            } else {
                buffer.append(headPriase.get(i).getZan_name() + ",");
            }
        }
        textView_who_priase_owen.setText(buffer.toString());
    }

    public void setComments(OwenOwenDetailsBean.EBean comments) {
        //加载评论列表
        recyclerView_owen_comments.setLayoutManager(new LinearLayoutManager(mContext));
        adapterCommments = new OwenOwenCommentsAdapter();
        recyclerView_owen_comments.setAdapter(adapterCommments);
        List<Object> listComments = new ArrayList<>();
//        if (comments.getComment() != null && comments.getComment().size() != 0) {
//            listComments.addAll(comments.getComment());
//        }
//        if (comments.getPpuid() != null && comments.getPpuid().size() != 0) {
//            for (int i = 0; i < comments.getPpuid().size(); i++) {
//                if (comments.getPpuid().size() != 0) {
//                    listComments.addAll(comments.getPpuid().get(i));
//                }
//            }
//        }
        //修改为一个评论对应多个回复的模式
        if (comments.getComment() != null && comments.getComment().size() != 0) {
            for (int i = 0; i < comments.getComment().size(); i++) {
                listComments.add(comments.getComment().get(i));
                if (comments.getPpuid().get(i) != null && comments.getPpuid().get(i).size() != 0) {
                    listComments.addAll(comments.getPpuid().get(i));
                }
            }
        }
        adapterCommments.refreshData(listComments);
        if (adapterCommments.getDatas().size() == 0) {
            ll_comment.setVisibility(View.GONE);
        } else {
            ll_comment.setVisibility(View.VISIBLE);
        }
        adapterCommments.setOnItemClickListener(new OnRecyclerItemListener<Object>() {
            @Override
            public void onItemClick(View v, Object data) {
                if (data instanceof OwenOwenDetailsBean.EBean.CommentBean) {
                    OwenOwenDetailsBean.EBean.CommentBean bean = (OwenOwenDetailsBean.EBean.CommentBean) data;
                    if ((bean.getUid().getId() + "").equals(Preference.get(ConstantString.USERID, ""))) {
                        //弹出选项弹窗
//                        ToastUtil.showToast(mContext, "弹出删除选项的弹窗");
                        setDialog(bean.getId() + "", "是否删除该评论");
                    } else {
                        //发送回复
                        sendFeedCommnit(bean.getId(), bean.getUid().getId());
//                        ToastUtil.showToast(mContext, "弹出回复选项的弹窗");
                    }
                } else {
                    OwenOwenDetailsBean.EBean.PpuidBean bean = (OwenOwenDetailsBean.EBean.PpuidBean) data;
                    if ((bean.getUid().getId() + "").equals(Preference.get(ConstantString.USERID, ""))) {
                        //弹出选项弹窗
                        setDialog(bean.getId() + "", "是否删除该评论");
//                        ToastUtil.showToast(mContext, "弹出删除选项的弹窗");
                    } else {
                        //发送回复
                        sendFeedCommnit(bean.getAid(), bean.getUid().getId());
//                        ToastUtil.showToast(mContext, "弹出回复选项的弹窗");
                    }
                }

            }
        });
    }

    private void sendFeedCommnit(final int pid, final int id) {
        pop_feedback = new MyPopForInput(this);
        pop_feedback.setSendListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop_feedback.getText().equals("")) {
                    pop_feedback.dismissWindow();
                    return;
                }
                OkHttpUtils.post().url(ConstantUrl.leaveBackUrl)
                        .addParams("pid", pid + "")
                        .addParams("uid", Preference.get(ConstantString.USERID, ""))
                        .addParams("ob_uid", id + "")
                        .addParams("content", pop_feedback.getText().toString()).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        pop_feedback.dismissWindow();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            BaseBean baseBean = GetGson.parseBaseBean(s);
                            ToastUtil.showToast(MyOwenDetailsActivity.this, baseBean.getM());
                            if (baseBean.getC() == 1) {
                                getData();
                            }
                        } catch (Exception e) {
                        }
                        pop_feedback.dismissWindow();
                    }
                });
            }
        });
    }

    private void sendCommnit(String text) {
        OkHttpUtils.post().url(ConstantUrl.leaveUrl)
                .addParams("pid", id + "")
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("ob_uid", Preference.get(ConstantString.USERID, "") + "")
                .addParams("content", text).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                    ToastUtil.showToast(MyOwenDetailsActivity.this, baseBean.getM());
                    if (baseBean.getC() == 1) {
                        getData();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    class MyRunnalbe implements Runnable {
        @Override
        public void run() {
            btn_like_owen_all.setEnabled(true);
        }
    }
}
