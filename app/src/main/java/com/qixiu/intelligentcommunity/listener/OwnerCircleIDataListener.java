package com.qixiu.intelligentcommunity.listener;

import android.view.View;

import com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean.CommentConfig;

/**
 * Description：
 * Author：XieXianyong
 * Date： 2017/6/24 14:50
 */
public interface OwnerCircleIDataListener {

    /**
     * exeFavort(执行赞和取消赞的操作)
     *
     * @param footprintPosition 点击的足迹位置
     * @param type              0赞 1取消赞
     * @param articleId         足迹id
     * @param articleUserId     足迹发布者id
     */
    public void exeFavort(int footprintPosition, int type, String articleId, String articleUserId);

    /**
     * 显示评论框
     *
     * @param view          点击的view
     * @param commentConfig 评论配置
     */
    public void showCommentDialog(View view, CommentConfig commentConfig);

    /**
     * 删除评论
     *
     * @param config 评论配置
     */
    public void deleteComment(CommentConfig config);

    /**
     * 删除足迹
     *
     * @param footprintPosition 足迹位置
     * @param articleid         足迹Id
     */
    public void deleteFootprint(int footprintPosition, int articleid);
}
