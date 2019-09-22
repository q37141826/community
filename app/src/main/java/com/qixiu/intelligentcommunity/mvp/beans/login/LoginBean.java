package com.qixiu.intelligentcommunity.mvp.beans.login;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/6/19.
 */

public class LoginBean extends BaseBean<LoginBean.OBean> {

    /**
     * c : 1
     * m : 登陆成功
     * o : {"id":1,"phone":"18771112603","password":"123456","true_name":"","nickname":"18771112603","head":"","estate":"","utype":"","period":"","building":"","unit":"","hnum":"","idcard":"","face_img":"","reserv_phone":"","register_time":1495621066,"device_type":2,"auth":1,"device":"987654321"}
     * e :
     */


    public static class OBean {
        /**
         * id : 1
         * phone : 18771112603
         * password : 123456
         * true_name :
         * nickname : 18771112603
         * head :
         * estate :
         * utype :
         * period :
         * building :
         * unit :
         * hnum :
         * idcard :
         * face_img :
         * reserv_phone :
         * register_time : 1495621066
         * device_type : 2
         * auth : 1
         * device : 987654321
         */

        private String id;
        private String phone;
        private String password;
        private String true_name;
        private String nickname;
        private String head;
        private String estate;
        private String utype;
        private String period;
        private String building;
        private String unit;
        private String hnum;
        private String idcard;
        private String face_img;
        private String reserv_phone;
        private int register_time;
        private int device_type;
        private String auth;
        private String device;
        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getUtype() {
            return utype;
        }

        public void setUtype(String utype) {
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

        public String getHnum() {
            return hnum;
        }

        public void setHnum(String hnum) {
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

        public String getAuth() {
            return auth;
        }

        public void setAuth(String auth) {
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
