package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/9/1.
 */

public class HomeHotGoodsBean extends BaseBean<HomeHotGoodsBean.OBean>{


    /**
     * c : 1
     * m : 商城首页
     * o : {"list":[{"goods_id":207,"goods_name":"韩版夏季时尚简约条纹","shop_price":"0.01","goods_remark":"海量新品 潮流穿搭 玩趣互动","thumb_url":"/shop/public/upload/goods/thumb/207/goods_thumb_207_305_305.jpeg"},{"goods_id":204,"goods_name":"良品铺子甘栗仁小栗子","shop_price":"0.01","goods_remark":"专区199减100 下单赢家庭","thumb_url":"/shop/public/upload/goods/thumb/204/goods_thumb_204_305_305.jpeg"},{"goods_id":203,"goods_name":"良品铺子靖江猪肉脯 ","shop_price":"20.00","goods_remark":"专区199减100 下单赢家庭","thumb_url":"/shop/public/upload/goods/thumb/203/goods_thumb_203_305_305.jpeg"},{"goods_id":202,"goods_name":"新西兰zespri佳","shop_price":"55.00","goods_remark":"每一颗精选果子，只为您的美味体","thumb_url":"/shop/public/upload/goods/thumb/202/goods_thumb_202_305_305.jpeg"},{"goods_id":201,"goods_name":"湖南怀化芷江6斤 正","shop_price":"68.00","goods_remark":"高山刺葡萄","thumb_url":"/shop/public/upload/goods/thumb/201/goods_thumb_201_305_305.jpeg"},{"goods_id":200,"goods_name":"拾蘑菇 云南石林人参","shop_price":"48.00","goods_remark":"每一颗精选果子，只为您的美味体","thumb_url":"/shop/public/upload/goods/thumb/200/goods_thumb_200_305_305.jpeg"}],"page":4}
     * e :
     */

    public static class OBean {
        /**
         * list : [{"goods_id":207,"goods_name":"韩版夏季时尚简约条纹","shop_price":"0.01","goods_remark":"海量新品 潮流穿搭 玩趣互动","thumb_url":"/shop/public/upload/goods/thumb/207/goods_thumb_207_305_305.jpeg"},{"goods_id":204,"goods_name":"良品铺子甘栗仁小栗子","shop_price":"0.01","goods_remark":"专区199减100 下单赢家庭","thumb_url":"/shop/public/upload/goods/thumb/204/goods_thumb_204_305_305.jpeg"},{"goods_id":203,"goods_name":"良品铺子靖江猪肉脯 ","shop_price":"20.00","goods_remark":"专区199减100 下单赢家庭","thumb_url":"/shop/public/upload/goods/thumb/203/goods_thumb_203_305_305.jpeg"},{"goods_id":202,"goods_name":"新西兰zespri佳","shop_price":"55.00","goods_remark":"每一颗精选果子，只为您的美味体","thumb_url":"/shop/public/upload/goods/thumb/202/goods_thumb_202_305_305.jpeg"},{"goods_id":201,"goods_name":"湖南怀化芷江6斤 正","shop_price":"68.00","goods_remark":"高山刺葡萄","thumb_url":"/shop/public/upload/goods/thumb/201/goods_thumb_201_305_305.jpeg"},{"goods_id":200,"goods_name":"拾蘑菇 云南石林人参","shop_price":"48.00","goods_remark":"每一颗精选果子，只为您的美味体","thumb_url":"/shop/public/upload/goods/thumb/200/goods_thumb_200_305_305.jpeg"}]
         * page : 4
         */

        private int page;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * goods_id : 207
             * goods_name : 韩版夏季时尚简约条纹
             * shop_price : 0.01
             * goods_remark : 海量新品 潮流穿搭 玩趣互动
             * thumb_url : /shop/public/upload/goods/thumb/207/goods_thumb_207_305_305.jpeg
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
