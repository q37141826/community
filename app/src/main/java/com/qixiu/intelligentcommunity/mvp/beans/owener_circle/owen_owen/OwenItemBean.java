package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_owen;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/7/6.
 */

public class OwenItemBean {


    /**
     * c : 1
     * m : 查询成功
     * o : [{"id":96,"content":"放寒假加不加","uid":152,"imgs":["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg","/public/upload/20170705/thumb_dc51ee44e3a3d5241a7a0454498467bd.jpg"],"addtime":"22小时前","tb_imgs":["/public/upload/20170705/18f7bdb749e654b182538fb90eb571d5.jpg","/public/upload/20170705/dc51ee44e3a3d5241a7a0454498467bd.jpg"],"uids":{"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"del":1,"zan":1,"comment":[{"id":1262,"uid":{"id":147,"nickname":"张二狗"},"content":"我的评论呢"},{"id":1265,"uid":{"id":152,"nickname":"邓紫琪"},"content":"哥叽叽叽叽"},{"id":1320,"uid":{"id":152,"nickname":"邓紫琪"},"content":"出汗后"},{"id":1333,"uid":{"id":148,"nickname":"毛驴儿"},"content":"苹果哈哈"}],"ppuid":[[{"id":1267,"uid":{"id":152,"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"张二狗","add_time":"2017-07-05 16:32","content":"挂几科快结婚","aid":1262}],[],[],[{"id":1334,"uid":{"id":152,"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"毛驴儿","add_time":"2017-07-06 10:03","content":"大多数电视剧收拾完","aid":1333}]],"zan_name":[{"uid":147,"zan_name":"张二狗"}]}]
     * e :
     */

    private int c;
    private String m;
    private String e;
    private List<OBean> o;

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

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public List<OBean> getO() {
        return o;
    }

    public void setO(List<OBean> o) {
        this.o = o;
    }

    public static class OBean {
        /**
         * id : 96
         * content : 放寒假加不加
         * uid : 152
         * imgs : ["/public/upload/20170705/thumb_18f7bdb749e654b182538fb90eb571d5.jpg","/public/upload/20170705/thumb_dc51ee44e3a3d5241a7a0454498467bd.jpg"]
         * addtime : 22小时前
         * tb_imgs : ["/public/upload/20170705/18f7bdb749e654b182538fb90eb571d5.jpg","/public/upload/20170705/dc51ee44e3a3d5241a7a0454498467bd.jpg"]
         * uids : {"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"}
         * del : 1
         * zan : 1
         * comment : [{"id":1262,"uid":{"id":147,"nickname":"张二狗"},"content":"我的评论呢"},{"id":1265,"uid":{"id":152,"nickname":"邓紫琪"},"content":"哥叽叽叽叽"},{"id":1320,"uid":{"id":152,"nickname":"邓紫琪"},"content":"出汗后"},{"id":1333,"uid":{"id":148,"nickname":"毛驴儿"},"content":"苹果哈哈"}]
         * ppuid : [[{"id":1267,"uid":{"id":152,"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"张二狗","add_time":"2017-07-05 16:32","content":"挂几科快结婚","aid":1262}],[],[],[{"id":1334,"uid":{"id":152,"nickname":"邓紫琪","head":"/public/upload/20170705/631ba944f1ae122c7f7a1f8a4365d5f3.jpg"},"ob_uid":"毛驴儿","add_time":"2017-07-06 10:03","content":"大多数电视剧收拾完","aid":1333}]]
         * zan_name : [{"uid":147,"zan_name":"张二狗"}]
         */

        private int id;
        private String content;
        private int uid;
        private String addtime;
        private UidsBean uids;
        private int del;
        private int zan;
        private List<String> imgs;
        private List<String> tb_imgs;
        private List<OwenCircleAllBean.OBean.ListBean.CommentBean> comment;
        private List<List<OwenCircleAllBean.OBean.ListBean.PpuidBean>> ppuid;
        private List<OwenCircleAllBean.OBean.ListBean.ZanNameBean> zan_name;

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

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
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

        public List<OwenCircleAllBean.OBean.ListBean.CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<OwenCircleAllBean.OBean.ListBean.CommentBean> comment) {
            this.comment = comment;
        }

        public List<List<OwenCircleAllBean.OBean.ListBean.PpuidBean>> getPpuid() {
            return ppuid;
        }

        public void setPpuid(List<List<OwenCircleAllBean.OBean.ListBean.PpuidBean>> ppuid) {
            this.ppuid = ppuid;
        }

        public List<OwenCircleAllBean.OBean.ListBean.ZanNameBean> getZan_name() {
            return zan_name;
        }

        public void setZan_name(List<OwenCircleAllBean.OBean.ListBean.ZanNameBean> zan_name) {
            this.zan_name = zan_name;
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
}
