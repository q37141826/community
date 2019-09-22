package com.qixiu.intelligentcommunity.mvp.beans.store;

import android.os.Parcel;
import android.os.Parcelable;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class AddGoodsToCarsBean extends BaseBean<AddGoodsToCarsBean.AddGoodsToCarsInfo> {


    /**
     * o : {"cart":[{"id":"3984","user_id":"1","goods_id":"142","goods_name":"海尔（Haier","goods_price":"2699.00","goods_num":"1","spec_key_name":"","thumb_url":"/shop/public/upload/goods/thumb/142/goods_thumb_142_305_305.jpeg"},{"id":"3985","user_id":"1","goods_id":"143","goods_name":"haier海尔 ","goods_price":"0.01","goods_num":"1","spec_key_name":"","thumb_url":"/shop/public/upload/goods/thumb/143/goods_thumb_143_305_305.jpeg"},{"id":"3987","user_id":"1","goods_id":"47","goods_name":"【联通合约机 5","goods_price":"1399.00","goods_num":"1","spec_key_name":"网络:4G 内存:16G 屏幕:触屏","thumb_url":"/shop/public/upload/goods/thumb/47/goods_thumb_47_305_305.jpeg"},{"id":"3988","user_id":"1","goods_id":"1","goods_name":"Apple iP","goods_price":"6008.00","goods_num":"4","spec_key_name":"网络:4G 颜色:黑色 内存:128G","thumb_url":"/shop/public/upload/goods/thumb/1/goods_thumb_1_305_305.jpeg"},{"id":"3989","user_id":"1","goods_id":"39","goods_name":"华为（HUAWE","goods_price":"2000.00","goods_num":"1","spec_key_name":"尺寸:7寸及以下 内存:16G 颜色:银白色","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"id":"3990","user_id":"1","goods_id":"141","goods_name":"三星 Galax","goods_price":"3400.00","goods_num":"1","spec_key_name":"颜色:黑色","thumb_url":"/shop/public/upload/goods/thumb/141/goods_thumb_141_305_305.jpeg"},{"id":"3994","user_id":"1","goods_id":"57","goods_name":"TCL D50A","goods_price":"2000.00","goods_num":"2","spec_key_name":"尺寸:42","thumb_url":"/shop/public/upload/goods/thumb/57/goods_thumb_57_305_305.jpeg"}]}
     */


    public static class AddGoodsToCarsInfo {
        private List<ShopCartBean> cart;

        public List<ShopCartBean> getCart() {
            return cart;
        }

        public void setCart(List<ShopCartBean> cart) {
            this.cart = cart;
        }

        public static class ShopCartBean implements Parcelable {
            /**
             * id : 3984
             * user_id : 1
             * goods_id : 142
             * goods_name : 海尔（Haier
             * goods_price : 2699.00
             * goods_num : 1
             * spec_key_name :
             * thumb_url : /shop/public/upload/goods/thumb/142/goods_thumb_142_305_305.jpeg
             */

            private String id;
            private String user_id;
            private String goods_id;
            private String goods_name;
            private String goods_price;
            private String goods_num;
            private String spec_key_name;
            private String thumb_url;
            private boolean isSelected;
            private String postage;

            public String getPostage() {
                return postage;
            }

            public void setPostage(String postage) {
                this.postage = postage;
            }


            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.user_id);
                dest.writeString(this.goods_id);
                dest.writeString(this.goods_name);
                dest.writeString(this.goods_price);
                dest.writeString(this.goods_num);
                dest.writeString(this.spec_key_name);
                dest.writeString(this.thumb_url);
                dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
                dest.writeString(this.postage);
            }

            public ShopCartBean() {
            }

            protected ShopCartBean(Parcel in) {
                this.id = in.readString();
                this.user_id = in.readString();
                this.goods_id = in.readString();
                this.goods_name = in.readString();
                this.goods_price = in.readString();
                this.goods_num = in.readString();
                this.spec_key_name = in.readString();
                this.thumb_url = in.readString();
                this.isSelected = in.readByte() != 0;
                this.postage = in.readString();
            }

            public static final Creator<ShopCartBean> CREATOR = new Creator<ShopCartBean>() {
                @Override
                public ShopCartBean createFromParcel(Parcel source) {
                    return new ShopCartBean(source);
                }

                @Override
                public ShopCartBean[] newArray(int size) {
                    return new ShopCartBean[size];
                }
            };
        }
    }
}
