package com.qixiu.intelligentcommunity.mvp.view.adapter.owen_circle;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.owener_circle.owen_event.OwenEventHolder;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class OwenEventAdapter extends RecyclerBaseAdapter {

    private int width_grid;


    private void setTileWith(View itemView, Context context) {
        if (width_grid == 0) {
            width_grid = (int) ((AndroidUtils.getDeviceWidth(context)));
        }

        int height = width_grid / 2 - ArshowContextUtil.dp2px(4);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_owenevent_rv;
    }

    @Override
    public Object createViewHolder(View itemView, Context context, int viewType) {
        setTileWith(itemView, context);
        return new OwenEventHolder(itemView, context, this);
    }
}
