package com.qixiu.intelligentcommunity.mvp.view.widget.rollpage;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import java.util.List;

public class ShufflingFigureAdapter extends PagerAdapter {

	private List<ImageView> ivs;

	@Override
	public int getCount() {
		return ivs == null ? 0 : ivs.size();
	}

	public ShufflingFigureAdapter(List<ImageView> ivs) {
		this.ivs = ivs;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(ivs.get(position));
		ImageView imageView = ivs.get(position);
		imageView.setScaleType(ScaleType.FIT_XY);
		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(ivs.get(position));
	}

}
