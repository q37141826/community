package com.qixiu.intelligentcommunity.mvp.beans.store;


import android.os.Parcel;
import android.os.Parcelable;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

public class StoreClassItemBean extends BaseBean<StoreClassItemBean.StoreClassItemInfo> {


    /**
     * o : {"cate":[{"id":"1","name":"语言识字"},{"id":"2","name":"智力开发"},{"id":"3","name":"身体运动"},{"id":"4","name":"科学探索"},{"id":"5","name":"生活礼仪"},{"id":"7","name":"音乐律动"},{"id":"8","name":"美术创意"},{"id":"9","name":"吃喝玩乐"}]}
     */
    public static class StoreClassItemInfo {
        private List<ClassifyItemBean> cate;

        public List<ClassifyItemBean> getCate() {
            return cate;
        }

        public void setCate(List<ClassifyItemBean> cate) {
            this.cate = cate;
        }

        public static class ClassifyItemBean implements Parcelable {
            /**
             * id : 1
             * name : 语言识字
             */
            private int resourceId;
            private  boolean isSelected=false;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            public int getResourceId() {
                return resourceId;
            }

            public void setResourceId(int resourceId) {
                this.resourceId = resourceId;
            }

            private String id;
            private String name;
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.resourceId);
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.image);
            }

            public ClassifyItemBean() {
            }

            protected ClassifyItemBean(Parcel in) {
                this.resourceId = in.readInt();
                this.id = in.readString();
                this.name = in.readString();
                this.image = in.readString();
            }

            public static final Parcelable.Creator<ClassifyItemBean> CREATOR = new Parcelable.Creator<ClassifyItemBean>() {
                @Override
                public ClassifyItemBean createFromParcel(Parcel source) {
                    return new ClassifyItemBean(source);
                }

                @Override
                public ClassifyItemBean[] newArray(int size) {
                    return new ClassifyItemBean[size];
                }
            };
        }
    }
}
