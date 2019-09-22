package com.qixiu.intelligentcommunity.mvp.beans.store.shopcar;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class ShopCarEditCountBean extends BaseBean<ShopCarEditCountBean.ShopCarEditCountInfo> {



    public static class ShopCarEditCountInfo {
        /**
         * goods_num : 2
         */

        private int goods_num;

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }
    }
}
