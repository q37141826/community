package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/8/25.
 */

public class GetGoodsRecordBean  extends BaseBean<GetGoodsRecordBean.OBean>{


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"id":1,"name":"冰箱","addtime":"2017/08/23 14:46"}],"page":2}
     * e :
     */



    public static class OBean {
        /**
         * list : [{"id":1,"name":"冰箱","addtime":"2017/08/23 14:46"}]
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
             * id : 1
             * name : 冰箱
             * addtime : 2017/08/23 14:46
             */

            private String id;
            private String name;
            private String addtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
