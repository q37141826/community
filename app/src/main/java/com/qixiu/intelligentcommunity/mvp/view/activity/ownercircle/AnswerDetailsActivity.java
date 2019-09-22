package com.qixiu.intelligentcommunity.mvp.view.activity.ownercircle;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer.OwenAnswerDetailsBean;
import com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen.OwenOwenPicBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.OwenOwenPicAdapter;
import com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle.owen_answer.AnswerDetailsCommentsAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_answer.AnswerDetailsCommentsHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForInput;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;

public class AnswerDetailsActivity extends BaseActivity implements View.OnClickListener,AnswerDetailsCommentsHolder.RefreshListenner {
    //详情的id
    private int id = 0, obj_id = 0;
    private RelativeLayout relativeLayout_titile_answer_details;
    private CircleImageView circuler_head_answerdetails;
    private TextView textView_nickname_answer_details, textView_time_answer_details,
            textView_title_answer_details, textView_content_answer_details,
            textView_address_answer_details, textView_connector_answer_details, textView_phone_answer_details;
    private RecyclerView recyclerview_pic_answer_detail, recyclerView_commentss_answerdetails;
    private OwenOwenPicAdapter adapterPic;
    private ImageView imageView_comments_answerdetails, imageView_delete_answer_details_delete;
    private AnswerDetailsCommentsAdapter adapterComments;
    private MyPopForInput myPopForInput;
    //回传数据
    OwenAnswerDetailsBean owenAnswerDetailsBean;
    @Override
    protected void onInitData() {
        id = getIntent().getIntExtra("id", 0);
        adapterComments = new AnswerDetailsCommentsAdapter();
        recyclerView_commentss_answerdetails.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_commentss_answerdetails.setAdapter(adapterComments);
        getData();

    }

    @Override
    protected void onInitView() {
        recyclerView_commentss_answerdetails = (RecyclerView) findViewById(R.id.recyclerView_commentss_answerdetails);
        relativeLayout_titile_answer_details = (RelativeLayout) findViewById(R.id.relativeLayout_titile_answer_details);
        circuler_head_answerdetails = (CircleImageView) findViewById(R.id.circuler_head_answerdetails);
        textView_nickname_answer_details = (TextView) findViewById(R.id.textView_nickname_answer_details);
        textView_time_answer_details = (TextView) findViewById(R.id.textView_time_answer_details);
        textView_title_answer_details = (TextView) findViewById(R.id.textView_title_answer_details);
        textView_content_answer_details = (TextView) findViewById(R.id.textView_content_answer_details);
        textView_address_answer_details = (TextView) findViewById(R.id.textView_address_answer_details);
        textView_connector_answer_details = (TextView) findViewById(R.id.textView_connector_answer_details);
        textView_phone_answer_details = (TextView) findViewById(R.id.textView_phone_answer_details);
        recyclerview_pic_answer_detail = (RecyclerView) findViewById(R.id.recyclerview_pic_answer_detail);
        imageView_comments_answerdetails = (ImageView) findViewById(R.id.imageView_comments_answerdetails);
        imageView_delete_answer_details_delete = (ImageView) findViewById(R.id.imageView_delete_answer_details_delete);
        initTitle();

    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("问答详情");
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        relativeLayout_titile_answer_details.addView(title.getView());
        initClick();
    }

    private void initClick() {
        imageView_comments_answerdetails.setOnClickListener(this);
        imageView_delete_answer_details_delete.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_answer_details;
    }

    public void getData() {
        OkHttpUtils.post().url(ConstantUrl.answerDetailsUrl)
                .addParams("id", id + "")
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    owenAnswerDetailsBean  = GetGson.init().fromJson(s, OwenAnswerDetailsBean.class);
                    if (owenAnswerDetailsBean.getO().getDel() == 1) {
                        imageView_delete_answer_details_delete.setVisibility(View.VISIBLE);
                    }
                    obj_id = owenAnswerDetailsBean.getO().getUid();
                    //设置头像正文等
                    setHeadNick(owenAnswerDetailsBean);
                    //加载正文中的图片
                    setPic((List<String>) owenAnswerDetailsBean.getO().getImgs());
                    //加载评论内容
                    adapterComments.refreshData(owenAnswerDetailsBean.getE());
                } catch (Exception e) {
                    try {
                        ToastUtil.showToast(AnswerDetailsActivity.this, GetGson.parseBaseBean(s).getM());
                    } catch (Exception e1) {
                    }
                }
            }
        });
    }


    public void setHeadNick(OwenAnswerDetailsBean headNick) {
        Glide.with(this).load(ConstantUrl.hostImageurl + headNick.getO().getUids().getHead()).into(circuler_head_answerdetails);
        textView_nickname_answer_details.setText(headNick.getO().getUids().getNickname());
        textView_title_answer_details.setText(headNick.getO().getTitle());
        textView_time_answer_details.setText(headNick.getO().getAddtime());
        textView_content_answer_details.setText(headNick.getO().getContent());

    }

    public void setPic(final List<String> pic) {
        adapterPic = new OwenOwenPicAdapter();
        recyclerview_pic_answer_detail.setAdapter(adapterPic);
        if (pic.size() != 0) {
            List<OwenOwenPicBean> list = new ArrayList<>();
            int type = 1;//是哪种图片布局，一张图平摊，四张图是2x2，其他的都是九宫格
            if (pic.size() == 4) {
                type = 2;
                recyclerview_pic_answer_detail.setLayoutManager(new GridLayoutManager(mContext, 2));
            } else if (pic.size() == 1) {
                type = 1;
                recyclerview_pic_answer_detail.setLayoutManager(new GridLayoutManager(mContext, 1));
            } else {
                type = 3;
                recyclerview_pic_answer_detail.setLayoutManager(new GridLayoutManager(mContext, 3));
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
                        datas.add(ConstantUrl.hostImageurl + owenAnswerDetailsBean.getO().getTb_imgs().get(i).toString());
                    }
                    PhotoPreview.builder().setShowDeleteButton(false).setPhotos(datas).setCurrentItem(recyclerview_pic_answer_detail.getChildLayoutPosition(v)).start(
                            AnswerDetailsActivity.this);
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_comments_answerdetails:
                myPopForInput = new MyPopForInput(this);
                myPopForInput.setSendListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(myPopForInput.getText().toString().equals("")){
                            myPopForInput.dismissWindow();
                            return;
                        }
                        sendCommmit(myPopForInput.getText());
                    }
                });
                break;

            case R.id.imageView_delete_answer_details_delete:
                setDialog("", "确认删除");
                break;
        }
    }

    private void sendCommmit(String text) {
        OkHttpUtils.post().url(ConstantUrl.answerCommentsUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("pid", id + "")
                .addParams("ob_uid", obj_id + "")
                .addParams("content", text).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                myPopForInput.dismissWindow();
            }

            @Override
            public void onResponse(String s, int i) {
                myPopForInput.dismissWindow();
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                    if (baseBean.getC() == 1) {
                        getData();
                    }
                    ToastUtil.showToast(AnswerDetailsActivity.this, baseBean.getM());
                } catch (Exception e) {
                }
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
//                    startdelete(pid);
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
        OkHttpUtils.post().url(ConstantUrl.deleteAnswerDetailUrl)
                .addParams("id", id + "")
                .addParams("uid", Preference.get(ConstantString.USERID, "")).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    BaseBean baseBean = GetGson.parseBaseBean(s);
                   if( baseBean.getC()==1){
                       Intent intent =new Intent(ConstantString.broadCastOwnAnser);
                       intent.putExtra("id",id+"");
                       sendBroadcast(intent);
                       finish();
                   }
                    ToastUtil.showToast(AnswerDetailsActivity.this,baseBean.getM());
                } catch (Exception e) {
                }
            }
        });


    }

    @Override
    public void refresh() {
        getData();
    }
}
