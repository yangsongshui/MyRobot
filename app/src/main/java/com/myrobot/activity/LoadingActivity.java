package com.myrobot.activity;

import android.content.Intent;
import android.os.Handler;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

public class LoadingActivity extends BaseActivity {


    Handler handler = new Handler();

    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    @Override
    protected void init() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            }
        }, 20000);
    }
}
