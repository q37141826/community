package com.qixiu.intelligentcommunity.mvp.view.fragment.ownercircle.bean;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Description：业主圈-我的
 * Author：XieXianyong
 * Date： 2017/6/28 20:31
 */
public class OwnerCicleMyBean extends BaseBean<OwnerCicleMyBean.OOwnerCircleMyBean> {
    public class OOwnerCircleMyBean {
        private List<OwnerCicleMyList> list;
        private int page;

        public List<OwnerCicleMyList> getList() {
            return list;
        }

        public void setList(List<OwnerCicleMyList> list) {
            this.list = list;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }

    public class OwnerCicleMyList {
        private String time;
        private List<OwnerCicleMyDataBean> data;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<OwnerCicleMyDataBean> getData() {
            return data;
        }

        public void setData(List<OwnerCicleMyDataBean> data) {
            this.data = data;
        }
    }

    public class OwnerCicleMyDataBean {
        private int id;
        private String addtime;
        private String[] imgs;
        private String content;
        private String addtimes;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String[] getImgs() {
            return imgs;
        }

        public void setImgs(String[] imgs) {
            this.imgs = imgs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAddtimes() {
            return addtimes;
        }

        public void setAddtimes(String addtimes) {
            this.addtimes = addtimes;
        }
    }
}
