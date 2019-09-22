package com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect;

import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;

import java.util.List;

/**
 * Created by HuiHeZe on 2017/6/20.
 */

public class MyPopBean extends BaseBean<MyPopBean.Obean>{


    public static class Obean{
        List<ListBean> list;
        public List<ListBean> getString(){
            return list;
        }
        public void setList(List<ListBean> list){
           this.list=list;
        }
        public static class ListBean{
            String sss;
            public String getSss(){
              return sss;
            }
            public  void setSss(String sss){
                this.sss=sss;
            }
        }

    }
}
