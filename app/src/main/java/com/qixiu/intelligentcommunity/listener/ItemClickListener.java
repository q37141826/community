/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */

package com.qixiu.intelligentcommunity.listener;


import com.qixiu.intelligentcommunity.widget.recyclerview.RecyclerHolder;

/**
 * Description：
 * Author：XieXianyong
 * Date： 2016-10-13 15:04
 */

public interface ItemClickListener {
    void OnItemClickListener(RecyclerHolder view, int position);

    void OnItemLongClickListener(RecyclerHolder view, int position);
}
