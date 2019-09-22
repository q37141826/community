package com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class JsInterfaceInfo implements Parcelable {
    /**
     * 当前界面title
     */
    private String title;
    /**
     * 下个界面tilte
     */
    private String subTitle;
    /**
     * 下个界面url
     */
    private String subUrl;

    /**
     * 查看大图列表
     */

    private String imgs;
    private String subId;
    private String name;
    private String phone;
    private String sendUrl;
    private String ob_uid;
    private String pid;

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl;
    }

    public String getOb_uid() {
        return ob_uid;
    }

    public void setOb_uid(String ob_uid) {
        this.ob_uid = ob_uid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }


    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    /**
     * 选择图片的对应角标
     */
    private String index;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(String subUrl) {
        this.subUrl = subUrl;
    }


    public JsInterfaceInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.subUrl);
        dest.writeString(this.imgs);
        dest.writeString(this.subId);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.sendUrl);
        dest.writeString(this.ob_uid);
        dest.writeString(this.pid);
        dest.writeString(this.index);
    }

    protected JsInterfaceInfo(Parcel in) {
        this.title = in.readString();
        this.subTitle = in.readString();
        this.subUrl = in.readString();
        this.imgs = in.readString();
        this.subId = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.sendUrl = in.readString();
        this.ob_uid = in.readString();
        this.pid = in.readString();
        this.index = in.readString();
    }

    public static final Creator<JsInterfaceInfo> CREATOR = new Creator<JsInterfaceInfo>() {
        @Override
        public JsInterfaceInfo createFromParcel(Parcel source) {
            return new JsInterfaceInfo(source);
        }

        @Override
        public JsInterfaceInfo[] newArray(int size) {
            return new JsInterfaceInfo[size];
        }
    };
}
