package com.qixiu.intelligentcommunity.mvp.beans.mine;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

/**
 * Created by HuiHeZe on 2017/6/21.
 */

public class NumBean extends BaseBean<NumBean.OBean>{


    public static class OBean {
        /**
         * uid : {"head":"http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0","nickname":"18771112603"}
         * house : 0
         * product : 0
         */

        private UidBean uid;
        private int house;
        private int product;

        public UidBean getUid() {
            return uid;
        }

        public void setUid(UidBean uid) {
            this.uid = uid;
        }

        public int getHouse() {
            return house;
        }

        public void setHouse(int house) {
            this.house = house;
        }

        public int getProduct() {
            return product;
        }

        public void setProduct(int product) {
            this.product = product;
        }

        public static class UidBean {
            /**
             * head : http://wx.qlogo.cn/mmopen/kJZfaibQXtM23o79ybq4z6D1cCeiaRnDFNgb91MzbEgEaBVqIHlOR3KfyzVKlka4yF7E4N2BEICBPF5Ndx4Tm1Ip1U923qwhtl/0
             * nickname : 18771112603
             */

            private String head;
            private String nickname;
            private String auth;
            private String utype;

            public String getAuth() {
                return auth;
            }

            public void setAuth(String auth) {
                this.auth = auth;
            }

            public String getUtype() {
                return utype;
            }

            public void setUtype(String utype) {
                this.utype = utype;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }
    }
}
