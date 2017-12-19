package com.myrobot.activity;

import android.view.View;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.OnClick;

public class GuanLiYuanActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_guan_li_yuan;
    }

    @Override
    protected void init() {

    }



    @OnClick({R.id.queren_beizhu, R.id.zizhanghao, R.id.mima_queren, R.id.fenzu, R.id.add_1, R.id.add_2, R.id.add_3, R.id.add_4, R.id.add_5, R.id.add_6, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.queren_beizhu:
                break;
            case R.id.zizhanghao:
                break;
            case R.id.mima_queren:
                break;
            case R.id.fenzu:
                break;
            case R.id.add_1:
                break;
            case R.id.add_2:
                break;
            case R.id.add_3:
                break;
            case R.id.add_4:
                break;
            case R.id.add_5:
                break;
            case R.id.add_6:
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
        }
    }
}
