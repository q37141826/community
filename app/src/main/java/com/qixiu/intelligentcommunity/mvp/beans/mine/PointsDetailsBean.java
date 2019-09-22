package com.qixiu.intelligentcommunity.mvp.beans.mine;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/8/29.
 */

public class PointsDetailsBean extends BaseBean<PointsDetailsBean.OBean>{


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:52"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:48"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:45"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:41"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:51"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:47"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:44"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:40"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 10:51"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 10:46"}],"page":19}
     * e :
     */


    public static class OBean {
        /**
         * list : [{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:52"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:48"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:45"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 13:41"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:51"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:47"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:44"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 11:40"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 10:51"},{"inter":"0.01","type":1,"inter_type":1,"addtime":"2017-09-08 10:46"}]
         * page : 19
         */

        private int page;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * inter : 0.01
             * type : 1
             * inter_type : 1
             * addtime : 2017-09-08 13:52
             */

            private String inter;
            private int type;
            private int inter_type;
            private String addtime;

            public String getInter() {
                return inter;
            }

            public void setInter(String inter) {
                this.inter = inter;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getInter_type() {
                return inter_type;
            }

            public void setInter_type(int inter_type) {
                this.inter_type = inter_type;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }
        }
    }
}
