package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

public class HomeNoticeBean  extends BaseBean<HomeNoticeBean.OBean> {


    /**
     * c : 1
     * m : 查询成功
     * o : {"notice_one":{"id":107,"title":"物业恳谈会\u2014\u2014衷心与业主的心走到一起","type":1,"estate":57},"news_one":{"id":108,"title":"莫愁展览活动\u2014\u2014文化贺新春·欢乐英雄城","type":2,"estate":57}}
     * e :
     */



    public static class OBean {
        /**
         * notice_one : {"id":107,"title":"物业恳谈会\u2014\u2014衷心与业主的心走到一起","type":1,"estate":57}
         * news_one : {"id":108,"title":"莫愁展览活动\u2014\u2014文化贺新春·欢乐英雄城","type":2,"estate":57}
         */

        private NoticeOneBean notice_one;
        private NewsOneBean news_one;

        public NoticeOneBean getNotice_one() {
            return notice_one;
        }

        public void setNotice_one(NoticeOneBean notice_one) {
            this.notice_one = notice_one;
        }

        public NewsOneBean getNews_one() {
            return news_one;
        }

        public void setNews_one(NewsOneBean news_one) {
            this.news_one = news_one;
        }

        public static class NoticeOneBean {
            /**
             * id : 107
             * title : 物业恳谈会——衷心与业主的心走到一起
             * type : 1
             * estate : 57
             */

            private int id;
            private String title;
            private int type;
            private String estate;
            private String addtime_desc;

            public String getAddtime_desc() {
                return addtime_desc;
            }

            public void setAddtime_desc(String addtime_desc) {
                this.addtime_desc = addtime_desc;
            }

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getEstate() {
                return estate;
            }

            public void setEstate(String estate) {
                this.estate = estate;
            }
        }

        public static class NewsOneBean {
            /**
             * id : 108
             * title : 莫愁展览活动——文化贺新春·欢乐英雄城
             * type : 2
             * estate : 57
             */

            private int id;
            private String title;
            private int type;
            private String estate;
            private String addtime_desc;

            public String getAddtime_desc() {
                return addtime_desc;
            }

            public void setAddtime_desc(String addtime_desc) {
                this.addtime_desc = addtime_desc;
            }

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getEstate() {
                return estate;
            }

            public void setEstate(String estate) {
                this.estate = estate;
            }
        }
    }
}
