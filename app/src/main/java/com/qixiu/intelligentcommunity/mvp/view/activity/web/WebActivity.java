package com.qixiu.intelligentcommunity.mvp.view.activity.web;

import android.view.KeyEvent;

import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.my_interface.web.OnWebShowlistener;

/**
 * Created by gyh on 2017/6/26 0026.
 */

public abstract class WebActivity extends TitleActivity implements OnWebShowlistener {


    private OnWebPageBackListener onWebPageBackListener;
    private boolean isWebGoBack;

    public void setOnWebPageBackListener(OnWebPageBackListener onWebPageBackListener) {

        this.onWebPageBackListener = onWebPageBackListener;
    }

    protected  final  void setIsWebGoBack(boolean isWebGoBack) {
        this.isWebGoBack = isWebGoBack;
    }

    public interface OnWebPageBackListener  {

        void onWebPageBack(boolean isWebGoBack);
    }

    @Override
    protected void onBackClick() {
        if (onWebPageBackListener != null) {
            onWebPageBackListener.onWebPageBack(isWebGoBack);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onWebPageBackListener != null) {
                onWebPageBackListener.onWebPageBack(isWebGoBack);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
