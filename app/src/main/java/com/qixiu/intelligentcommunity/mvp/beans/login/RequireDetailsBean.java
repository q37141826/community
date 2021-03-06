package com.qixiu.intelligentcommunity.mvp.beans.login;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/6/26.
 */

public class RequireDetailsBean extends BaseBean<RequireDetailsBean.OBean> {

    /**
     * c : 1
     * m : 查询成功
     * o : {"id":3,"phone":"18771112603","password":"123456","true_name":"俐呀","nickname":"18771112603","head":"http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0","estate":"万科花园小区","utype":2,"period":"二期","building":"一栋","unit":"一单元","hnum":12035,"idcard":"420684199512145656","face_img":"sdjass1213123","reserv_phone":"","register_time":1495621167,"device_type":1,"auth":2,"device":"123456789"}
     * e :
     */
    public static class OBean {
        /**
         * id : 3
         * phone : 18771112603
         * password : 123456
         * true_name : 俐呀
         * nickname : 18771112603
         * head : http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0
         * estate : 万科花园小区
         * utype : 2
         * period : 二期
         * building : 一栋
         * unit : 一单元
         * hnum : 12035
         * idcard : 420684199512145656
         * face_img : sdjass1213123
         * reserv_phone :
         * register_time : 1495621167
         * device_type : 1
         * auth : 2
         * device : 123456789
         */

        private int id;
        private String phone;
        private String password;
        private String true_name;
        private String nickname;
        private String head;
        private String estate;
        private int utype;
        private String period;
        private String building;
        private String unit;
        private int hnum;
        private String idcard;
        private String face_img;
        private String reserv_phone;
        private int register_time;
        private int device_type;
        private int auth;
        private String device;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getEstate() {
            return estate;
        }

        public void setEstate(String estate) {
            this.estate = estate;
        }

        public int getUtype() {
            return utype;
        }

        public void setUtype(int utype) {
            this.utype = utype;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getHnum() {
            return hnum;
        }

        public void setHnum(int hnum) {
            this.hnum = hnum;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getFace_img() {
            return face_img;
        }

        public void setFace_img(String face_img) {
            this.face_img = face_img;
        }

        public String getReserv_phone() {
            return reserv_phone;
        }

        public void setReserv_phone(String reserv_phone) {
            this.reserv_phone = reserv_phone;
        }

        public int getRegister_time() {
            return register_time;
        }

        public void setRegister_time(int register_time) {
            this.register_time = register_time;
        }

        public int getDevice_type() {
            return device_type;
        }

        public void setDevice_type(int device_type) {
            this.device_type = device_type;
        }

        public int getAuth() {
            return auth;
        }

        public void setAuth(int auth) {
            this.auth = auth;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }
    }
}
