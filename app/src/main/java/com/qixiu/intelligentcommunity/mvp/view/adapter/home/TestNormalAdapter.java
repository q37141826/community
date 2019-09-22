package com.qixiu.intelligentcommunity.mvp.view.adapter.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.utlis.GlideLoader;
import com.yancy.imageselector.ImageLoader;

import java.util.List;

/*
*
* 轮播页的适配器
*
* */
public class TestNormalAdapter extends StaticPagerAdapter {
    private List<String> images;
    private Context context;

    public TestNormalAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setBackgroundResource(R.drawable.shape_shadow_backgroud);
//        new GlideLoader().displayImage(context, images.get(position), view);
        Glide.with(context).load(images.get(position)).dontAnimate().placeholder(R.mipmap.loading).into(view);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getCount() {
        return images.size();
    }
}