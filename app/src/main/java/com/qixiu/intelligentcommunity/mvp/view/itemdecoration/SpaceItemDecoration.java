package com.qixiu.intelligentcommunity.mvp.view.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 间距的设置
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration
{

    private int space;

    public SpaceItemDecoration(int space)
    {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state)
    {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager)
        {
        }
        int childLayoutPosition = parent.getChildLayoutPosition(view);

        if (childLayoutPosition == 0)
        {
            outRect.top = 0;
            outRect.bottom = 0;
            outRect.left = 0;
            outRect.right = 0;
        } else
        {
            outRect.top = space / 2;
            outRect.left = space / 2;
            outRect.right = space / 2;
            outRect.bottom = space / 2;
        }
    }
}