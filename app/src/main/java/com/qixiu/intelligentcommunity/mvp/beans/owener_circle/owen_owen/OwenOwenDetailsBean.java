package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/7/3.
 */

public class OwenOwenDetailsBean {


    /**
     * c : 1
     * m : 查询成功
     * o : {"id":50,"content":"开机","uid":67,"imgs":["/public/upload/20170630/thumb_86e3b6b0bf9056568aac8be0a5528e28.jpg"],"addtime":"2017-06-30 10:21:41","tb_imgs":["/public/upload/20170630/86e3b6b0bf9056568aac8be0a5528e28.jpg"],"del":1,"zan":3,"zan_name":[{"uid":67,"zan_name":"小花"},{"uid":67,"zan_name":"小花"}],"uids":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"}}
     * e : {"comment":[{"id":964,"uid":{"id":67,"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":67,"add_time":"2017-07-03 10:43","content":"都很好VB哈哈"}],"ppuid":[[]]}
     */

    private int c;
    private String m;
    private OBean o;
    private EBean e;

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

    public EBean getE() {
        return e;
    }

    public void setE(EBean e) {
        this.e = e;
    }

    public static class OBean {
        /**
         * id : 50
         * content : 开机
         * uid : 67
         * imgs : ["/public/upload/20170630/thumb_86e3b6b0bf9056568aac8be0a5528e28.jpg"]
         * addtime : 2017-06-30 10:21:41
         * tb_imgs : ["/public/upload/20170630/86e3b6b0bf9056568aac8be0a5528e28.jpg"]
         * del : 1
         * zan : 3
         * zan_name : [{"uid":67,"zan_name":"小花"},{"uid":67,"zan_name":"小花"}]
         * uids : {"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"}
         */

        private int id;
        private String content;
        private int uid;
        private String addtime;
        private int del;
        private int zan;
        private UidsBean uids;
        private List<String> imgs;
        private List<String> tb_imgs;
        private List<ZanNameBean> zan_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public UidsBean getUids() {
            return uids;
        }

        public void setUids(UidsBean uids) {
            this.uids = uids;
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

        public List<ZanNameBean> getZan_name() {
            return zan_name;
        }

        public void setZan_name(List<ZanNameBean> zan_name) {
            this.zan_name = zan_name;
        }

        public static class UidsBean {
            /**
             * true_name : 小花
             * head : /public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg
             */

            private String true_name;
            private String head;
            private String nickname;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }

        public static class ZanNameBean {
            /**
             * uid : 67
             * zan_name : 小花
             */

            private int uid;
            private String zan_name;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getZan_name() {
                return zan_name;
            }

            public void setZan_name(String zan_name) {
                this.zan_name = zan_name;
            }
        }
    }

    public static class EBean {
        private List<CommentBean> comment;
        private List<List<PpuidBean>> ppuid;

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public List<List<PpuidBean>> getPpuid() {
            return ppuid;
        }

        public void setPpuid(List<List<PpuidBean>> ppuid) {
            this.ppuid = ppuid;
        }

        public static class PpuidBean {
            /**
             * aid : 12
             * id : 416
             * uid : {"id":3,"true_name":"小明","head":"http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0"}
             * ob_uid : 张三
             * add_time : 2017-06-12 17:47
             * content : 梅长苏前来回复
             */

            private int aid;
            private int id;
            private UidBean uid;
            private String ob_uid;
            private String add_time;
            private String content;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

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

            public static class UidBean {
                /**
                 * id : 3
                 * true_name : 小明
                 * head : http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0
                 */

                private int id;
                private String true_name;
                private String head;
                private String nickname;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTrue_name() {
                    return true_name;
                }

                public void setTrue_name(String true_name) {
                    this.true_name = true_name;
                }

                public String getHead() {
                    return head;
                }

                public void setHead(String head) {
                    this.head = head;
                }
            }
        }

        public static class CommentBean {
            /**
             * id : 964
             * uid : {"id":67,"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"}
             * ob_uid : 67
             * add_time : 2017-07-03 10:43
             * content : 都很好VB哈哈
             */

            private int id;
            private UidBean uid;
            private int ob_uid;
            private String add_time;
            private String content;

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

            public static class UidBean {
                /**
                 * id : 67
                 * true_name : 小花
                 * head : /public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg
                 */

                private int id;
                private String true_name;
                private String head;
                private String nickname;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTrue_name() {
                    return true_name;
                }

                public void setTrue_name(String true_name) {
                    this.true_name = true_name;
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
