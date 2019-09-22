package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.adapte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.Constant;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.listener.ItemClickListener;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.OwnerCircleFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.CommentConfig;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCircleBean;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCircleListImageView;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;
import com.qixiu.intelligentcommunity.widget.CircleImageView;
import com.qixiu.intelligentcommunity.widget.ExpandableTextView;
import com.qixiu.intelligentcommunity.widget.TimeLineContentTextView;
import com.qixiu.intelligentcommunity.widget.TimeLineImageView;
import com.qixiu.intelligentcommunity.widget.animation.BaseAnimatorSet;
import com.qixiu.intelligentcommunity.widget.animation.BounceEnter.BounceTopEnter;
import com.qixiu.intelligentcommunity.widget.animation.SlideExit.SlideBottomExit;
import com.qixiu.intelligentcommunity.widget.recyclerview.RecyclerHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Description：业主圈Adapter
 * Author：XieXianyong
 * Date： 2017/6/22 17:51
 */
public class OwnerCircleAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private Context context;
    private ItemClickListener itemClickListener;
    List<OwnerCircleBean.OOwnerCircleBean.OwnerCircleEntity> mOwnerCirclelist;
    private BaseAnimatorSet mBasIn;
    private BaseAnimatorSet mBasOut;
    private List<String> deleteCommentSources;
    private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();
    private List<OwnerCircleListImageView> ownerImage = new ArrayList<>();


    public OwnerCircleAdapter(Context context, List<OwnerCircleBean.OOwnerCircleBean.OwnerCircleEntity> mOwnerCirclelist) {
        this.context = context;
        this.mOwnerCirclelist = mOwnerCirclelist;
        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();
        deleteCommentSources = asList(context.getResources().getStringArray(R.array.footprint_delete_dialog_item));
    }

    public void setListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owner_circle_whole_list_item, null);
        return new RecyclerHolder(view, 24);
    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        TextView mTvName = holder.getView(R.id.tv_name);
        CircleImageView mIvAvatar = holder.getView(R.id.iv_avatar);
        TextView mTvTime = holder.getView(R.id.tv_time);
        TextView mTvDeltete = holder.getView(R.id.tv_deltete);
        LinearLayout footprintContentLL = holder.getView(R.id.ll_memory_content);
        //我转发时的文字
        TimeLineContentTextView reportTV = holder.getView(R.id.tv_report);
        //转发的正文
        final ExpandableTextView contentTV = holder.getView(R.id.etv_weibo_content);
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemClickListener(holder, position);
                }
            });
        }
        final OwnerCircleBean.OOwnerCircleBean.OwnerCircleEntity ownerBean = mOwnerCirclelist.get(position);
        ownerImage.clear();
        List<String> imgs = Arrays.asList(ownerBean.getImgs());
        for (int i = 0; i < imgs.size(); i++) {
            ownerImage.add(new OwnerCircleListImageView(i, ConstantUrl.IMAGEURL + imgs.get(i), ConstantUrl.IMAGEURL + imgs.get(i)));
        }
        OwnerCircleBean.OOwnerCircleBean.PublisherEntity userInfo = ownerBean.getUids();
        mTvName.setText(userInfo.getTrue_name());
        Glide.with(context).load(ConstantUrl.IMAGEURL + userInfo.getHead()).placeholder(R.mipmap.ic_launcher).into(mIvAvatar);
        mTvTime.setText(ownerBean.getAddtime());
        switch (ownerBean.getDel()) {
            case Constant.DELETE://有删除
                mTvDeltete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(context).setTitle("提示").setMessage("确定要删除该动态吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                OwnerCircleFragment.instance.deleteFootprint(position, ownerBean.getId());
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        }).show();
                    }
                });

                break;
            case Constant.NOT_DELETE://没有删除
                mTvDeltete.setVisibility(View.GONE);
                break;
        }
        TimeLineImageView picIV = holder.getView(R.id.tl_iv);

        footprintContentLL.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        footprintContentLL.setPadding(AndroidUtils.dip2px(context, 12), 0, AndroidUtils.dip2px(context, 12), 0);
        reportTV.setVisibility(View.GONE);
//        contentTV.setText(footprintBean.getTitle(), mConvertTextCollapsedStatus, position);
        fillPic(ownerImage, picIV);
        TextView commentBtn = holder.getView(R.id.btn_comment);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentConfig config = new CommentConfig();
                config.circlePosition = position;
                config.commentType = CommentConfig.Type.PUBLIC;
                OwnerCircleFragment.instance.showCommentDialog(v, config);
            }
        });

    }

    //填充九宫格图片
    private void fillPic(List<OwnerCircleListImageView> picsList, TimeLineImageView view) {
        if (picsList != null && picsList.size() > 0) {
            view.setVisibility(View.VISIBLE);
            view.setList(picsList);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mOwnerCirclelist.size();
    }
}
