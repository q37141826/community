package com.qixiu.intelligentcommunity.mvp.beans.store;


import android.os.Parcel;
import android.os.Parcelable;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.impl.GoodsListImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class StoreBean extends BaseBean<StoreBean.StoreInfo> {


    /**
     * o : {"ad":[{"ad_id":"54","ad_name":"自定义广告名称","ad_link":"1","ad_code":"/public/upload/ad/2016/09-12/57d645ec27e00.jpg"},{"ad_id":"56","ad_name":"自定义广告名称","ad_link":"2","ad_code":"/public/upload/ad/2016/09-12/57d64616d745c.jpg"},{"ad_id":"57","ad_name":"自定义广告名称","ad_link":"3","ad_code":"/public/upload/ad/2016/09-12/57d64661cd041.jpg"}],"cate":[{"id":"1","name":"语言识字"},{"id":"2","name":"智力开发"},{"id":"3","name":"身体运动"},{"id":"4","name":"科学探索"},{"id":"5","name":"生活礼仪"},{"id":"7","name":"音乐律动"},{"id":"8","name":"美术创意"},{"id":"9","name":"吃喝玩乐"}],"goods":{"list":[{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}],"page":18}}
     */


    public static class StoreInfo implements Parcelable {
        /**
         * ad : [{"ad_id":"54","ad_name":"自定义广告名称","ad_link":"1","ad_code":"/public/upload/ad/2016/09-12/57d645ec27e00.jpg"},{"ad_id":"56","ad_name":"自定义广告名称","ad_link":"2","ad_code":"/public/upload/ad/2016/09-12/57d64616d745c.jpg"},{"ad_id":"57","ad_name":"自定义广告名称","ad_link":"3","ad_code":"/public/upload/ad/2016/09-12/57d64661cd041.jpg"}]
         * cate : [{"id":"1","name":"语言识字"},{"id":"2","name":"智力开发"},{"id":"3","name":"身体运动"},{"id":"4","name":"科学探索"},{"id":"5","name":"生活礼仪"},{"id":"7","name":"音乐律动"},{"id":"8","name":"美术创意"},{"id":"9","name":"吃喝玩乐"}]
         * goods : {"list":[{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}],"page":18}
         */

        private GoodsBean goods;
        private List<AdvBean> ad;
        private List<ClassifyItemBean> cate;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public List<AdvBean> getAd() {
            return ad;
        }

        public void setAd(List<AdvBean> ad) {
            this.ad = ad;
        }

        public List<ClassifyItemBean> getCate() {
            return cate;
        }

        public void setCate(List<ClassifyItemBean> cate) {
            this.cate = cate;
        }

        public static class GoodsBean implements Parcelable {
            /**
             * list : [{"goods_id":"65","goods_name":"长虹(CHANG","shop_price":"2799.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg"},{"goods_id":"66","goods_name":"迎馨家纺全棉斜纹","shop_price":"110.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/66/goods_thumb_66_305_305.jpeg"},{"goods_id":"39","goods_name":"华为（HUAWE","shop_price":"2288.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/39/goods_thumb_39_305_305.jpeg"},{"goods_id":"40","goods_name":"荣耀X2 标准版","shop_price":"1999.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/40/goods_thumb_40_305_305.jpeg"},{"goods_id":"41","goods_name":"华为（HUAWE","shop_price":"1588.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/41/goods_thumb_41_305_305.jpeg"},{"goods_id":"42","goods_name":"Teclast/","shop_price":"499.00","goods_remark":"今日起商城搞活动,注册立马送3","thumb_url":"/shop/public/upload/goods/thumb/42/goods_thumb_42_305_305.jpeg"}]
             * page : 18
             */

            private int page;
            private List<GoodsListBean> list;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public List<GoodsListBean> getList() {
                return list;
            }

            public void setList(List<GoodsListBean> list) {
                this.list = list;
            }

            public static class GoodsListBean implements GoodsListImpl, Parcelable {
                /**
                 * goods_id : 65
                 * goods_name : 长虹(CHANG
                 * shop_price : 2799.00
                 * goods_remark : 今日起商城搞活动,注册立马送3
                 * thumb_url : /shop/public/upload/goods/thumb/65/goods_thumb_65_305_305.jpeg
                 */

                private String goods_id;
                private String goods_name;
                private String shop_price;
                private String goods_remark;
                private String thumb_url;

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getShop_price() {
                    return shop_price;
                }

                public void setShop_price(String shop_price) {
                    this.shop_price = shop_price;
                }

                public String getGoods_remark() {
                    return goods_remark;
                }

                public void setGoods_remark(String goods_remark) {
                    this.goods_remark = goods_remark;
                }

                public String getThumb_url() {
                    return thumb_url;
                }

                public void setThumb_url(String thumb_url) {
                    this.thumb_url = thumb_url;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.goods_id);
                    dest.writeString(this.goods_name);
                    dest.writeString(this.shop_price);
                    dest.writeString(this.goods_remark);
                    dest.writeString(this.thumb_url);
                }

                public GoodsListBean() {
                }

                protected GoodsListBean(Parcel in) {
                    this.goods_id = in.readString();
                    this.goods_name = in.readString();
                    this.shop_price = in.readString();
                    this.goods_remark = in.readString();
                    this.thumb_url = in.readString();
                }

                public static final Creator<GoodsListBean> CREATOR = new Creator<GoodsListBean>() {
                    @Override
                    public GoodsListBean createFromParcel(Parcel source) {
                        return new GoodsListBean(source);
                    }

                    @Override
                    public GoodsListBean[] newArray(int size) {
                        return new GoodsListBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.page);
                dest.writeList(this.list);
            }

            public GoodsBean() {
            }

            protected GoodsBean(Parcel in) {
                this.page = in.readInt();
                this.list = new ArrayList<GoodsListBean>();
                in.readList(this.list, GoodsListBean.class.getClassLoader());
            }

            public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
                @Override
                public GoodsBean createFromParcel(Parcel source) {
                    return new GoodsBean(source);
                }

                @Override
                public GoodsBean[] newArray(int size) {
                    return new GoodsBean[size];
                }
            };
        }

        public static class AdvBean implements Parcelable {
            /**
             * ad_id : 54
             * ad_name : 自定义广告名称
             * ad_link : 1
             * ad_code : /public/upload/ad/2016/09-12/57d645ec27e00.jpg
             */

            private String ad_id;
            private String ad_name;
            private String ad_link;
            private String ad_code;

            public String getAd_id() {
                return ad_id;
            }

            public void setAd_id(String ad_id) {
                this.ad_id = ad_id;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public String getAd_link() {
                return ad_link;
            }

            public void setAd_link(String ad_link) {
                this.ad_link = ad_link;
            }

            public String getAd_code() {
                return ad_code;
            }

            public void setAd_code(String ad_code) {
                this.ad_code = ad_code;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.ad_id);
                dest.writeString(this.ad_name);
                dest.writeString(this.ad_link);
                dest.writeString(this.ad_code);
            }

            public AdvBean() {
            }

            protected AdvBean(Parcel in) {
                this.ad_id = in.readString();
                this.ad_name = in.readString();
                this.ad_link = in.readString();
                this.ad_code = in.readString();
            }

            public static final Creator<AdvBean> CREATOR = new Creator<AdvBean>() {
                @Override
                public AdvBean createFromParcel(Parcel source) {
                    return new AdvBean(source);
                }

                @Override
                public AdvBean[] newArray(int size) {
                    return new AdvBean[size];
                }
            };
        }

        public static class ClassifyItemBean implements ClassifyIntef, Parcelable {
            /**
             * id : 1
             * name : 语言识字
             */

            private String id;
            private String name;
            private String image;
            private boolean isSelected = false;

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }


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

            public ClassifyItemBean() {
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.id);
                dest.writeString(this.name);
                dest.writeString(this.image);
                dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
            }

            protected ClassifyItemBean(Parcel in) {
                this.id = in.readString();
                this.name = in.readString();
                this.image = in.readString();
                this.isSelected = in.readByte() != 0;
            }

            public static final Creator<ClassifyItemBean> CREATOR = new Creator<ClassifyItemBean>() {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.goods, flags);
            dest.writeList(this.ad);
            dest.writeTypedList(this.cate);
        }

        public StoreInfo() {
        }

        protected StoreInfo(Parcel in) {
            this.goods = in.readParcelable(GoodsBean.class.getClassLoader());
            this.ad = new ArrayList<AdvBean>();
            in.readList(this.ad, AdvBean.class.getClassLoader());
            this.cate = in.createTypedArrayList(ClassifyItemBean.CREATOR);
        }

        public static final Parcelable.Creator<StoreInfo> CREATOR = new Parcelable.Creator<StoreInfo>() {
            @Override
            public StoreInfo createFromParcel(Parcel source) {
                return new StoreInfo(source);
            }

            @Override
            public StoreInfo[] newArray(int size) {
                return new StoreInfo[size];
            }
        };
    }
}
