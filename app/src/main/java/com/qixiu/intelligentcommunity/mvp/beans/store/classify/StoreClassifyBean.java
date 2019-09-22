package com.qixiu.intelligentcommunity.mvp.beans.store.classify;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.impl.GoodsListImpl;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class StoreClassifyBean extends BaseBean<StoreClassifyBean.StoreClassifyInfo> {


    /**
     * o : {"goods":{"list":[{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"},{"goods_id":"43","goods_name":"荣耀畅玩平板no","shop_price":"1499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/43/goods_thumb_43_305_305.jpeg"}],"page":2}}
     */


    public static class StoreClassifyInfo {
        /**
         * goods : {"list":[{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"},{"goods_id":"43","goods_name":"荣耀畅玩平板no","shop_price":"1499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/43/goods_thumb_43_305_305.jpeg"}],"page":2}
         */

        private GoodsBean goods;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * list : [{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"},{"goods_id":"43","goods_name":"荣耀畅玩平板no","shop_price":"1499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/43/goods_thumb_43_305_305.jpeg"}]
             * page : 2
             */

            private int page;
            private List<GoodListBean> list;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public List<GoodListBean> getList() {
                return list;
            }

            public void setList(List<GoodListBean> list) {
                this.list = list;
            }

            public static class GoodListBean implements GoodsListImpl {
                /**
                 * goods_id : 39
                 * goods_name : 华为（HUAWE
                 * shop_price : 2288.00
                 * goods_remark : 今日起商城搞活动,注册立马送3
                 * thumb_url : /shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg
                 */

                private String goods_id;
                private String goods_name;
                private String shop_price;
                private String goods_remark;
                private String thumb_url;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getShop_price() {
                    return shop_price;
                }

                public void setShop_price(String shop_price) {
                    this.shop_price = shop_price;
                }

                public String getGoods_remark() {
                    return goods_remark;
                }

                public void setGoods_remark(String goods_remark) {
                    this.goods_remark = goods_remark;
                }

                public String getThumb_url() {
                    return thumb_url;
                }

                public void setThumb_url(String thumb_url) {
                    this.thumb_url = thumb_url;
                }
            }
        }
    }
}
