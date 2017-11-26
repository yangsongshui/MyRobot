package com.myrobot.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_name)
    TextView homeName;

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.lianwang_bt, R.id.huyi_bt, R.id.renji_bt, R.id.jiqiren_bt, R.id.shangwu_bt, R.id.qiye_bt, R.id.yonghu_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lianwang_bt:
                break;
            case R.id.huyi_bt:
                startActivity(new Intent(this, HuiYiActivity.class));
                break;
            case R.id.renji_bt:
                startActivity(new Intent(this, RenJiActivity.class));
                break;
            case R.id.jiqiren_bt:
                startActivity(new Intent(this, JiQiRenActivity.class));
                break;
            case R.id.shangwu_bt:
                break;
            case R.id.qiye_bt:
                break;
            case R.id.yonghu_bt:
                startActivity(new Intent(this, GuanLiYuanActivity.class));
                break;
        }
    }
}
