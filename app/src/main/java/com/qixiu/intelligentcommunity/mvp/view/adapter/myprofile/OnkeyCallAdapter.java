package com.qixiu.intelligentcommunity.mvp.view.adapter.myprofile;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.mine.OnekeyCallBean;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.mine.OneKeyCallHolder;

/**
 * Created by HuiHeZe on 2017/6/21.
 */

public class OnkeyCallAdapter extends RecyclerBaseAdapter<OnekeyCallBean.OBean.ListBean,OneKeyCallHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.item_onekeycall;
    }

    @Override
    public OneKeyCallHolder createViewHolder(View itemView, Context context, int viewType) {
        return new OneKeyCallHolder(itemView,context,this);
    }
}
