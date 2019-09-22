package com.qixiu.intelligentcommunity.mvp.model.home_modle;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeSelectorModle implements Parcelable {
    public HomeSelectorModle(String text, int drawbleRes) {
        this.text = text;
        this.drawbleRes = drawbleRes;
    }

    private String text;
    private int drawbleRes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDrawbleRes() {
        return drawbleRes;
    }

    public void setDrawbleRes(int drawbleRes) {
        this.drawbleRes = drawbleRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.drawbleRes);
    }

    public HomeSelectorModle() {
    }

    protected HomeSelectorModle(Parcel in) {
        this.text = in.readString();
        this.drawbleRes = in.readInt();
    }

    public static final Parcelable.Creator<HomeSelectorModle> CREATOR = new Parcelable.Creator<HomeSelectorModle>() {
        @Override
        public HomeSelectorModle createFromParcel(Parcel source) {
            return new HomeSelectorModle(source);
        }

        @Override
        public HomeSelectorModle[] newArray(int size) {
            return new HomeSelectorModle[size];
        }
    };
}
