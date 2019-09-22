package com.qixiu.intelligentcommunity.mvp.view.itemdecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/5/19 0019.
 */

public class LinearSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public LinearSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;
        outRect.top = 0;


    }
}
