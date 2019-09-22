package com.qixiu.intelligentcommunity.mvp.beans.store.order;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HuiHeZe on 2017/5/12.
 */

public class AddressListBean extends BaseBean< List<AddressListBean.OBean>> {



    public static class OBean implements Serializable{
        /**
         * address_id : 854
         * is_default : 1
         * consignee : 邓紫棋
         * address : 1863885
         * zipcode : 285525
         * mobile : 13554550382
         * province : 湖北省
         * city : 武汉市
         * district : 洪山区
         */

        private String address_id;
        private String is_default;
        private String consignee;
        private String address;
        private String zipcode;
        private String mobile;
        private String province;
        private String city;
        private String district;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
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
    }
}
