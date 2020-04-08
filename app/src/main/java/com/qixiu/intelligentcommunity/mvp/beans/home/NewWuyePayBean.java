package com.qixiu.intelligentcommunity.mvp.beans.home;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

public class NewWuyePayBean  extends BaseBean<NewWuyePayBean.OBean> {

//    {
//        "c": 1,
//            "m": "查询成功",
//            "o": {
//        "uid": "1574",
//                "inter": "0.00",//用户积分
//                "last_wuye_endtime": 1585843200,//上次缴费截至时间戳
//                "endtime_desc": "2020年04月03日",//上次缴费截至时间描述
//                "bprice": "1.20",//物业单价 元
//                "barea": "128.51",//物业面积 平方米
//                "score_to_money": 10 //积分抵扣金额   10个积分抵扣1元
//    },
//        "e": ""
//    }



    public static class OBean {
        /**
         * uid : 671
         * inter : 0.00
         * last_wuye_endtime : 1586102400
         * endtime_desc : 2020年04月06日
         * bprice : 0.01
         * barea : 1.00
         * yearprice : 0.12
         * score_to_money : 10
         */

        private String uid;
        private String inter;
        private int last_wuye_endtime;
        private String endtime_desc;
        private String bprice;
        private String barea;
        private double yearprice;
        private int score_to_money;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getInter() {
            return inter;
        }

        public void setInter(String inter) {
            this.inter = inter;
        }

        public int getLast_wuye_endtime() {
            return last_wuye_endtime;
        }

        public void setLast_wuye_endtime(int last_wuye_endtime) {
            this.last_wuye_endtime = last_wuye_endtime;
        }

        public String getEndtime_desc() {
            return endtime_desc;
        }

        public void setEndtime_desc(String endtime_desc) {
            this.endtime_desc = endtime_desc;
        }

        public String getBprice() {
            return bprice;
        }

        public void setBprice(String bprice) {
            this.bprice = bprice;
        }

        public String getBarea() {
            return barea;
        }

        public void setBarea(String barea) {
            this.barea = barea;
        }

        public double getYearprice() {
            return yearprice;
        }

        public void setYearprice(double yearprice) {
            this.yearprice = yearprice;
        }

        public int getScore_to_money() {
            return score_to_money;
        }

        public void setScore_to_money(int score_to_money) {
            this.score_to_money = score_to_money;
        }
    }
}
