package com.qixiu.intelligentcommunity.mvp.beans.store.order;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderDetailsBean {

    /**
     * c : 1
     * m : 订单列表
     * o : {"order":{"order_id":"80","order_sn":"201601231454088299","is_deliver":"1","is_common":"1","consignee":"刘先生","mobile":"13986765412","province":"3","city":"39","district":"416","address":"海兴大厦","goods_price":"1700.01","shipping_price":"0.00","total_amount":"0.00","pay_code":"alipay","add_time":"2016-01-23 14:54:08","pay_time":"0"},"goods":[{"goods_id":"1","goods_name":"荣耀 畅玩46","goods_price":"200.00","goods_num":"4","thumb_url":"/shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg"},{"goods_id":"1","goods_name":"荣耀 畅玩46","goods_price":"300.00","goods_num":"3","thumb_url":"/shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg"}]}
     * e :
     */

    private int c;
    private String m;
    private OBean o;
    private String e;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public OBean getO() {
        return o;
    }

    public void setO(OBean o) {
        this.o = o;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public static class OBean {
        /**
         * order : {"order_id":"80","order_sn":"201601231454088299","is_deliver":"1","is_common":"1","consignee":"刘先生","mobile":"13986765412","province":"3","city":"39","district":"416","address":"海兴大厦","goods_price":"1700.01","shipping_price":"0.00","total_amount":"0.00","pay_code":"alipay","add_time":"2016-01-23 14:54:08","pay_time":"0"}
         * goods : [{"goods_id":"1","goods_name":"荣耀 畅玩46","goods_price":"200.00","goods_num":"4","thumb_url":"/shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg"},{"goods_id":"1","goods_name":"荣耀 畅玩46","goods_price":"300.00","goods_num":"3","thumb_url":"/shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg"}]
         */

        private OrderBean order;
        private List<GoodsBean> goods;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class OrderBean {
            /**
             * order_id : 80
             * order_sn : 201601231454088299
             * is_deliver : 1
             * is_common : 1
             * consignee : 刘先生
             * mobile : 13986765412
             * province : 3
             * city : 39
             * district : 416
             * address : 海兴大厦
             * goods_price : 1700.01
             * shipping_price : 0.00
             * total_amount : 0.00
             * pay_code : alipay
             * add_time : 2016-01-23 14:54:08
             * pay_time : 0
             */

            private String order_id;
            private String order_sn;
            private String is_deliver;
            private String is_common;
            private String consignee;
            private String mobile;
            private String province;
            private String city;
            private String district;
            private String address;
            private String goods_price;
            private String shipping_price;
            private String total_amount;
            private String pay_code;
            private String add_time;
            private String pay_time;

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }

            public String getIs_deliver() {
                return is_deliver;
            }

            public void setIs_deliver(String is_deliver) {
                this.is_deliver = is_deliver;
            }

            public String getIs_common() {
                return is_common;
            }

            public void setIs_common(String is_common) {
                this.is_common = is_common;
            }

            public String getConsignee() {
                return consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getShipping_price() {
                return shipping_price;
            }

            public void setShipping_price(String shipping_price) {
                this.shipping_price = shipping_price;
            }

            public String getTotal_amount() {
                return total_amount;
            }

            public void setTotal_amount(String total_amount) {
                this.total_amount = total_amount;
            }

            public String getPay_code() {
                return pay_code;
            }

            public void setPay_code(String pay_code) {
                this.pay_code = pay_code;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }
        }

        public static class GoodsBean {
            /**
             * goods_id : 1
             * goods_name : 荣耀 畅玩46
             * goods_price : 200.00
             * goods_num : 4
             * thumb_url : /shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg
             */

            private String goods_id;
            private String goods_name;
            private String goods_price;
            private String goods_num;
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

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_num() {
                return goods_num;
            }

            public void setGoods_num(String goods_num) {
                this.goods_num = goods_num;
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
