package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myrobot.R;
import com.myrobot.api.Page;
import com.myrobot.app.MyApplication;
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

    @BindView(R.id.bt_1)
    TextView bt_1;
    @BindView(R.id.bt_2)
    TextView bt_2;
    @BindView(R.id.bt_3)
    TextView bt_3;
    @BindView(R.id.bt_4)
    TextView bt_4;
    @BindView(R.id.bt_5)
    TextView bt_5;
    @BindView(R.id.bt_6)
    TextView bt_6;
    @BindView(R.id.msg_et)
    EditText msg_et;
    OkHttpClient client;
    Gson gs;
    Handler handler;
    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_qi_ye;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        gs = new Gson();
        client = MyApplication.newInstance().getmOkHttpClient();
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

    @OnClick({R.id.bt_1, R.id.shousuo_bt, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shousuo_bt:
                if (!msg_et.getText().toString().equals("")) {
                    postPage("http://112.74.196.237:81/robot_api/public/index.php/api/3/files?key=" + msg_et.getText().toString());
                    // startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://news.baidu.com/ns?word=" + msg_et.getText().toString()));
                }
                break;
            case R.id.bt_1:

                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(0).getPath()));
                break;
            case R.id.bt_2:
                //if (pagebeanBean != null)
                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(1).getPath()));
                break;
            case R.id.bt_3:
                // if (pagebeanBean != null)
                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(2).getPath()));
                break;
            case R.id.bt_4:
                // if (pagebeanBean != null)
                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(3).getPath()));
                break;
            case R.id.bt_5:
                //  if (pagebeanBean != null)
                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(4).getPath()));
                break;
            case R.id.bt_6:
                // if (pagebeanBean != null)
                startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://dcsapi.com?k=266904417&url=" + page.getData().get(5).getPath()));
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
                if (page != null){
                    Message msg = new Message();
                    Bundle b = new Bundle();// 存放数据
                    b.putString("msg", page.getMsg());
                    msg.setData(b);
                    handler.sendMessage(msg);
                    if (page.getCode() == 1) {

                        initView();
                    }
                }else {
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
        if (page.getData().size() > 0) {
            msg1.setText(page.getData().get(0).getName());
            bt_1.setVisibility(View.VISIBLE);
        } else {
            bt_1.setVisibility(View.GONE);
        }
        if (page.getData().size() > 1) {
            msg2.setText(page.getData().get(1).getName());
            bt_2.setVisibility(View.VISIBLE);
        } else {
            bt_2.setVisibility(View.GONE);
        }
        if (page.getData().size() > 2) {
            msg3.setText(page.getData().get(2).getName());
            bt_3.setVisibility(View.VISIBLE);
        } else {
            bt_3.setVisibility(View.GONE);
        }
        if (page.getData().size() > 3) {
            msg4.setText(page.getData().get(3).getName());
            bt_4.setVisibility(View.VISIBLE);
        } else {
            bt_4.setVisibility(View.GONE);
        }

        if (page.getData().size() > 4) {
            msg5.setText(page.getData().get(4).getName());
            bt_5.setVisibility(View.VISIBLE);
        } else {
            bt_5.setVisibility(View.GONE);
        }
        if (page.getData().size() > 5) {
            msg6.setText(page.getData().get(5).getName());
            bt_6.setVisibility(View.VISIBLE);
        } else {
            bt_6.setVisibility(View.GONE);
        }


    }
}
