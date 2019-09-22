package com.qixiu.intelligentcommunity.mvp.view.holder.store.goods;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.mvp.beans.store.goods.GoodsDetailBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.bigpicture.BigPictureActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.store.good.GoodsDetailCommentAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.SplitStringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GoodDetailHolder extends RecyclerBaseHolder<GoodsDetailBean.GoodsDetailInfos.GoodsCommentBean> implements AdapterView.OnItemClickListener {

    private final ImageView mCiv_usericon;
    private final TextView mTv_username;
    private final TextView mTv_comment_date;
    private final TextView mTv_spec_key_name;
    private final TextView mTv_content;
    private final GridView mGv_gooddetail_comment_picture;
    private List<String> mStrings;
    //private final TextView mTv_comment_total_count;

    public GoodDetailHolder(View itemView, Context context, RecyclerView.Adapter adapter) {
        super(itemView, context, adapter);
        mCiv_usericon = (ImageView) itemView.findViewById(R.id.civ_usericon);
        mTv_username = (TextView) itemView.findViewById(R.id.tv_username);
        mTv_comment_date = (TextView) itemView.findViewById(R.id.tv_comment_date);
        // mTv_comment_total_count = (TextView) itemView.findViewById(R.id.tv_comment_total_count);
        mTv_spec_key_name = (TextView) itemView.findViewById(R.id.tv_spec_key_name);
        mTv_content = (TextView) itemView.findViewById(R.id.tv_content);
        mGv_gooddetail_comment_picture = (GridView) itemView.findViewById(R.id.gv_gooddetail_comment_picture);

    }

    @Override
    public void bindHolder(int position) {

        showCommentPritures();

        Glide.with(BaseApplication.getContext()).load(ConstantUrl.hosturl + mData.getHead()).crossFade().into(mCiv_usericon);
        mTv_comment_date.setText(mData.getAdd_time());
        mTv_username.setText(mData.getNickname());
//        if (position == 0) {
//            mTv_comment_total_count.setVisibility(View.VISIBLE);
//            mTv_comment_total_count.setText(StringConstantsUrl.STRING_COMMENTCOUNT + ((DefaultAdapter) mAdapter).getDatas().size() + StringConstantsUrl.STRING_BACKBRACE);
//        } else {
//            mTv_comment_total_count.setVisibility(View.GONE);
//        }

        mTv_spec_key_name.setText(mData.getSpec_key_name());
        mTv_content.setText(mData.getContent());

    }

    private void showCommentPritures() {
        String imgs = mData.getImg();
        if (imgs != null && imgs.length() > 0) {
            mStrings = SplitStringUtils.startSplit(imgs, ";");
            GoodsDetailCommentAdapter goodsDetailCommentAdapter = new GoodsDetailCommentAdapter();
            goodsDetailCommentAdapter.refreshData(mStrings);
            mGv_gooddetail_comment_picture.setAdapter(goodsDetailCommentAdapter);

        }
        mGv_gooddetail_comment_picture.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mStrings != null && position < mStrings.size()) {
            Intent intent = new Intent(BaseApplication.getContext(), BigPictureActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(IntentDataKeyConstant.BIGPICTURE_KEY, ConstantUrl.hosturl + mStrings.get(position));
            BaseApplication.getContext().startActivity(intent);
        }
    }
}
