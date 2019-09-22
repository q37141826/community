package com.qixiu.intelligentcommunity.mvp.view.activity.store.classify;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.allclassify.StoreAllClassifyFragment;


/**
 * Created by Administrator on 2016/8/26.
 */
public class StoreAllClassifyActivity extends BaseActivity{


    @Override
    protected void onInitData() {

    }

    @Override
    protected void onInitView() {
        StoreAllClassifyFragment storeAllClassifyFragment = new StoreAllClassifyFragment();
        storeAllClassifyFragment.setArguments(getIntent().getExtras());
        addFragment(R.id.fl_allclassify, storeAllClassifyFragment, storeAllClassifyFragment.getClass().getSimpleName());
    }




    @Override
    protected int getLayoutResource() {
        return R.layout.activity_allclassify;
    }


}
