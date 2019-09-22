package com.qixiu.intelligentcommunity.widget.recyclerview;/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */


import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Description：适配一切RecyclerView.ViewHolder
 * Author：xiexianyong
 * Date： 2016/8/14 16:13
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;

    public RecyclerHolder(View itemView) {
        super(itemView);
        //一般不会超过8个吧
        this.mViews = new SparseArray<View>(12);
    }

    public RecyclerHolder(View itemView, int count) {
        super(itemView);
        //一般不会超过8个吧
        this.mViews = new SparseArray<View>(count);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


}