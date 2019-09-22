package com.qixiu.intelligentcommunity.mvp.beans.store;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class AdvBaan extends BaseBean<AdvBaan.AdvInfo> {


    /**
     * o : {"ad":[{"ad_id":"54","ad_name":"自定义广告名称","ad_link":"1","ad_code":"/public/upload/ad/2016/09-12/57d645ec27e00.jpg"},{"ad_id":"56","ad_name":"自定义广告名称","ad_link":"2","ad_code":"/public/upload/ad/2016/09-12/57d64616d745c.jpg"},{"ad_id":"57","ad_name":"自定义广告名称","ad_link":"3","ad_code":"/public/upload/ad/2016/09-12/57d64661cd041.jpg"}]}
     */


    public static class AdvInfo {
        private List<AdvEngineBean> ad;

        public List<AdvEngineBean> getAd() {
            return ad;
        }

        public void setAd(List<AdvEngineBean> ad) {
            this.ad = ad;
        }

        public static class AdvEngineBean {
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
        }
    }
}
