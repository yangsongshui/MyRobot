package com.myrobot.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myrobot.R;
import com.myrobot.adapter.ZuAdapter;
import com.myrobot.api.User;
import com.myrobot.app.MyApplication;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.Zu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.myrobot.utils.Constant.JSON;

public class GuanLiYuanActivity extends BaseActivity {
    ViewPager pager;
    OkHttpClient client;
    Gson gs;
    private ProgressDialog progressDialog;
    Handler handler;
    String url = "";
    ZuAdapter adapter;
    EditText yonghu_et, zu_et;
    User user;
    int position = 0;
    List<Zu.DataBean> news = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_guan_li_yuan;
    }

    @Override
    protected void init() {
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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        client = new OkHttpClient();
        gs = new Gson();
        pager = findViewById(R.id.pager);
        yonghu_et = findViewById(R.id.yonghu_et);
        zu_et = findViewById(R.id.zu_et);

        adapter = new ZuAdapter(getSupportFragmentManager(), news);
        pager.setAdapter(adapter);
        user = MyApplication.newInstance().getUser();
        if (user != null) {
            if (user.getData().getParent_id() > 0)
                url = "http://112.74.196.237:81/robot_api/public/index.php/api/" + user.getData().getId() + "/departments/manager/" + user.getData().getParent_id() + "/users";
            else
                url = "http://112.74.196.237:81/robot_api/public/index.php/api/" + user.getData().getId() + "/departments/manager/" + user.getData().getId() + "/users";
            progressDialog.show();
            getUser(url);
        }

        adapter.setCount(news);
        adapter.notifyDataSetChanged();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                GuanLiYuanActivity.this.position = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @OnClick({R.id.fenzu, R.id.zizhanghao, R.id.mima_queren, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        JsonObject jsonObject;
        String url;
        play();
        switch (view.getId()) {
            case R.id.queren_beizhu:
                break;
            case R.id.zizhanghao:
                url = "http://112.74.196.237:81/robot_api/public/index.php/api/" + user.getData().getId() + "/son";
                String name = yonghu_et.getText().toString();
                if (!name.trim().isEmpty()) {
                    jsonObject = new JsonObject();
                    jsonObject.addProperty("name", name);
                    jsonObject.addProperty("parent_id", user.getData().getId());
                    jsonObject.addProperty("fingerprint", String.valueOf(user.getData().getFingerprint()));
                    jsonObject.addProperty("department_id", news.get(position).getDepartment_id());
                    jsonObject.addProperty("role_id", "2");
                    addUser(url, jsonObject.toString());
                }

                break;
            case R.id.mima_queren:
                break;
            case R.id.fenzu:
                url = "http://112.74.196.237:81//robot_api/public/index.php/api/" + user.getData().getId() + "/departments";
                String zu = zu_et.getText().toString();
                if (!zu.trim().isEmpty()) {

                    jsonObject = new JsonObject();
                    jsonObject.addProperty("name", zu);
                    if (user.getData().getParent_id() > 0)
                        jsonObject.addProperty("manager_id", user.getData().getParent_id());
                    else
                        jsonObject.addProperty("manager_id", user.getData().getId());
                    Log.e("et", zu + " " + url);
                    addUser(url, jsonObject.toString());
                }
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
        }
    }

    private void addUser(final String url, final String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);
                Log.e("onFailure", e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = new Message();
                msg.arg1 = 1;
                handler.sendMessage(msg);
                String js = response.body().string();
                Log.e("js", js);
                User user = MyApplication.newInstance().getUser();
                String url = "";
                if (user != null) {
                    if (user.getData().getParent_id() > 0)
                        url = "http://112.74.196.237:81/robot_api/public/index.php/api/" + user.getData().getId() + "/departments/manager/" + user.getData().getParent_id() + "/users";
                    else
                        url = "http://112.74.196.237:81/robot_api/public/index.php/api/" + user.getData().getId() + "/departments/manager/" + user.getData().getId() + "/users";
                    getUser(url);
                }
            }
        });
    }

    private void getUser(String url) {
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("msg", "网络异常");
                msg.setData(b);
                msg.arg1 = 0;
                handler.sendMessage(msg);
                Log.e("js", e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Log.e("js", js);
                Zu zu = gs.fromJson(js, Zu.class);//把JSON字符串转为对象
                if (zu != null) {
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    if (zu.getCode() == 1) {
                        msg.arg1 = 1;
                        b.putString("msg", "查询成功");
                        adapter.setCount(zu.getData());
                        //  adapter.notifyDataSetChanged();
                    } else {
                        msg.arg1 = 0;
                        b.putString("msg", "查询失败");
                    }
                    msg.setData(b);
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    b.putString("msg", "查询失败");
                    msg.setData(b);
                    handler.sendMessage(msg);

                }

            }
        });
    }

}
