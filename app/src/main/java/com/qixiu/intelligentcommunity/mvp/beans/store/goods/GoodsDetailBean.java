package com.qixiu.intelligentcommunity.mvp.beans.store.goods;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class GoodsDetailBean extends BaseBean<GoodsDetailBean.GoodsDetailInfos> {


    /**
     * o : {"goods_Info":[{"goods_id":"1","goods_sn":"TP000000","goods_name":"Apple iPhone 6s Plus 16G 玫瑰金 移动联通电信4G手机","store_count":"299","shop_price":"6007.00","keywords":"LG 3g 876 支持 双模 2008年04月 灰色 GSM,850,900,1800,1900","goods_remark":"选择【联通合约机0元购机】，购机款仅需4066 选择【移动合约机】，锯惠5588，不换号，购机入网返高额话费~ 选【电信合约机】，买手机送话费！激活到账100元，实付低至29元/月，月享4GB大流量","is_recommend":"1","sales_sum":"0"}],"goods_images_list":[{"image_url":"/public/upload/goods/2016/03-09/56e01a54a2c6d.jpg"},{"image_url":"/public/upload/goods/2016/03-09/56e01a54bcc53.jpg"},{"image_url":"/public/upload/goods/2016/03-09/56e01a54de5a9.jpg"},{"image_url":"/public/upload/goods/2016/03-09/56e01a4088d3b.jpg"}],"spec_goods_price":[{"key_name":["网络:4G","颜色:玫瑰金","内存:128G"],"price":"0.08","store_count":"100"},{"price":"0.08"},{"store_count":"100"},{"key_name":["网络:4G","颜色:银色","内存:128G"]},{"price":"5808.00"},{"store_count":"100"},{"key_name":["网络:4G","颜色:黑色","内存:128G"]},{"price":"6008.00"},{"store_count":"99"}],"goods_comment":[{"username":"茱莉亚","content":"这东西不错,下次还会来买","add_time":"1457746403","img":"","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":""},{"username":"蒙娜丽莎","content":"不错,买回来老公很喜欢","add_time":"1457746449","img":"","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":""},{"username":"嫦娥","content":"晒单给大家看看.我刚买的.","add_time":"1457746625","img":"/public/upload/goods/2016/03-09/56e01a54a2c6d.jpg;/public/upload/goods/2016/03-09/56e01a54bcc53.jpg","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":""},{"username":"jack","content":"dsfasfsadfasdfsa","add_time":"1484882861","img":"/public/upload/comment/2017/01-20/588183ad67ef1.jpg","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":""},{"username":"jack","content":"dsfasfsadfasdfsa","add_time":"1484882861","img":"/public/upload/comment/2017/01-20/588183ad67ef1.jpg","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":""},{"username":"jack","content":"dsfasfsadfasdfsa","add_time":"1484882861","img":"/public/upload/comment/2017/01-20/588183ad67ef1.jpg","head":"/Upload/App/TestData/002564a5d7d2173787c550.jpg","spec_key_name":"网络:4G 颜色:黑色 内存:128G"}]}
     */


    public static class GoodsDetailInfos {
        private GoodsInfoBean goods_Info;
        private List<String> goods_images_list;
        private List<SpecGoodsPriceBean> spec_goods_price;
        private List<GoodsCommentBean> goods_comment;

        public GoodsInfoBean getGoods_Info() {
            return goods_Info;
        }

        public void setGoods_Info(GoodsInfoBean goods_Info) {
            this.goods_Info = goods_Info;
        }

        public List<String> getGoods_images_list() {
            return goods_images_list;
        }

        public void setGoods_images_list(List<String> goods_images_list) {
            this.goods_images_list = goods_images_list;
        }

        public List<SpecGoodsPriceBean> getSpec_goods_price() {
            return spec_goods_price;
        }

        public void setSpec_goods_price(List<SpecGoodsPriceBean> spec_goods_price) {
            this.spec_goods_price = spec_goods_price;
        }

        public List<GoodsCommentBean> getGoods_comment() {
            return goods_comment;
        }

        public void setGoods_comment(List<GoodsCommentBean> goods_comment) {
            this.goods_comment = goods_comment;
        }

        public static class GoodsInfoBean {
            /**
             * goods_id : 1
             * goods_sn : TP000000
             * goods_name : Apple iPhone 6s Plus 16G 玫瑰金 移动联通电信4G手机
             * store_count : 299
             * shop_price : 6007.00
             * keywords : LG 3g 876 支持 双模 2008年04月 灰色 GSM,850,900,1800,1900
             * goods_remark : 选择【联通合约机0元购机】，购机款仅需4066 选择【移动合约机】，锯惠5588，不换号，购机入网返高额话费~ 选【电信合约机】，买手机送话费！激活到账100元，实付低至29元/月，月享4GB大流量
             * is_recommend : 1
             * sales_sum : 0
             */

            private String goods_id;
            private String goods_sn;
            private String goods_name;
            private String store_count;
            private String shop_price;
            private String keywords;
            private String goods_remark;
            private String is_recommend;
            private String sales_sum;
            private String goods_type;
            private String postage;

            public String getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public String getPostage() {
                return postage;
            }

            public void setPostage(String postage) {
                this.postage = postage;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_sn() {
                return goods_sn;
            }

            public void setGoods_sn(String goods_sn) {
                this.goods_sn = goods_sn;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getStore_count() {
                return store_count;
            }

            public void setStore_count(String store_count) {
                this.store_count = store_count;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getGoods_remark() {
                return goods_remark;
            }

            public void setGoods_remark(String goods_remark) {
                this.goods_remark = goods_remark;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getSales_sum() {
                return sales_sum;
            }

            public void setSales_sum(String sales_sum) {
                this.sales_sum = sales_sum;
            }
        }

        public static class GoodsImagesListBean {
            /**
             * image_url : /public/upload/goods/2016/03-09/56e01a54a2c6d.jpg
             */

            private String image_url;

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }
        }

        public static class SpecGoodsPriceBean {

            /**
             * id : 5
             * name : 网络
             * spec : [{"id":"11","item":"4G"},{"id":"12","item":"3G"}]
             */

            private String id;
            private String name;
            private boolean isSelectSpecLine;
            public boolean isSelectSpecLine() {
                return isSelectSpecLine;
            }

            public void setSelectSpecLine(boolean selectSpecLine) {
                isSelectSpecLine = selectSpecLine;
            }


            private List<SpecBean> spec;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<SpecBean> getSpec() {
                return spec;
            }

            public void setSpec(List<SpecBean> spec) {
                this.spec = spec;
            }

            public static class SpecBean {
                /**
                 * id : 11
                 * item : 4G
                 */
                private String id;
                private String item;
                //条目选择状态
                private boolean isSelect;

                public boolean isSelect() {
                    return isSelect;
                }

                public void setSelect(boolean select) {
                    isSelect = select;
                }


                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getItem() {
                    return item;
                }

                public void setItem(String item) {
                    this.item = item;
                }
            }
        }

        public static class GoodsCommentBean {
            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            /**
             * nickname : 茱莉亚
             * content : 这东西不错,下次还会来买
             * add_time : 1457746403
             * img :
             * head : /Upload/App/TestData/002564a5d7d2173787c550.jpg
             * spec_key_name :
             */

            private String nickname;
            private String order_id;
            private String content;
            private String add_time;
            private String img;
            private String head;
            private String spec_key_name;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getSpec_key_name() {
                return spec_key_name;
            }

            public void setSpec_key_name(String spec_key_name) {
                this.spec_key_name = spec_key_name;
            }
        }
    }
}
