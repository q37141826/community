package com.qixiu.intelligentcommunity.mvp.view.activity.guidepage;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.home.VpAdapter;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;

import java.util.ArrayList;
import java.util.List;

public class GuidePageActivity extends AppCompatActivity {
    private ViewPager viewPager_guidepage;
    PagerAdapter adapter;
    List<View> images=new ArrayList<>();
    int currentPosition;
//    int imageresourses[]={R.mipmap.new_guide01,R.mipmap.guide02,R.mipmap.guide03};
    int imageresourses[]={R.mipmap.guide01,R.mipmap.guide02,R.mipmap.guide03};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page2);
        viewPager_guidepage = (ViewPager) findViewById(R.id.viewPager_guidepage);
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams param = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(param);
            imageView.setImageResource(imageresourses[i]);
            images.add(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentPosition==2){
                        if (Preference.get(ConstantString.USERID,"").equals("")) {
                            CommonUtils.startIntent(GuidePageActivity.this, LoginActivity.class);
                        }else {
                            CommonUtils.startIntent(GuidePageActivity.this, MainActivity.class);
                        }
                        GuidePageActivity.this.finish();
                    }
                }
            });
        }
        adapter=new VpAdapter(images);
        viewPager_guidepage.setAdapter(adapter);
        viewPager_guidepage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
