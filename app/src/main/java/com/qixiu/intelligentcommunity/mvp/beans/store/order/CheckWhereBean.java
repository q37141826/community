package com.qixiu.intelligentcommunity.mvp.beans.store.order;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/5/24.
 */

public class CheckWhereBean  {


    /**
     * message : ok
     * nu : 884518225406150756
     * ischeck : 1
     * condition : D01
     * com : yuantong
     * status : 200
     * state : 3
     * data : [{"time":"2017-03-23 13:55:39","ftime":"2017-03-23 13:55:39","context":"客户 签收人 : 万科会所菜鸟驿站 已签收  感谢使用圆通速递，期待再次为您服务"},{"time":"2017-03-23 12:18:17","ftime":"2017-03-23 12:18:17","context":"快件已由万科魅力会所特5区店菜鸟驿站代收，请及时取件，如有疑问请联系13027173141"},{"time":"2017-03-23 09:03:58","ftime":"2017-03-23 09:03:58","context":"湖北省武汉市江夏区水晶郦都公司(点击查询电话)李** 派件中 派件员电话13430950543"},{"time":"2017-03-23 05:20:53","ftime":"2017-03-23 05:20:53","context":"湖北省武汉市武昌区庙山公司 已发出,下一站 湖北省武汉市江夏区水晶郦都"},{"time":"2017-03-22 23:00:48","ftime":"2017-03-22 23:00:48","context":"武汉转运中心 已发出,下一站 湖北省武汉市武昌区庙山"},{"time":"2017-03-22 22:58:21","ftime":"2017-03-22 22:58:21","context":"武汉转运中心 已收入"},{"time":"2017-03-22 00:05:23","ftime":"2017-03-22 00:05:23","context":"江门转运中心 已发出,下一站 武汉转运中心"},{"time":"2017-03-22 00:03:48","ftime":"2017-03-22 00:03:48","context":"江门转运中心 已收入"},{"time":"2017-03-21 16:05:22","ftime":"2017-03-21 16:05:22","context":"广东省江门市开平市公司 取件人 : 梁丽娟 已收件"}]
     * imgUrl : /shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg
     * shipping_name : 圆通快递
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private String imgUrl;
    private String shipping_name;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2017-03-23 13:55:39
         * ftime : 2017-03-23 13:55:39
         * context : 客户 签收人 : 万科会所菜鸟驿站 已签收  感谢使用圆通速递，期待再次为您服务
         */

        private String time;
        private String ftime;
        private String context;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
