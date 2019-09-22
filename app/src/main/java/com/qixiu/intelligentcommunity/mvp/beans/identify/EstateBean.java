package com.qixiu.intelligentcommunity.mvp.beans.identify;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class EstateBean extends BaseBean<List<EstateBean.OBean>> {


    /**
     * c : 1
     * m : 查询成功
     * o : [{"id":1,"name":"万科花园小区"},{"id":2,"name":"保利花园小区"},{"id":6,"name":"万科城市花园"},{"id":8,"name":"保利国际新城"},{"id":9,"name":"蓝光coco"}]
     * e :
     */


    public static class OBean {
        /**
         * id : 1
         * name : 万科花园小区
         */

        private int id;
        private String name;

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
    }
}
