package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/19.
 */

public class HomeBean  extends BaseBean<List<HomeBean.OBean>> {


    /**
     * c : 1
     * m : 查询成功
     * o : [{"ad_id":62,"ad_code":"http://p0.ifengimg.com/a/2017_23/7f002bc4b409ef7_size83_w530_h298.jpg","ad_link":""},{"ad_id":63,"ad_code":"http://p0.ifengimg.com/a/2017_23/7f002bc4b409ef7_size83_w530_h298.jpg","ad_link":""},{"ad_id":64,"ad_code":"http://p0.ifengimg.com/a/2017_23/7f002bc4b409ef7_size83_w530_h298.jpg","ad_link":""}]
     * e : [{"id":3,"title":"test数据2","imgs":["http://p0.ifengimg.com/a/2017_23/7f002bc4b409ef7_size83_w530_h298.jpg"],"state":1}]
     */

    private List<EBean> e;




    public List<EBean> getE() {
        return e;
    }

    public void setE(List<EBean> e) {
        this.e = e;
    }

    public  class OBean {
        /**
         * ad_id : 79
         * ad_code : /public/upload/ad/2018/10-22/e4fbde9141b8d40a46a2ab1a6c94d4c3.png
         * ad_link : www.xd318.com
         */

        private int ad_id;
        private String ad_code;
        private String ad_link;

        public int getAd_id() {
            return ad_id;
        }

        public void setAd_id(int ad_id) {
            this.ad_id = ad_id;
        }

        public String getAd_code() {
            return ad_code;
        }

        public void setAd_code(String ad_code) {
            this.ad_code = ad_code;
        }

        public String getAd_link() {
            return ad_link;
        }

        public void setAd_link(String ad_link) {
            this.ad_link = ad_link;
        }
    }

    public static class EBean {
        /**
         * id : 3
         * title : test数据2
         * imgs : ["http://p0.ifengimg.com/a/2017_23/7f002bc4b409ef7_size83_w530_h298.jpg"]
         * state : 1
         */

        private int id;
        private String title;

        public String getAddtime_desc() {
            return addtime_desc;
        }

        public void setAddtime_desc(String addtime_desc) {
            this.addtime_desc = addtime_desc;
        }

        private String addtime_desc;
        private int state;
        private List<String> imgs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }



}
