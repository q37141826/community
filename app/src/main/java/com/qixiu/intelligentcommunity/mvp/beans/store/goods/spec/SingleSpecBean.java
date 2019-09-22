package com.qixiu.intelligentcommunity.mvp.beans.store.goods.spec;

import android.os.Parcel;
import android.os.Parcelable;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;


/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class SingleSpecBean extends BaseBean<SingleSpecBean.SingleSpecInfo> {


    /**
     * o : {"key":"11_13_21_55","key_name":"网络:4G 内存:16G 屏幕:触屏 颜色:黑色","price":"1169.00","store_count":"100"}
     */


    public static class SingleSpecInfo implements Parcelable {
        /**
         * key : 11_13_21_55
         * key_name : 网络:4G 内存:16G 屏幕:触屏 颜色:黑色
         * price : 1169.00
         * store_count : 100
         */
        private String key;
        private String key_name;
        private String price;
        private String store_count;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getKey_name() {
            return key_name;
        }

        public void setKey_name(String key_name) {
            this.key_name = key_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStore_count() {
            return store_count;
        }

        public void setStore_count(String store_count) {
            this.store_count = store_count;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.key);
            dest.writeString(this.key_name);
            dest.writeString(this.price);
            dest.writeString(this.store_count);
        }

        public SingleSpecInfo() {
        }

        protected SingleSpecInfo(Parcel in) {
            this.key = in.readString();
            this.key_name = in.readString();
            this.price = in.readString();
            this.store_count = in.readString();
        }

        public static final Creator<SingleSpecInfo> CREATOR = new Creator<SingleSpecInfo>() {
            @Override
            public SingleSpecInfo createFromParcel(Parcel source) {
                return new SingleSpecInfo(source);
            }

            @Override
            public SingleSpecInfo[] newArray(int size) {
                return new SingleSpecInfo[size];
            }
        };
    }
}
