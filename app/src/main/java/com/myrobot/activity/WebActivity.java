package com.myrobot.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        WebSettings wSet = webView.getSettings();
        wSet.setJavaScriptEnabled(true);
        wSet.setUseWideViewPort(true);
        wSet.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
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
            default:
                break;
        }
    }
}
