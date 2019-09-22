package com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.mine.myprofile.MyPublishFragment;
import com.qixiu.intelligentcommunity.my_interface.FragmentInterface;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;

import java.util.ArrayList;
import java.util.List;

public class MyPublishActivity extends BaseActivity {
    private RelativeLayout relativeLayout_title_myPublish;
    private TabLayout tablelayout_myPublish;
    private ViewPager viewPager_myPublish;
    String titles[] = {"发布的房产", "发布的二手商品"};
    List<FragmentInterface> fragmentInterfaces = null;

    @Override
    protected void onInitData() {
        int position = getIntent().getExtras().getInt("position");
        if (position == 0) {
            viewPager_myPublish.setCurrentItem(0);
        } else {
            viewPager_myPublish.setCurrentItem(1);
        }
        if (fragmentInterfaces == null) {
            fragmentInterfaces = new ArrayList<>();
        }

        for (int i = 0; i < titles.length; i++) {
            MyPublishFragment fragment = new MyPublishFragment();
            fragmentInterfaces.add(fragment);

        }

//        PublishFragmentAdapter publishadapter = new PublishFragmentAdapter(this.getSupportFragmentManager(), fragmentInterfaces);
//        orderFragmentAdapter.setPageTitle(titles);
//        viewPager_myPublish.setAdapter(publishadapter);
    }

    @Override
    protected void onInitView() {
        relativeLayout_title_myPublish = (RelativeLayout) findViewById(R.id.relativeLayout_title_myPublish);
        tablelayout_myPublish = (TabLayout) findViewById(R.id.tablelayout_myPublish);
        viewPager_myPublish = (ViewPager) findViewById(R.id.viewPager_myPublish);
        initTitle();
        tablelayout_myPublish.setupWithViewPager(viewPager_myPublish);
    }

    private void initTitle() {
        TitleView title = new TitleView(this);
        title.setTitle("我的发布");
        relativeLayout_title_myPublish.addView(title.getView());
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_publish;
    }
}
