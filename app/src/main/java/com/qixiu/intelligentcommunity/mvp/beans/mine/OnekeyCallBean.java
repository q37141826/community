package com.qixiu.intelligentcommunity.mvp.beans.mine;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/21.
 */

public class OnekeyCallBean extends BaseBean<OnekeyCallBean.OBean> {


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"id":1,"name":"警察1","phone":"18945661234"},{"id":3,"name":"警察3","phone":"18779996541"}],"page":1}
     * e :
     */


    public static class OBean {
        /**
         * list : [{"id":1,"name":"警察1","phone":"18945661234"},{"id":3,"name":"警察3","phone":"18779996541"}]
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
             * name : 警察1
             * phone : 18945661234
             */

            private int id;
            private String name;
            private String phone;

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

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
