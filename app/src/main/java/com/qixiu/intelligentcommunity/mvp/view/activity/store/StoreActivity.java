package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.StoreFragment;

public class StoreActivity extends TitleActivity {
    private ViewGroup layoutForFragment;
    FragmentManager fragmentManager;


    @Override
    protected void onInitData() {
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction= fragmentManager.beginTransaction();
        transaction.add(layoutForFragment.getId(),new StoreFragment());
        transaction.commit();
        rl_title_right.setVisibility(View.GONE);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        setTitleText(R.string.main_store_txt);
        layoutForFragment=findViewById(R.id.layoutForFragment);
    }

    @Override
    protected void onOtherClick(View view) {

    }

    @Override
    protected void onTitleRightClick() {

    }

    @Override
    protected void onBackClick() {
        finish();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_strore;
    }
}
