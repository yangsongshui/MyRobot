package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myrobot.R;
import com.myrobot.api.Page;
import com.myrobot.api.ServiceApi;
import com.myrobot.app.MyApplication;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.News;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.myrobot.utils.Constant.NESW_URL;

public class QiYeActivity extends BaseActivity implements retrofit2.Callback<News> {

    @BindView(R.id.msg_1)
    TextView msg1;
    @BindView(R.id.msg_2)
    TextView msg2;
    @BindView(R.id.msg_3)
    TextView msg3;
    @BindView(R.id.msg_4)
    TextView msg4;
    @BindView(R.id.msg_5)
    TextView msg5;
    @BindView(R.id.msg_6)
    TextView msg6;
    @BindView(R.id.msg_et)
    EditText msg_et;
    OkHttpClient client;
    Gson gs;
    Handler handler;
    ServiceApi service;
    Retrofit retrofit;
    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_qi_ye;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NESW_URL)
                .build();
        progressDialog.show();
        service = retrofit.create(ServiceApi.class);
        retrofit2.Call<News> call = service.getNesw("企业", "1");
        call.enqueue(this);

        gs = new Gson();
        client = MyApplication.newInstance().mOkHttpClient;
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                showToastor(msg.getData().getString("msg"));
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                return false;
            }
        });

        postPage("http://112.74.196.237:81/robot_api/public/index.php/api/3/files?key=");
    }

    @OnClick({R.id.bt_1, R.id.shousuo_bt,R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shousuo_bt:
               if (!msg_et.getText().toString().equals("")) {
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://news.baidu.com/ns?word="+msg_et.getText().toString()));
                }
                break;
            case R.id.bt_1:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(0).getLink()));
                break;
            case R.id.bt_2:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(1).getLink()));
                break;
            case R.id.bt_3:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(2).getLink()));
                break;
            case R.id.bt_4:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(3).getLink()));
                break;
            case R.id.bt_5:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(4).getLink()));
                break;
            case R.id.bt_6:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(5).getLink()));
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
            default:
                break;
        }
    }

    Page page;

    private void postPage(String url) {
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("msg", "网络异常");
                msg.setData(b);
                handler.sendMessage(msg);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Page page = gs.fromJson(js, Page.class);//把JSON字符串转为对象
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("msg", page.getMsg());
                msg.setData(b);
                handler.sendMessage(msg);


            }
        });
    }

    @Override
    public void onResponse(retrofit2.Call<News> call, retrofit2.Response<News> response) {
        News news = response.body();

        if (news.getShowapi_res_code() == 0) {
            showToastor("查询成功");
            initView(news.getShowapi_res_body().getPagebean());

        } else {
            showToastor("查询失败");

        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(retrofit2.Call<News> call, Throwable t) {
        //请求失败操作
        progressDialog.dismiss();
        showToastor("网络异常");
        Log.e("错误", t.getMessage().toString());
    }

    News.ShowapiResBodyBean.PagebeanBean pagebeanBean;

    private void initView(News.ShowapiResBodyBean.PagebeanBean pagebeanBean) {
        Log.e("JSON", pagebeanBean.toString());
        this.pagebeanBean = pagebeanBean;
        msg1.setText(pagebeanBean.getContentlist().get(0).getTitle());
        msg2.setText(pagebeanBean.getContentlist().get(1).getTitle());
        msg3.setText(pagebeanBean.getContentlist().get(2).getTitle());
        msg4.setText(pagebeanBean.getContentlist().get(3).getTitle());
        msg5.setText(pagebeanBean.getContentlist().get(4).getTitle());
        msg6.setText(pagebeanBean.getContentlist().get(5).getTitle());

    }
}
