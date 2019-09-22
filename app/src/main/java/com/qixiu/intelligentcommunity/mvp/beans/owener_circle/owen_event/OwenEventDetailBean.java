package com.qixiu.intelligentcommunity.mvp.beans.owener_circle.owen_event;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class OwenEventDetailBean extends BaseBean<OwenEventDetailBean.OwenEventDetailInfo> {


    /**
     * o : {"id":7,"title":"test3","stime":"2017.06.30","etime":"2017.06.30","address":"test3","uname":"test3","phone":"test3","content":"test3","imgs":["/public/upload/news/2017/06-30/577c8e2aec58b152e626304a93b0efcf.jpg"],"num":8,"addtime":"2017-06-30","state":1,"estate":1,"zan":0,"commet":6,"zan_name":2}
     * e : [{"id":955,"uid":66,"ob_uid":0,"add_time":"2017-06-30 15:34","content":"不行","uids":{"true_name":"张玉凯","head":"/public/upload/20170627/57799ba6fc1727b986149de86072653a.jpg"},"ppuid":[{"id":1006,"uid":{"true_name":"小敏","head":"/public/upload/20170704/e529a34dbe249cb671a95d0a6f7d1ffa.jpg"},"ob_uid":"小花","add_time":"2017-07-04 10:49","content":"骚扰","auid":135},{"id":1013,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:57","content":"还哈","auid":67},{"id":1014,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:57","content":"还能","auid":67},{"id":1016,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 10:58","content":"被","auid":67},{"id":1018,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:59","content":"耳钉定mix地咚你你林俊杰你哦哦那喔哦那喔提他们:-O提那:-O魔连噢你连UNmo里面噢有他摸mo噢噢涂噢:-O噢噢喔哦来她天TT总OK","auid":67},{"id":1078,"uid":{"true_name":"张三","head":"/public/upload/20170628/6abfd3c23f991c6cf1d8fb015ebe1040.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 15:16","content":"？？？？？","auid":1},{"id":1079,"uid":{"true_name":"张三","head":"/public/upload/20170628/6abfd3c23f991c6cf1d8fb015ebe1040.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 15:16","content":"还好吧","auid":1}]},{"id":996,"uid":67,"ob_uid":0,"add_time":"2017-07-04 09:48","content":"哦咯","uids":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ppuid":[]},{"id":998,"uid":67,"ob_uid":0,"add_time":"2017-07-04 09:49","content":"就是","uids":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ppuid":[]},{"id":1017,"uid":67,"ob_uid":0,"add_time":"2017-07-04 10:58","content":"二二OK呀了婆婆哦破楼下送哦破lol预谟rosy二哦lol哦破过敏多咯五","uids":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ppuid":[]}]
     */

    private List<EOwenEventDetailInfo> e;
    
    public List<EOwenEventDetailInfo> getE() {
        return e;
    }

    public void setE(List<EOwenEventDetailInfo> e) {
        this.e = e;
    }

    public static class OwenEventDetailInfo {
        /**
         * id : 7
         * title : test3
         * stime : 2017.06.30
         * etime : 2017.06.30
         * address : test3
         * uname : test3
         * phone : test3
         * content : test3
         * imgs : ["/public/upload/news/2017/06-30/577c8e2aec58b152e626304a93b0efcf.jpg"]
         * num : 8
         * addtime : 2017-06-30
         * state : 1
         * estate : 1
         * zan : 0
         * commet : 6
         * zan_name : 2
         */

        private int id;
        private String title;
        private String stime;
        private String etime;
        private String address;
        private String uname;
        private String phone;
        private String content;
        private int num;
        private String addtime;
        private int state;
        private int estate;
        private int zan;
        private int commet;
        private int zan_name;
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

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getEstate() {
            return estate;
        }

        public void setEstate(int estate) {
            this.estate = estate;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }

        public int getCommet() {
            return commet;
        }

        public void setCommet(int commet) {
            this.commet = commet;
        }

        public int getZan_name() {
            return zan_name;
        }

        public void setZan_name(int zan_name) {
            this.zan_name = zan_name;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }

    public static class EOwenEventDetailInfo {
        /**
         * id : 955
         * uid : 66
         * ob_uid : 0
         * add_time : 2017-06-30 15:34
         * content : 不行
         * uids : {"true_name":"张玉凯","head":"/public/upload/20170627/57799ba6fc1727b986149de86072653a.jpg"}
         * ppuid : [{"id":1006,"uid":{"true_name":"小敏","head":"/public/upload/20170704/e529a34dbe249cb671a95d0a6f7d1ffa.jpg"},"ob_uid":"小花","add_time":"2017-07-04 10:49","content":"骚扰","auid":135},{"id":1013,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:57","content":"还哈","auid":67},{"id":1014,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:57","content":"还能","auid":67},{"id":1016,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 10:58","content":"被","auid":67},{"id":1018,"uid":{"true_name":"小花","head":"/public/upload/20170628/5d12f3330655bbcd5cbfbe44d48c5212.jpg"},"ob_uid":"小敏","add_time":"2017-07-04 10:59","content":"耳钉定mix地咚你你林俊杰你哦哦那喔哦那喔提他们:-O提那:-O魔连噢你连UNmo里面噢有他摸mo噢噢涂噢:-O噢噢喔哦来她天TT总OK","auid":67},{"id":1078,"uid":{"true_name":"张三","head":"/public/upload/20170628/6abfd3c23f991c6cf1d8fb015ebe1040.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 15:16","content":"？？？？？","auid":1},{"id":1079,"uid":{"true_name":"张三","head":"/public/upload/20170628/6abfd3c23f991c6cf1d8fb015ebe1040.jpg"},"ob_uid":"张玉凯","add_time":"2017-07-04 15:16","content":"还好吧","auid":1}]
         */

        private int id;
        private int uid;
        private int ob_uid;
        private String add_time;
        private String content;
        private UidsInfoBean uids;
        private List<PpuidInfoBean> ppuid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getOb_uid() {
            return ob_uid;
        }

        public void setOb_uid(int ob_uid) {
            this.ob_uid = ob_uid;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UidsInfoBean getUids() {
            return uids;
        }

        public void setUids(UidsInfoBean uids) {
            this.uids = uids;
        }

        public List<PpuidInfoBean> getPpuid() {
            return ppuid;
        }

        public void setPpuid(List<PpuidInfoBean> ppuid) {
            this.ppuid = ppuid;
        }

        public static class UidsInfoBean {
            /**
             * true_name : 张玉凯
             * head : /public/upload/20170627/57799ba6fc1727b986149de86072653a.jpg
             */

            private String true_name;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            private String nickname;
            private String head;

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }
        }

        public static class PpuidInfoBean {
            /**
             * id : 1006
             * uid : {"true_name":"小敏","head":"/public/upload/20170704/e529a34dbe249cb671a95d0a6f7d1ffa.jpg"}
             * ob_uid : 小花
             * add_time : 2017-07-04 10:49
             * content : 骚扰
             * auid : 135
             */

            private int id;
            private UidInfoBean uid;
            private String ob_uid;
            private String add_time;
            private String content;
            private int auid;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public UidInfoBean getUid() {
                return uid;
            }

            public void setUid(UidInfoBean uid) {
                this.uid = uid;
            }

            public String getOb_uid() {
                return ob_uid;
            }

            public void setOb_uid(String ob_uid) {
                this.ob_uid = ob_uid;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getAuid() {
                return auid;
            }

            public void setAuid(int auid) {
                this.auid = auid;
            }

            public static class UidInfoBean {
                /**
                 * true_name : 小敏
                 * head : /public/upload/20170704/e529a34dbe249cb671a95d0a6f7d1ffa.jpg
                 */

                private String true_name;
                private String head;
                private String nickname;

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }
                public String getTrue_name() {
                    return true_name;
                }

                public void setTrue_name(String true_name) {
                    this.true_name = true_name;
                }

                public String getHead() {
                    return head;
                }

                public void setHead(String head) {
                    this.head = head;
                }
            }
        }
    }
}
