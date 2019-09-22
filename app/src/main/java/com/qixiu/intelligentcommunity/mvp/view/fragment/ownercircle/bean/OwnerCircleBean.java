package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Description：业主圈Bean
 * Author：XieXianyong
 * Date： 2017/6/28 13:57
 */
public class OwnerCircleBean extends BaseBean<OwnerCircleBean.OOwnerCircleBean> {
    public class OOwnerCircleBean {
        private List<OwnerCircleEntity> list;
        private int page;

        public List<OwnerCircleEntity> getList() {
            return list;
        }

        public void setList(List<OwnerCircleEntity> list) {
            this.list = list;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public class OwnerCircleEntity {
            private int id;
            private String content;
            private int uid;
            private String[] imgs;//发布的时间
            private String addtime;
            private PublisherEntity uids;//发布者信息
            private int del;//是否有删除功能（1.有删除 2.没有删除）
            private int zan;//是否有点赞功能（1.有 2.没有 3.已点赞）
            private List<CommentInfoEnity> comment;//评论
            private List<FabulousInfoEntity> zan_name;//点赞

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

            public String[] getImgs() {
                return imgs;
            }

            public void setImgs(String[] imgs) {
                this.imgs = imgs;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public PublisherEntity getUids() {
                return uids;
            }

            public void setUids(PublisherEntity uids) {
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

            public List<CommentInfoEnity> getComment() {
                return comment;
            }

            public void setComment(List<CommentInfoEnity> comment) {
                this.comment = comment;
            }

            public List<FabulousInfoEntity> getZan_name() {
                return zan_name;
            }

            public void setZan_name(List<FabulousInfoEntity> zan_name) {
                this.zan_name = zan_name;
            }
        }

        //发布信息
        public class PublisherEntity {
            private String true_name;
            private String head;

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

        //评论信息
        public class CommentInfoEnity {

        }

        //点赞用户
        public class FabulousInfoEntity {

        }
    }
}
