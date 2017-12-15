package com.myrobot.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.psw_et)
    EditText pswEt;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.login_bt, R.id.zhuche_bt, R.id.main_back})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.login_bt:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
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

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IOException("Unexpected code " + response);
        }
        return "";
    }
}
