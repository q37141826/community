package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/8/29.
 */

public class GetGoodsDetailsBean extends BaseBean<GetGoodsDetailsBean.OBean>{

    /**
     * c : 1
     * m : 查询成功
     * o : {"id":1,"name":"冰箱","gtime":"2107/8/29 13:00-14:00","status":1}
     * e :
     */


    public static class OBean {
        /**
         * id : 1
         * name : 冰箱
         * gtime : 2107/8/29 13:00-14:00
         * status : 1
         */

        private int id;
        private String name;
        private String gtime;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGtime() {
            return gtime;
        }

        public void setGtime(String gtime) {
            this.gtime = gtime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
