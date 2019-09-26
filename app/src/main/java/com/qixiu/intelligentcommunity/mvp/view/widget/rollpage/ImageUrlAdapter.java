package com.qixiu.intelligentcommunity.mvp.view.widget.rollpage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowContextUtil;

import java.util.ArrayList;
import java.util.List;

public class ImageUrlAdapter extends LoopPagerAdapter {
    List<String> datas = new ArrayList<>();

    public ImageUrlAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    public void refreshData(List<String> datas) {

        if (this.datas.size() > 0) {
            this.datas.clear();
        }
        this.datas.addAll(datas == null ? new ArrayList<String>() : datas);
        notifyDataSetChanged();
    }

    @Override
    public View getView(ViewGroup container, int position) {
//        View view = View.inflate(container.getContext(), R.layout.layout_banner_image, null);
//        ImageView imageView = view.findViewById(R.id.imageViewBanner);

        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ArshowContextUtil.getWidthPixels() - 16, (ArshowContextUtil.getWidthPixels() - 16) * 3 / 4));
        Glide.with(container.getContext()).load(datas.get(position).startsWith("http")?datas.get(position): ConstantUrl.hosturl +datas.get(position)).into(imageView);
        return imageView;
    }

    @Override
    public int getRealCount() {

        return datas == null ? 0 : datas.size();
    }
}