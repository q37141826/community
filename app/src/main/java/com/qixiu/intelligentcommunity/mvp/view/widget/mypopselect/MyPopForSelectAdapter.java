package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.content.Context;
import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.adapter.base.RecyclerBaseAdapter;


/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class MyPopForSelectAdapter extends RecyclerBaseAdapter<PopoItemBean,MyPopForSelectHolder> {
    MyPopForSelectHolder myPopForSelectHolder;
    @Override
    public int getLayoutId() {
        return R.layout.item_pop_selected;
    }

    @Override
    public MyPopForSelectHolder createViewHolder(View itemView, Context context, int viewType) {
        myPopForSelectHolder  = new MyPopForSelectHolder(itemView, context, this);
        return myPopForSelectHolder;
    }
}
