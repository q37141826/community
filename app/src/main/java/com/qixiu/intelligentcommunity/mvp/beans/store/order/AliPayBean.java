package com.qixiu.intelligentcommunity.mvp.beans.store.order;

/**
 * Created by HuiHeZe on 2017/5/25.
 */

public class AliPayBean {


    /**
     * c : 1
     * m : 生成订单支付宝成功
     * o : {"alipay":"partner=\"2088002379466999\"&seller_id=\"whnaive@163.com\"&out_trade_no=\"201705251515246334\"&subject=\"订单付款1744\"&body=\"1744\"&total_fee=\"0.01\"&notify_url=\"http://www.fun.com/App/Alipay/alipay_notify\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&trade_money=\"0.01\"&sign=\"EplrbXpIjdkKbXi7sYLBdOQLynJrx9wU2BCqMEJ0FyYFb1GluGQ%2BZJiur3xZ%2BzCoSqyV7cNAck5Ne50%2B%2FKdnI8a0pdlkj8LW8kHiCeXS%2B%2FW2JkM%2FVNU15%2Bha0Unyf6wESr8RN5LBcy8GHn%2BejCj2Zkk%2BEtLbaYtjBVnpm0TKIzk%3D\"&sign_type=\"RSA\"","order_id":"1744"}
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
         * alipay : partner="2088002379466999"&seller_id="whnaive@163.com"&out_trade_no="201705251515246334"&subject="订单付款1744"&body="1744"&total_fee="0.01"&notify_url="http://www.fun.com/App/Alipay/alipay_notify"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="30m"&trade_money="0.01"&sign="EplrbXpIjdkKbXi7sYLBdOQLynJrx9wU2BCqMEJ0FyYFb1GluGQ%2BZJiur3xZ%2BzCoSqyV7cNAck5Ne50%2B%2FKdnI8a0pdlkj8LW8kHiCeXS%2B%2FW2JkM%2FVNU15%2Bha0Unyf6wESr8RN5LBcy8GHn%2BejCj2Zkk%2BEtLbaYtjBVnpm0TKIzk%3D"&sign_type="RSA"
         * order_id : 1744
         */

        private String alipay;
        private String order_id;

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
