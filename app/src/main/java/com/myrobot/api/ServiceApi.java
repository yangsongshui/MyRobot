package com.myrobot.api;


import com.myrobot.bean.Weather;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 描述：retrofit的接口service定义
 */
public interface ServiceApi {
    //天气查询接口
    @POST("109-35?showapi_appid=51436&showapi_sign=925d1f706dca48da876547c5fcf518cc")
    Call<Weather> getNesw(@Query("title") String title);
}
