package com.qixiu.intelligentcommunity.mvp.beans.login;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/6/19.
 */

public class SendCodeBean extends BaseBean<SendCodeBean.OBean>{


    /**
     * c : 1
     * m : 发送成功
     * o : {"verify_id":"15"}
     * e :
     */


    public static class OBean {
        /**
         * verify_id : 15
         */

        private String verify_id;

        public String getVerify_id() {
            return verify_id;
        }

        public void setVerify_id(String verify_id) {
            this.verify_id = verify_id;
        }
    }
}
