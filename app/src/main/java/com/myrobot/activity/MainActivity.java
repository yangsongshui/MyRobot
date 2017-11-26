package com.myrobot.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

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
        }
    }
}
