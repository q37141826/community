package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class HomePayRecordBean extends BaseBean<HomePayRecordBean.OBean>{


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"log_id":239,"change_time":"2017年05月24日","user_money":"2000.00"}],"page":2}
     * e :
     */


    public static class OBean {
        /**
         * list : [{"log_id":239,"change_time":"2017年05月24日","user_money":"2000.00"}]
         * page : 2
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
             * log_id : 239
             * change_time : 2017年05月24日
             * user_money : 2000.00
             */

            private int log_id;
            private String change_time;
            private String user_money;
            private boolean IS_LAST=false;

            public boolean IS_LAST() {
                return IS_LAST;
            }

            public void setIS_LAST(boolean IS_LAST) {
                this.IS_LAST = IS_LAST;
            }

            public int getLog_id() {
                return log_id;
            }

            public void setLog_id(int log_id) {
                this.log_id = log_id;
            }

            public String getChange_time() {
                return change_time;
            }

            public void setChange_time(String change_time) {
                this.change_time = change_time;
            }

            public String getUser_money() {
                return user_money;
            }

            public void setUser_money(String user_money) {
                this.user_money = user_money;
            }
        }
    }
}
