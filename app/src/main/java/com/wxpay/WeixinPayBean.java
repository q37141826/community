package com.wxpay;

import com.google.gson.annotations.SerializedName;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/6/28.
 */

public class WeixinPayBean extends BaseBean<WeixinPayBean.OBean>{




    public static class OBean {
        /**
         * appid : wx19b38ecb56927764
         * noncestr : 52fa8306aa9fbd5b359b5c1707a03845
         * package : Sign=WXPay
         * partnerid : 1413713102
         * prepayid : wx20170905094357bfaea0b7980370493979
         * timestamp : 1504575837
         * sign : A689E8261DDCC87EF5D55AB562EAD840
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
