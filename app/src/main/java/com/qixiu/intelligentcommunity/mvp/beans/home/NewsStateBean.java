package com.qixiu.intelligentcommunity.mvp.beans.home;

/**
 * Created by HuiHeZe on 2017/7/5.
 */

public class NewsStateBean {


    /**
     * c : 1
     * m : 查询成功
     * o : {"new_type":1,"post_type":1}
     * e :
     */

    private int c;
    private String m;
    private OBean o;
    private String e;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public OBean getO() {
        return o;
    }

    public void setO(OBean o) {
        this.o = o;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public static class OBean {
        /**
         * new_type : 1
         * post_type : 1
         */

        private int new_type;
        private int post_type;

        public int getNew_type() {
            return new_type;
        }

        public void setNew_type(int new_type) {
            this.new_type = new_type;
        }

        public int getPost_type() {
            return post_type;
        }

        public void setPost_type(int post_type) {
            this.post_type = post_type;
        }
    }
}
