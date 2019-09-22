package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_answer;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/7/4.
 */

public class OwenAnswerDetailsBean {


    /**
     * c : 1
     * m : 查询成功
     * o : {"id":115,"title":"简直时间就是你是不是","content":"收拾收拾三个","addtime":"2017-07-07","uid":152,"num":3,"imgs":["/public/upload/20170707/thumb_9ce6fdcac895885f538f09615d9301e8.jpg"],"tb_imgs":["/public/upload/20170707/9ce6fdcac895885f538f09615d9301e8.jpg"],"uids":{"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"del":1}
     * e : [{"id":1596,"uid":174,"ob_uid":152,"add_time":"2017-07-07 11:04","content":"风景国家级","uids":{"nickname":"邓丽君","head":"/public/upload/20170706/bef1bba9475835194c8f6d07105f1baa.jpg"},"ppuid":[{"id":1644,"uid":{"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"邓丽君","add_time":"2017-07-07 17:39","content":"kskdkkzjzksj","auid":152}]},{"id":1598,"uid":174,"ob_uid":152,"add_time":"2017-07-07 11:26","content":"退还给哥哥","uids":{"nickname":"邓丽君","head":"/public/upload/20170706/bef1bba9475835194c8f6d07105f1baa.jpg"},"ppuid":[{"id":1645,"uid":{"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"邓丽君","add_time":"2017-07-07 17:39","content":"zhjsnsnsndk","auid":152}]},{"id":1599,"uid":174,"ob_uid":152,"add_time":"2017-07-07 11:26","content":"统计局哈哈哈哈","uids":{"nickname":"邓丽君","head":"/public/upload/20170706/bef1bba9475835194c8f6d07105f1baa.jpg"},"ppuid":[]}]
     */

    private int c;
    private String m;
    private OBean o;
    private List<EBean> e;

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

    public List<EBean> getE() {
        return e;
    }

    public void setE(List<EBean> e) {
        this.e = e;
    }

    public static class OBean {
        /**
         * id : 115
         * title : 简直时间就是你是不是
         * content : 收拾收拾三个
         * addtime : 2017-07-07
         * uid : 152
         * num : 3
         * imgs : ["/public/upload/20170707/thumb_9ce6fdcac895885f538f09615d9301e8.jpg"]
         * tb_imgs : ["/public/upload/20170707/9ce6fdcac895885f538f09615d9301e8.jpg"]
         * uids : {"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"}
         * del : 1
         */

        private int id;
        private String title;
        private String content;
        private String addtime;
        private int uid;
        private int num;
        private UidsBean uids;
        private int del;
        private List<String> imgs;
        private List<String> tb_imgs;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public UidsBean getUids() {
            return uids;
        }

        public void setUids(UidsBean uids) {
            this.uids = uids;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getTb_imgs() {
            return tb_imgs;
        }

        public void setTb_imgs(List<String> tb_imgs) {
            this.tb_imgs = tb_imgs;
        }

        public static class UidsBean {
            /**
             * nickname : 邓紫琪
             * head : /public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg
             */

            private String nickname;
            private String head;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }
    }

    public static class EBean {
        /**
         * id : 1596
         * uid : 174
         * ob_uid : 152
         * add_time : 2017-07-07 11:04
         * content : 风景国家级
         * uids : {"nickname":"邓丽君","head":"/public/upload/20170706/bef1bba9475835194c8f6d07105f1baa.jpg"}
         * ppuid : [{"id":1644,"uid":{"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"邓丽君","add_time":"2017-07-07 17:39","content":"kskdkkzjzksj","auid":152}]
         */

        private int id;
        private int uid;
        private int ob_uid;
        private String add_time;
        private String content;
        private UidsBeanX uids;
        private List<PpuidBean> ppuid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getOb_uid() {
            return ob_uid;
        }

        public void setOb_uid(int ob_uid) {
            this.ob_uid = ob_uid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UidsBeanX getUids() {
            return uids;
        }

        public void setUids(UidsBeanX uids) {
            this.uids = uids;
        }

        public List<PpuidBean> getPpuid() {
            return ppuid;
        }

        public void setPpuid(List<PpuidBean> ppuid) {
            this.ppuid = ppuid;
        }

        public static class UidsBeanX {
            /**
             * nickname : 邓丽君
             * head : /public/upload/20170706/bef1bba9475835194c8f6d07105f1baa.jpg
             */

            private String nickname;
            private String head;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }

        public static class PpuidBean {
            /**
             * id : 1644
             * uid : {"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"}
             * ob_uid : 邓丽君
             * add_time : 2017-07-07 17:39
             * content : kskdkkzjzksj
             * auid : 152
             */

            private int id;
            private UidBean uid;
            private String ob_uid;
            private String add_time;
            private String content;
            private int auid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UidBean getUid() {
                return uid;
            }

            public void setUid(UidBean uid) {
                this.uid = uid;
            }

            public String getOb_uid() {
                return ob_uid;
            }

            public void setOb_uid(String ob_uid) {
                this.ob_uid = ob_uid;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAuid() {
                return auid;
            }

            public void setAuid(int auid) {
                this.auid = auid;
            }

            public static class UidBean {
                /**
                 * nickname : 邓紫琪
                 * head : /public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg
                 */

                private String nickname;
                private String head;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getHead() {
                    return head;
                }

                public void setHead(String head) {
                    this.head = head;
                }
            }
        }
    }
}
