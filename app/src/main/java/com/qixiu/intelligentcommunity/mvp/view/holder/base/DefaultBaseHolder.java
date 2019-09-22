package com.qixiu.intelligentcommunity.mvp.view.holder.base;

import android.view.View;

/**
 * Created by Administrator on 2017/5/11 0011.
 *  该类可提供ListView,GridView等Holder（不包含RecyclerView）
 *
 */

public abstract class DefaultBaseHolder<D> {

    public DefaultBaseHolder(View contentView) {

    }

    public abstract void setData(D data);
}
