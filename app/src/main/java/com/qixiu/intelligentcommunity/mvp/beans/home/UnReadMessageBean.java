package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

public class UnReadMessageBean extends BaseBean<UnReadMessageBean.OBean> {


    public static class OBean {
        /**
         * activity_unread : 12
         * notice_unread : 59
         * news_unread : 59
         * messages_unread : 0
         */

        private int activity_unread;
        private int notice_unread;
        private int news_unread;
        private int messages_unread;

        public int getActivity_unread() {
            return activity_unread;
        }

        public void setActivity_unread(int activity_unread) {
            this.activity_unread = activity_unread;
        }

        public int getNotice_unread() {
            return notice_unread;
        }

        public void setNotice_unread(int notice_unread) {
            this.notice_unread = notice_unread;
        }

        public int getNews_unread() {
            return news_unread;
        }

        public void setNews_unread(int news_unread) {
            this.news_unread = news_unread;
        }

        public int getMessages_unread() {
            return messages_unread;
        }

        public void setMessages_unread(int messages_unread) {
            this.messages_unread = messages_unread;
        }


        public int getAllUnread() {
            return getActivity_unread() + getMessages_unread() + getNews_unread() + getNotice_unread();
        }
    }
}
