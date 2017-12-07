package com.myrobot.activity;

import android.app.ProgressDialog;

import com.myrobot.R;
import com.myrobot.api.ServiceApi;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.News;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.myrobot.utils.Constant.NESW_URL;

public class ShangWuActivity extends BaseActivity {
    Retrofit retrofit;
    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_shang_wu;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NESW_URL)
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<News> call = service.getNesw("机器人");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //请求成功操作
                News news = response.body();
                if (news.getShowapi_res_code() == 0) {

                } else {
                    showToastor("天气查询失败");
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //请求失败操作
                progressDialog.dismiss();
                showToastor("天气查询失败");
            }
        });
    }
}
