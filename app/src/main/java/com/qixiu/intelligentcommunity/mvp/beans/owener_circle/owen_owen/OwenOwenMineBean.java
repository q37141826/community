package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenOwenMineBean {


    /**
     * c : 1
     * m : 查询成功
     * o : {"list":[{"time":2017,"data":[{"time":"05/07月","data":[{"id":96,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg"],"content":"放寒假加不加","addtimes":"2017"},{"id":95,"addtime":"05/07月","imgs":[],"content":"就是就是就是可是可是","addtimes":"2017"},{"id":85,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_0f68a74bff873174d1c83bb996c3f25a.jpg"],"content":"u杜叔叔","addtimes":"2017"}]}]}],"page":1}
     * e :
     */

    private int c;
    private String m;
    private OBean o;
    private String e;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public OBean getO() {
        return o;
    }

    public void setO(OBean o) {
        this.o = o;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public static class OBean {
        /**
         * list : [{"time":2017,"data":[{"time":"05/07月","data":[{"id":96,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg"],"content":"放寒假加不加","addtimes":"2017"},{"id":95,"addtime":"05/07月","imgs":[],"content":"就是就是就是可是可是","addtimes":"2017"},{"id":85,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_0f68a74bff873174d1c83bb996c3f25a.jpg"],"content":"u杜叔叔","addtimes":"2017"}]}]}]
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
             * time : 2017
             * data : [{"time":"05/07月","data":[{"id":96,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg"],"content":"放寒假加不加","addtimes":"2017"},{"id":95,"addtime":"05/07月","imgs":[],"content":"就是就是就是可是可是","addtimes":"2017"},{"id":85,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_0f68a74bff873174d1c83bb996c3f25a.jpg"],"content":"u杜叔叔","addtimes":"2017"}]}]
             */

            private int time;
            private List<DataBeanX> data;

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public List<DataBeanX> getData() {
                return data;
            }

            public void setData(List<DataBeanX> data) {
                this.data = data;
            }

            public static class DataBeanX {
                /**
                 * time : 05/07月
                 * data : [{"id":96,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg"],"content":"放寒假加不加","addtimes":"2017"},{"id":95,"addtime":"05/07月","imgs":[],"content":"就是就是就是可是可是","addtimes":"2017"},{"id":85,"addtime":"05/07月","imgs":["/public/upload/20170705/thumb_0f68a74bff873174d1c83bb996c3f25a.jpg"],"content":"u杜叔叔","addtimes":"2017"}]
                 */

                private String time;
                private List<DataBean> data;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class DataBean {
                    /**
                     * id : 96
                     * addtime : 05/07月
                     * imgs : ["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg"]
                     * content : 放寒假加不加
                     * addtimes : 2017
                     */
                    private boolean IS_FiRST=false;
                    private int id;
                    private String addtime;
                    private String content;
                    private String addtimes;
                    private List<String> imgs;

                    public boolean IS_FiRST() {
                        return IS_FiRST;
                    }

                    public void setIS_FiRST(boolean IS_FiRST) {
                        this.IS_FiRST = IS_FiRST;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getAddtime() {
                        return addtime;
                    }

                    public void setAddtime(String addtime) {
                        this.addtime = addtime;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getAddtimes() {
                        return addtimes;
                    }

                    public void setAddtimes(String addtimes) {
                        this.addtimes = addtimes;
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
    }
}
