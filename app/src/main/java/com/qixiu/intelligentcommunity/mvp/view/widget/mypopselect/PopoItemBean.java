package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class PopoItemBean implements Parcelable {
    private String text;
    private boolean isSelect=false;
    private  boolean isLast=false;
    private int backResource=0;
    private String id;
    private String dataId;
    private String pinyin;
    private String district;

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBackResource() {
        return backResource;
    }

    public void setBackResource(int backResource) {
        this.backResource = backResource;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public @DrawableRes int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @DrawableRes
    private int icon;

    private String images;

    public PopoItemBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.icon);
        dest.writeString(this.images);
    }

    protected PopoItemBean(Parcel in) {
        this.text = in.readString();
        this.isSelect = in.readByte() != 0;
        this.icon = in.readInt();
        this.images = in.readString();
    }

    public static final Creator<PopoItemBean> CREATOR = new Creator<PopoItemBean>() {
        @Override
        public PopoItemBean createFromParcel(Parcel source) {
            return new PopoItemBean(source);
        }

        @Override
        public PopoItemBean[] newArray(int size) {
            return new PopoItemBean[size];
        }
    };
}
