package com.qixiu.intelligentcommunity.utlis;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class JsonUtil {
    private JsonUtil() {
    }

    private static Gson gson = null;

    private static synchronized Gson getGsonInstance() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 将JSON字符串转换为指定类型对象
     *
     * @param json
     * @param typeOfT
     * @return
     * @throws JsonSyntaxException json语法错误
     */
    public static <T> T fromJson(String json, Type typeOfT)
            throws JsonSyntaxException {
        T t = null;
        try {
            t = getGsonInstance().fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("JsonUtil", "typeOfT : " + typeOfT.getClass());
            LogUtil.d("JsonUtil", "转换时发生异常!" + "源字符串:\n" + json);
        }
        return t;
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return getGsonInstance().toJson(obj);
    }

}