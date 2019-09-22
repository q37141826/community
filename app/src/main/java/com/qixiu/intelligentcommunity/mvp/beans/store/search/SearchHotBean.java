package com.qixiu.intelligentcommunity.mvp.beans.store.search;


import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class SearchHotBean extends BaseBean<SearchHotBean.SearchHotInfo> {


    /**
     * o : {"keywords":["手机","小米","iphone","三星","华为","冰箱"]}
     */


    public static class SearchHotInfo {
        private List<String> keywords;

        public List<String> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<String> keywords) {
            this.keywords = keywords;
        }
    }
}
