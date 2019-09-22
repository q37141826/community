package com.qixiu.intelligentcommunity.mvp.beans.store;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class ShopCarBean extends BaseBean {
    public static class ShopCarInfo {


        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private boolean selected;
    }

}
