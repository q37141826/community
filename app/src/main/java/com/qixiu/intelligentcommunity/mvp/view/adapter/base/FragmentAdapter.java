package com.qixiu.intelligentcommunity.mvp.view.adapter.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<FragmentInterface> fragmentInterfaces;
    private String[] titles;

    public void setPageTitle(String[] titles) {
        this.titles = titles;
    }

    int index;

    public FragmentAdapter(FragmentManager fm, List<FragmentInterface> fragmentInterfaces) {
        super(fm);
        this.fragmentInterfaces = fragmentInterfaces;
    }

    public void setFragmentPosition(int position) {
        this.index = position;
    }

    @Override
    public Fragment getItem(int position) {

        return (Fragment) fragmentInterfaces.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles == null ? "" : titles[position];
    }

    @Override
    public int getCount() {

        return fragmentInterfaces == null ? 0 : fragmentInterfaces.size();
    }
}
