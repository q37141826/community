package com.qixiu.intelligentcommunity.mvp.beans.store.order;


import android.os.Parcel;
import android.os.Parcelable;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuiHeZe on 2017/5/3.
 */

public class OrderBean extends BaseBean<OrderBean.OBean> {


    public static class OBean implements Serializable {
        /**
         * list : [{"order_id":"1672","order_sn":"201705191713159984","is_deliver":"1","order_status":"2","shipping_status":"1","pay_code":"alipay","pay_name":"支付宝支付","goods":[{"order_id":"1672","goods_id":"39","goods_name":"华为（HUAWE","goods_price":"0.01","goods_num":"1","spec_key_name":"尺寸:7寸及以下 内存:16G 颜色:银白色","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"}]}]
         * page : 1
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

        public static class ListBean implements Parcelable{
            /**
             * order_id : 1672
             * order_sn : 201705191713159984
             * is_deliver : 1
             * order_status : 2
             * shipping_status : 1
             * pay_code : alipay
             * pay_name : 支付宝支付
             * goods : [{"order_id":"1672","goods_id":"39","goods_name":"华为（HUAWE","goods_price":"0.01","goods_num":"1","spec_key_name":"尺寸:7寸及以下 内存:16G 颜色:银白色","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"}]
             */

            private String order_id;
            private String order_sn;
            private String is_deliver;
            private String order_status;
            private String shipping_status;
            private String pay_code;
            private String pay_name;
            private String is_common;
            private List<GoodsBean> goods;

            protected ListBean(Parcel in) {
                order_id = in.readString();
                order_sn = in.readString();
                is_deliver = in.readString();
                order_status = in.readString();
                shipping_status = in.readString();
                pay_code = in.readString();
                pay_name = in.readString();
                is_common = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel in) {
                    return new ListBean(in);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };

            public String getIs_common() {
                return is_common;
            }

            public void setIs_common(String is_common) {
                this.is_common = is_common;
            }

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

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getShipping_status() {
                return shipping_status;
            }

            public void setShipping_status(String shipping_status) {
                this.shipping_status = shipping_status;
            }

            public String getPay_code() {
                return pay_code;
            }

            public void setPay_code(String pay_code) {
                this.pay_code = pay_code;
            }

            public String getPay_name() {
                return pay_name;
            }

            public void setPay_name(String pay_name) {
                this.pay_name = pay_name;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(order_id);
                dest.writeString(order_sn);
                dest.writeString(is_deliver);
                dest.writeString(order_status);
                dest.writeString(shipping_status);
                dest.writeString(pay_code);
                dest.writeString(pay_name);
                dest.writeString(is_common);
            }

            public static class GoodsBean {
                /**
                 * order_id : 1672
                 * goods_id : 39
                 * goods_name : 华为（HUAWE
                 * goods_price : 0.01
                 * goods_num : 1
                 * spec_key_name : 尺寸:7寸及以下 内存:16G 颜色:银白色
                 * thumb_url : /shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg
                 */

                private String order_id;
                private String goods_id;
                private String goods_name;
                private String goods_price;
                private String goods_num;
                private String spec_key_name;
                private String thumb_url;

                public String getOrder_id() {
                    return order_id;
                }

                public void setOrder_id(String order_id) {
                    this.order_id = order_id;
                }

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

                public String getSpec_key_name() {
                    return spec_key_name;
                }

                public void setSpec_key_name(String spec_key_name) {
                    this.spec_key_name = spec_key_name;
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
