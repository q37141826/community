package com.qixiu.intelligentcommunity.mvp.beans;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/26.
 */

public class ServiceBean extends BaseBean<List<ServiceBean.OBean>>{
//
    /**
     * c : 1
     * m : 查询成功
     * o : [{"title":"搬家服务","logo":"12345678.jpg","url":"www.baidu.com"},{"title":"保姆","logo":"456789.jpg","url":"www.baidu.com"}]
     * e :
     */


    public static class OBean {
        /**
         * title : 搬家服务
         * logo : 12345678.jpg
         * url : www.baidu.com
         */

        private String title;
        private String logo;
        private String url;
        private String type;
        private String text;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
