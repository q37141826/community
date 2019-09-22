package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/3 0003.
 */

public class OwenEventBean extends BaseBean<OwenEventBean.OwenEventInfo> {


    /**
     * o : {"list":[{"id":1,"title":"爬山","stime":"2017-05-31","etime":"2017-06-02","imgs":["123456554.jpg"],"num":0}],"page":1}
     */


    public static class OwenEventInfo {
        /**
         * list : [{"id":1,"title":"爬山","stime":"2017-05-31","etime":"2017-06-02","imgs":["123456554.jpg"],"num":0}]
         * page : 1
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
             * id : 1
             * title : 爬山
             * stime : 2017-05-31
             * etime : 2017-06-02
             * imgs : ["123456554.jpg"]
             * num : 0
             */

            private int id;
            private String title;
            private String stime;
            private String etime;
            private int num;
            private List<String> imgs;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getStime() {
                return stime;
            }

            public void setStime(String stime) {
                this.stime = stime;
            }

            public String getEtime() {
                return etime;
            }

            public void setEtime(String etime) {
                this.etime = etime;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }
        }
    }
}
