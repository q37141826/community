/*
 * Copyright (c) 2015.
 * 湖南球谱科技有限公司版权所有
 * Hunan Qiupu Technology Co., Ltd. all rights reserved.
 */

package com.qixiu.intelligentcommunity.widget.imagepager;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private View view;
    private List<String> mUrls;
    private Context mContext;

    public ViewPagerAdapter(List<String> urls, Context context) {
        mUrls = urls;
        mContext = context;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        view = (View) object;

        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public View getPrimaryItem() {
        return view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final PhotoView view = new PhotoView(mContext);
        view.enable();
        Glide.with(view.getContext())
                .load(mUrls.get(position))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .thumbnail(0.1f)
                .into(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ((Activity) mContext).finish();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {
                return false;
            }
        });
        container.addView(view);
        view.setId(position);
        return view;
    }

};