package com.qixiu.intelligentcommunity.mvp.view.adapter.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.interf.IAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.RecyclerBaseHolder;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 高玉恒 on 2017/4/10 0010.
 * 仅供RecyclerView使用的Adapter
 */

public abstract class RecyclerBaseAdapter<D, H extends RecyclerBaseHolder> extends RecyclerView.Adapter<H> implements IAdapter<H>, View.OnClickListener {
    private int width_grid;
    List<D> datas = new ArrayList<>();
    private OnRecyclerItemListener<D> mOnItemClickListener;
    private Bundle mBundle;
    OnHolderNotifyRefreshListener mOnHolderNotifyRefreshListener;

    public void setOnHolderNotifyRefreshListener(OnHolderNotifyRefreshListener onHolderNotifyRefreshListener) {
        mOnHolderNotifyRefreshListener = onHolderNotifyRefreshListener;
    }



    public interface OnHolderNotifyRefreshListener {
        void onHolderNotifyRefresh(Object data);
    }

    public void holderNotifyRefresh(Object data) {
        if (mOnHolderNotifyRefreshListener != null) {
            mOnHolderNotifyRefreshListener.onHolderNotifyRefresh(data);
        }
    }

    protected void setTileWith(View itemView, int rowCount, Context context, int differencevalue) {
        if (width_grid == 0) {
            width_grid = (int) ((AndroidUtils.getDeviceWidth(context)));
        }
        int width = width_grid / rowCount;
        int height = width;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.leftMargin = differencevalue * 2;
        layoutParams.rightMargin = differencevalue * 2;
        layoutParams.topMargin = differencevalue * 4;
        itemView.setLayoutParams(layoutParams);
    }

    @Override

    public H onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (getLayoutId() > 0) {
            //TODO: 发现使用View.inflater布局显示异常，因此推荐使用LayoutInflater
            itemView =
                    LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);

        } else {

            itemView = getLayoutView(parent.getContext());
            if (itemView == null) {
                throw new RuntimeException("您的View都没有，拿什么显示？");
            }
        }
        itemView.setOnClickListener(this);
        return createViewHolder(itemView, parent.getContext(), viewType);
    }


    public List<D> getDatas() {
        return datas;

    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setData(datas.get(position));
        holder.bindHolder(position);
    }

    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }


    public View getLayoutView(Context context) {
        return null;
    }

    public final void addDatas(List<D> datas) {
        int insertIndex = this.datas.size();
        if (datas != null && datas.size() > 0) {
            if (this.datas.addAll(datas)) {
                notifyDataSetChanged();
            }


        }

    }

    public void refreshData(List<D> datas) {

        if (this.datas.size() > 0) {
            this.datas.clear();
        }
        this.datas.addAll(datas == null ? new ArrayList<D>() : datas);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerItemListener<D> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;

    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (D) v.getTag());
        }
    }

    /**
     * 如果子类要使用Adapter来传递数据请重写该方法
     *
     * @return
     */
    public Bundle getArguments() {

        return mBundle;
    }

    public void setArguments(Bundle arguments) {

        this.mBundle = arguments;
    }
}
