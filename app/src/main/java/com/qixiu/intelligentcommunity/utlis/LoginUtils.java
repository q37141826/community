package com.qixiu.intelligentcommunity.utlis;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.mvp.beans.login.LoginBean;

public class LoginUtils {
    private static final String LOGIN_DATA = "login_data";

    private LoginUtils() {
    }

    public static boolean isLogined() {
        return !TextUtils.isEmpty((Preference.get(ConstantString.USERID, "")));
    }

    public static LoginBean getLoginData() {
        Gson gson = new Gson();
        String loginData = Preference.get(LOGIN_DATA, "");
        if (TextUtils.isEmpty(loginData)) {
            return null;
        }
        LoginBean bean = gson.fromJson(loginData, LoginBean.class);
        return bean;
    }

    public static boolean saveLoginData(String data) {
        Preference.put(LOGIN_DATA, data);
        return true;
    }

    public static String getLoginId(){
        return Preference.get(ConstantString.USERID,"");
    }
}
