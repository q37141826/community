package com.qixiu.intelligentcommunity.mvp.beans.store.order;

import com.google.gson.annotations.SerializedName;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by Administrator on 2016/12/26.
 */
public class WeixinPayModel extends BaseBean<WeixinPayModel.OBean> {


    public static class OBean {
        /**
         * appid : wx56dd783d30568120
         * noncestr : 242d662f44e1eff7757b8bc966cc7705
         * package : Sign=WXPay
         * partnerid : 1458642902
         * prepayid : null
         * timestamp : 1493104659
         * sign : A8C2D5F4EC70EB8A7BF106FDF3905EB0
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private int timestamp;
        private String sign;
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

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
//
//
//    /**
//     * o : null
//     * e : null
//     */
//
//    private Object o;
//    private Object e;
//
//    public Object getO() {
//        return o;
//    }
//
//    public void setO(Object o) {
//        this.o = o;
//    }
//
//    public Object getE() {
//        return e;
//    }
//
//    public void setE(Object e) {
//        this.e = e;
//    }



}
