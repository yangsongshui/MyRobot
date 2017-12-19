package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myrobot.R;
import com.myrobot.api.User;
import com.myrobot.base.BaseActivity;
import com.myrobot.utils.MD5;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.myrobot.utils.Constant.JSON;

public class MainActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    OkHttpClient client;
    Gson gs;
    private ProgressDialog progressDialog;
    Handler handler;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        client = new OkHttpClient();
        gs = new Gson();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {


               // showToastor(msg.getData().getString("msg"));


                return false;
            }
        });
    }

    @OnClick({R.id.login_bt, R.id.zhuche_bt, R.id.main_back})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.login_bt:
                //startActivity(new Intent(MainActivity.this, HomeActivity.class));
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                String phone = phoneEt.getText().toString();
                String psw = pswEt.getText().toString();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone);
                jsonObject.addProperty("password", MD5.getMD5(psw));
                post("http://112.74.196.237:81/robot_api/public/index.php/users/login?", jsonObject.toString());

                break;
            case R.id.zhuche_bt:

                startActivity(new Intent(MainActivity.this, ZhuCeActivity.class));
                break;
            case R.id.main_back:
                finish();
                break;
            default:
                break;

        }
    }

    public void post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();
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
                User user = gs.fromJson(js, User.class);//把JSON字符串转为对象
          /*      Message msg = new Message();
                Bundle b = new Bundle();// 存放数据*/
                if (user != null) {
                 /*   b.putString("msg", user.getMsg());
                    msg.setData(b);
                    handler.sendMessage(msg);*/
                    if (user.getCode() == 1) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }
                } else {
                  /*  b.putString("msg", "数据获取失败");
                    msg.setData(b);*/
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

    }
}
