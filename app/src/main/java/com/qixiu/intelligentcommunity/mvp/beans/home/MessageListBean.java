package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/23.
 */

public class MessageListBean extends BaseBean<MessageListBean.OBean>{


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"id":4,"suid":3,"type":6,"message":"xxx请求授权","status":2,"buid":1}],"page":1}
     * e :
     */

    public static class OBean {
        /**
         * list : [{"id":4,"suid":3,"type":6,"message":"xxx请求授权","status":2,"buid":1}]
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
             * id : 4
             * suid : 3
             * type : 6
             * message : xxx请求授权
             * status : 2
             * buid : 1
             */

            private int id;
            private int suid;
            private int type;
            private String message;
            private int status;
            private int buid;
            private boolean IS_FIRST=false;

            public boolean IS_FIRST() {
                return IS_FIRST;
            }

            public void setIS_FIRST(boolean IS_FIRST) {
                this.IS_FIRST = IS_FIRST;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getSuid() {
                return suid;
            }

            public void setSuid(int suid) {
                this.suid = suid;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getBuid() {
                return buid;
            }

            public void setBuid(int buid) {
                this.buid = buid;
            }
        }
    }
}
