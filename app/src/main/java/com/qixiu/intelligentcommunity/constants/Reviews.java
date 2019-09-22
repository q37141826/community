package com.qixiu.intelligentcommunity.ConstantUrls;



import com.qixiu.intelligentcommunity.mvp.beans.login.LoginBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Reviews {


    //登陆接口1
    @POST("User/login")
    Call<LoginBean> loginUser(
            @Query("token") String apptoken,
            @Query("phone") String phone,
            @Query("pswd") String pswd,
            @Query("device") String device,
            @Query("type") int type
    );



}