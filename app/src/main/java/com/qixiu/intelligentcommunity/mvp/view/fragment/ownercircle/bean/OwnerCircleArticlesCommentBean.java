package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean;

import java.io.Serializable;

/**
 * Description：评论bean
 * Author：XieXianyong
 * Date： 2017/6/24 10:28
 */
public class OwnerCircleArticlesCommentBean implements Serializable {
    private String commentId;
    private String articleId;
    private String articleUserId;
    private String content;
    private String status;
    private long createdAt;
    private String updatedAt;
    private String parentId;
    private String replyId;
    private String authorId;
    private String commentNice;
    private String commentImg;
    private String replyNice;
    private String replyImg;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleUserId() {
        return articleUserId;
    }

    public void setArticleUserId(String articleUserId) {
        this.articleUserId = articleUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getCommentNice() {
        return commentNice;
    }

    public void setCommentNice(String commentNice) {
        this.commentNice = commentNice;
    }

    public String getCommentImg() {
        return commentImg;
    }

    public void setCommentImg(String commentImg) {
        this.commentImg = commentImg;
    }

    public String getReplyNice() {
        return replyNice;
    }

    public void setReplyNice(String replyNice) {
        this.replyNice = replyNice;
    }

    public String getReplyImg() {
        return replyImg;
    }

    public void setReplyImg(String replyImg) {
        this.replyImg = replyImg;
    }
}
