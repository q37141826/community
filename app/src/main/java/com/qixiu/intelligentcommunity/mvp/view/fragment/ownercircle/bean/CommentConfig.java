package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean;

/**
 * Author XieXianyong
 * Date: 2016.12.14
 * Description:
 */

public class CommentConfig {
    public static enum Type {
        PUBLIC("public"), REPLY("reply");
        private String value;

        private Type(String value) {
            this.value = value;
        }

    }

    public int circlePosition;
    public int commentPosition;
    public String replyId;
    public String replyNickName;
    public String parentId;
    public Type commentType;
    public String content;

}
