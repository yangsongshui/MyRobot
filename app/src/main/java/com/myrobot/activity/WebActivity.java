package com.myrobot.activity;

import android.view.View;
import android.webkit.WebSettings;
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
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
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
