package com.qixiu.intelligentcommunity.mvp.beans.store;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class StoreCategoryBean  extends BaseBean<StoreCategoryBean.StoreCategoryInfo> {


    /**
     * o : {"goods":{"list":[{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}],"page":18}}
     */


    public static class StoreCategoryInfo {
        /**
         * goods : {"list":[{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}],"page":18}
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
             * list : [{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}]
             * page : 18
             */

            private int page;
            private List<GoodsListBean> list;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public List<GoodsListBean> getList() {
                return list;
            }

            public void setList(List<GoodsListBean> list) {
                this.list = list;
            }

            public static class GoodsListBean {
                /**
                 * goods_id : 65
                 * goods_name : 长虹(CHANG
                 * shop_price : 2799.00
                 * goods_remark : 今日起商城搞活动,注册立马送3
                 * thumb_url : /shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg
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
