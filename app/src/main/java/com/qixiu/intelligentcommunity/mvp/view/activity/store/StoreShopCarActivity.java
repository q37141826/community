package com.qixiu.intelligentcommunity.mvp.view.activity.store;

import android.view.View;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.store.StoreShopCarFragment;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class StoreShopCarActivity extends NewTitleActivity implements View.OnClickListener {


    private OnDelectShopCarListner onDelectShopCarListener;

    @Override
    protected void onInitData() {

    }

    private void setOnDelectShopCarListener(OnDelectShopCarListner onDelectShopCarListener) {

        this.onDelectShopCarListener = onDelectShopCarListener;
    }

    public interface OnDelectShopCarListner {
        void onDelected();

    }


    @Override
    protected void onInitViewNew() {
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setTitle("购物车");
        StoreShopCarFragment storeShopCarFragment = new StoreShopCarFragment();
        setOnDelectShopCarListener(storeShopCarFragment);
        addFragment(R.id.fl_shopcar_fragment, storeShopCarFragment, storeShopCarFragment.getClass().getSimpleName());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shopcar;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativelayout_back_myProfile:
                finish();
                break;
//            case R.id.rl_shopcar_edit:
//                if (onDelectShopCarListener != null) {
//                    onDelectShopCarListener.onDelected();
//
//                }
//                //删除操作
//                break;

        }
    }
}
