package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean;

import java.io.Serializable;

/**
 * Description：图片Bean
 * Author：XieXianyong
 * Date： 2017/6/24 16:53
 */
public class OwnerCircleListImageView implements Serializable {
    private int id;
    private String img;
    private String createdAt;
    private String updatedAt;
    private String albumsId;
    private String smallimg;
    private String imgname;


    public OwnerCircleListImageView(int id, String img, String smallimg) {
        this.id = id;
        this.img = img;
        this.smallimg = smallimg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAlbumsId() {
        return albumsId;
    }

    public void setAlbumsId(String albumsId) {
        this.albumsId = albumsId;
    }


    public String getSmallimg() {
        return smallimg;
    }

    public void setSmallimg(String smallimg) {
        this.smallimg = smallimg;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }
}
