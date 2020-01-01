package com.qixiu.intelligentcommunity.mvp.view.itemdecoration;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;

import com.qixiu.intelligentcommunity.R;

public class SpaceItemsDecorationColor extends SpaceItemsDecoration {


    public SpaceItemsDecorationColor(int space) {
        super(space);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        c.drawColor(parent.getContext().getResources().getColor(R.color.diliver_505050), PorterDuff.Mode.ADD);
    }
}
