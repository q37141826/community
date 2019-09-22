package com.qixiu.intelligentcommunity.listener.rv_adapter;

import android.view.View;

/**
 * Created by 高玉恒 on 2017/4/10 0010.
 */

public interface OnRecyclerItemListener<T> {

    public void onItemClick(View v, T data);


}
