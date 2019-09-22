package com.qixiu.intelligentcommunity.mvp.view.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.qixiu.intelligentcommunity.R;

/**
 * 自定义分割线
 * Created by Administrator on 2017/8/24 0024.
 */

public class DiyDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;
    private final Drawable drawableIndexOther;
    private final View headView;

    private Drawable drawableIndexOne;

    private int mOrientation;

    public DiyDividerItemDecoration(Context context, int orientation, View headView) {
        this.headView = headView;
        drawableIndexOne = context.getResources().getDrawable(R.drawable.shape_divider_one);
        drawableIndexOther = context.getResources().getDrawable(R.drawable.shape_divider_other);


        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {


        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            //针对于XRecyclerView头部分割线显示问题
            if (i == 0) {
                if (headView == child) {
                    final int bottom = top + drawableIndexOther.getIntrinsicHeight();
                    drawableIndexOther.setBounds(left, top, right, bottom);
                    drawableIndexOther.draw(c);
                } else {
                    final int bottom = top + drawableIndexOne.getIntrinsicHeight();
                    drawableIndexOne.setBounds(left, top, right, bottom);
                    drawableIndexOne.draw(c);
                }

            } else {
                final int bottom = top + drawableIndexOther.getIntrinsicHeight();
                drawableIndexOther.setBounds(left, top, right, bottom);
                drawableIndexOther.draw(c);
            }

        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            if (i == 0) {
                final int right = left + drawableIndexOne.getIntrinsicHeight();
                drawableIndexOne.setBounds(left, top, right, bottom);
                drawableIndexOne.draw(c);
            } else {
                final int right = left + drawableIndexOther.getIntrinsicHeight();
                drawableIndexOther.setBounds(left, top, right, bottom);
                drawableIndexOther.draw(c);
            }


        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
//            if (itemPosition == 0) {
//                outRect.set(0, 0, 0, drawableIndexOne.getIntrinsicHeight());
//            } else {
            outRect.set(0, 0, 0, drawableIndexOther.getIntrinsicHeight());
//            }

        } else {

//            if (itemPosition == 0) {
//                outRect.set(0, 0, drawableIndexOne.getIntrinsicWidth(), 0);
//            } else {
            outRect.set(0, 0, drawableIndexOther.getIntrinsicWidth(), 0);
//            }

        }
    }

}
