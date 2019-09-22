package com.qixiu.intelligentcommunity.utlis;

import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;


/**
 * Created by Administrator on 2017/4/9 0009.
 */
public class GetGson {


        private static Gson gson;
        public static synchronized  Gson init() {
            if (gson == null) {
                gson = new Gson();
            }
            return gson;
        }

    public  static MessageBean parseMessageBean(String s){
        if(gson==null){
            init();
        }
        if(gson!=null){
            try {
                MessageBean messageBean = gson.fromJson(s, MessageBean.class);
                return messageBean;
            }catch (Exception e){
                return  null;
            }
        }
        return null;
    }

    public  static BaseBean parseBaseBean(String s){
        if(gson==null){
            init();
        }
        if(gson!=null){
            try {
                BaseBean messageBean = gson.fromJson(s, BaseBean.class);
                return messageBean;
            }catch (Exception e){
                return  null;
            }
        }
        return null;
    }
}
