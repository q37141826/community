package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.OwnerCicleMyBean;
import com.qixiu.intelligentcommunity.widget.recyclerview.RecyclerHolder;

import java.util.List;

/**
 * Description：我的
 * Author：XieXianyong
 * Date： 2017/6/23 14:52
 */
public class OwnerCircleMyAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private Context context;
    List<OwnerCicleMyBean.OwnerCicleMyDataBean> mOwnerCircleMylist;

    public OwnerCircleMyAdapter(Context context, List<OwnerCicleMyBean.OwnerCicleMyDataBean> mOwnerCircleMylist) {
        this.context = context;
        this.mOwnerCircleMylist = mOwnerCircleMylist;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owner_circle_my_list_item, null);
        return new RecyclerHolder(view, 24);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        TextView mTvContent = holder.getView(R.id.tv_content);
        ImageView mIvDynamicPhoto = holder.getView(R.id.iv_dynamic_photo);
        mTvContent.setText(mOwnerCircleMylist.get(position).getContent());
//        Glide.with(context).load(ConstantUrl.IMAGEURL + mOwnerCircleMylist.get(position).getImgs()).placeholder(R.mipmap.ic_launcher).into(mIvDynamicPhoto);

    }

    @Override
    public int getItemCount() {
        return mOwnerCircleMylist.size();
    }
}
