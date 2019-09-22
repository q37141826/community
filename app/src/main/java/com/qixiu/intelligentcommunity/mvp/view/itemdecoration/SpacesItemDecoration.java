package com.qixiu.intelligentcommunity.mvp.view.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/8/25.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration

{
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space * 2;
        outRect.bottom = space;
        outRect.top = space;


    }
}
