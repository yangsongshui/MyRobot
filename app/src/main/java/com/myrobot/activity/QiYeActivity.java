package com.myrobot.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.myrobot.R;
import com.myrobot.adapter.PageAdapter;
import com.myrobot.api.Page;
import com.myrobot.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QiYeActivity extends BaseActivity {

    @BindView(R.id.msg_et)
    EditText msg_et;
    @BindView(R.id.lv)
    RecyclerView listView;
    OkHttpClient client;
    Gson gs;
    Handler handler;
    ProgressDialog progressDialog;
    PageAdapter pageAdapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_qi_ye;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        gs = new Gson();
        client =  new OkHttpClient();
        progressDialog.show();
        initView();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                pageAdapter.setItems(page.getData());
                showToastor(msg.getData().getString("msg"));
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                return false;
            }
        });

        postPage("http://112.74.196.237:81/robot_api/public/index.php/api/3/files?key=");
    }

    @OnClick({R.id.shousuo_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shousuo_bt:
                if (!msg_et.getText().toString().equals("")) {
                    progressDialog.show();
                    postPage("http://112.74.196.237:81/robot_api/public/index.php/api/3/files?key=" + msg_et.getText().toString());

                }
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
                if (page != null) {
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    if (page.getCode()==1){
                        b.putString("msg", "查询成功");
                    }else {
                        b.putString("msg", "查询失败");
                    }
                    b.putString("msg", page.getMsg());
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


    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        pageAdapter = new PageAdapter(this);
        listView.setAdapter(pageAdapter);
    }
}
