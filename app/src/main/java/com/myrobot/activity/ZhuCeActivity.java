package com.myrobot.activity;

import android.view.View;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.OnClick;

public class ZhuCeActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_zhu_ce;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.zhuche_back, R.id.zhuche_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.zhuche_back:
                finish();
                break;
            case R.id.zhuche_bt:
                break;
        }
    }
}
