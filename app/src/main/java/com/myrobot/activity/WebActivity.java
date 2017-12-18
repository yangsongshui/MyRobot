package com.myrobot.activity;

import android.view.View;
import android.webkit.WebView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {


    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentView() {
        return R.layout.activity_web;
    }

    @Override
    protected void init() {
        String url = getIntent().getStringExtra("url");
        //设置自适应屏幕，两者合用
        webView.loadUrl(url);
    }


    @OnClick({R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
        }
    }
}
