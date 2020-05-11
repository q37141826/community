package com.qixiu.intelligentcommunity.mvp.view.activity.guidepage;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.mine.LoginActivity;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ShortCutHelper;

import me.leolin.shortcutbadger.ShortcutBadger;

public class StartPageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_page);
        onInitData();
    }


    protected void onInitData() {


//        try {
//            if (!Preference.getBoolean(ConstantString.IS_FIRST_LOGIN)) {
//            CommonUtils.startIntent(this, GuidePageActivity.class);
//            Preference.putBoolean(ConstantString.IS_FIRST_LOGIN, true);
//            StartPageActivity.this.finish();
//            return;
//            }
//        } catch (Exception e) {
//        }
        try {
            if (Preference.get(ConstantString.USERID, "").equals("")) {
                handeler.postDelayed(new MyRunnable(1), 100);
            } else {
                handeler.postDelayed(new MyRunnable(2), 100);
            }
        } catch (Exception e) {
        }
    }




    //跳转的延迟线程
    Handler handeler = new Handler();

    class MyRunnable implements Runnable {
        private int type;

        public MyRunnable(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            if (type == 1) {
                CommonUtils.startIntent(StartPageActivity.this, LoginActivity.class);
            } else {
                CommonUtils.startIntent(StartPageActivity.this, MainActivity.class);
            }
            StartPageActivity.this.finish();
        }
    }
}
