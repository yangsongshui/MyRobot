package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.myrobot.OnItemCheckListener;
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

public class QiYeActivity extends BaseActivity implements OnItemCheckListener {

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
        client = new OkHttpClient();
        pageAdapter = new PageAdapter(this);
        initView();
        progressDialog.show();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                showToastor(msg.getData().getString("msg"));
                if (msg.arg1==1)
                    pageAdapter.setItems(page.getData());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                return false;
            }
        });
        pageAdapter.setOnItemCheckListener(new OnItemCheckListener() {
            @Override
            public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {
                Page.DataBean data =page.getData().get(position);
                if (data.getPath().contains(".mp4")) {
                    startActivity(new Intent(QiYeActivity.this, VideoActivity.class)
                            .putExtra("url", data.getPath())
                            .putExtra("name", data.getName()));
                } else {
                    startActivity(new Intent(QiYeActivity.this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(position).getPath()));
                }
            }
        });
        postPage("http://112.74.196.237:81/robot_api/public/index.php/api/30/files?key=");
        pageAdapter.setOnItemCheckListener(this);
    }

    @OnClick({R.id.shousuo_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shousuo_bt:
                if (!msg_et.getText().toString().equals("")) {
                    progressDialog.show();
                    postPage("http://112.74.196.237:81/robot_api/public/index.php/api/30/files?key=" + msg_et.getText().toString());

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
                msg.arg1 = 0;
                handler.sendMessage(msg);
                Log.e("js", e.getMessage().toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Log.e("js", js);
                page = gs.fromJson(js, Page.class);//把JSON字符串转为对象
                if (page != null) {
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    if (page.getCode() == 1) {
                        msg.arg1 = 1;
                        b.putString("msg", "查询成功");
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


    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        pageAdapter = new PageAdapter(this);
        listView.setAdapter(pageAdapter);
    }

    @Override
    public void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position) {
        startActivity(new Intent(this,WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(position).getPath()));
    }
}
