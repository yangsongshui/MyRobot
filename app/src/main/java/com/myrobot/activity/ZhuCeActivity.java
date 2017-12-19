package com.myrobot.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myrobot.R;
import com.myrobot.api.Code;
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

public class ZhuCeActivity extends BaseActivity {
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.coed_ed)
    EditText coedEd;
    @BindView(R.id.rl)
    RelativeLayout relativeLayout;
    @BindView(R.id.zhuche_back)
    ImageView zhuche_back;
    @BindView(R.id.zhiwen)
    ImageView zhiwen;
    OkHttpClient client;
    Gson gs;
    Handler handler;

    @Override
    protected int getContentView() {
        return R.layout.activity_zhu_ce;
    }

    @Override
    protected void init() {
        gs = new Gson();
        client = new OkHttpClient();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    String js = msg.getData().getString("user");
                    User user = gs.fromJson(js, User.class);//把JSON字符串转为对象
                    showToastor(user.getMsg());
                    if (user.getCode() == 1)
                        finish();
                } else if (msg.arg1 == 2) {
                    showToastor("网络异常");
                }else if (msg.arg1==3){
                    showToastor((String) msg.obj);
                }
                relativeLayout.setVisibility(View.VISIBLE);
                zhuche_back.setVisibility(View.VISIBLE);
                zhiwen.setVisibility(View.GONE);
                return false;
            }
        });

    }


    @OnClick({R.id.zhuche_back, R.id.zhuche_bt, R.id.get_code})
    public void onViewClicked(View view) {
        JsonObject jsonObject;
        play();
        switch (view.getId()) {
            case R.id.zhuche_back:
                finish();
                break;
            case R.id.zhuche_bt:
                relativeLayout.setVisibility(View.GONE);
                Glide.with(ZhuCeActivity.this).load(R.drawable.zhiweng).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(zhiwen);
                zhuche_back.setVisibility(View.GONE);
                zhiwen.setVisibility(View.VISIBLE);
                String phone2 = phoneEt.getText().toString();
                String psw = pswEt.getText().toString();
                String code = coedEd.getText().toString();
                jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone2);
                jsonObject.addProperty("vcode", "888888");
                jsonObject.addProperty("password", MD5.getMD5(psw));
                int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                jsonObject.addProperty("fingerprint", String.valueOf(numcode));
                jsonObject.addProperty("robot_mac", "80984093298574");
                postZhuce("http://112.74.196.237:81/robot_api/public/index.php/users/register?", jsonObject.toString());
                break;
            case R.id.get_code:
                String phone = phoneEt.getText().toString();
                jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone);
                postCoed("http://112.74.196.237:81/robot_api/public/index.php/users/vcode?", jsonObject.toString());


                break;
        }
    }

    Code jsonObject = new Code();

    private void postCoed(String url, final String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                jsonObject = gs.fromJson(js, Code.class);//把JSON字符串转为对象
                Message msg = new Message();
                msg.arg1 = 3;
                msg.obj=jsonObject.getMsg();
                handler.sendMessage(msg);
            }
        });
    }

    private void postZhuce(String url, final String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("user", js);
                msg.arg1 = 1;
                msg.setData(b);
                handler.sendMessage(msg);

            }
        });
    }
}
