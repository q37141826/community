package com.qixiu.intelligentcommunity.mvp.beans.identify;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class PeriodsBean extends BaseBean<List<PeriodsBean.OBean>>{


    /**
     * c : 1
     * m : 查询成功
     * o : [{"id":1,"fid":1,"name":"一期"},{"id":2,"fid":1,"name":"二期"}]
     * e :
     */



    public static class OBean {
        /**
         * id : 1
         * fid : 1
         * name : 一期
         */

        private int id;
        private int fid;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
