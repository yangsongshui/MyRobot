package com.myrobot.api;


import com.myrobot.bean.News;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 描述：retrofit的接口service定义
 */
public interface ServiceApi {
    //新闻搜索
    @POST("109-35?showapi_appid=51436&showapi_sign=925d1f706dca48da876547c5fcf518cc")
    Call<News> getNesw(@Query("title") String title,@Query("page") String page);

    //登陆
    @POST("login?")
    Observable<User> loadLoginInfo(@Query("phone") String phoneNumber, @Query("password") String psw);
}
