package com.myrobot.activity;

import android.view.View;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class JiQiRenActivity extends BaseActivity {


    @BindView(R.id.home_name)
    TextView homeName;

    @Override
    protected int getContentView() {
        return R.layout.activity_ji_qi_ren;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.lianwang_bt, R.id.shexiang_bt, R.id.zhuping_bt, R.id.yinxiang_bt, R.id.pingbi_bt, R.id.zhupin_bt, R.id.fenping_bt,
            R.id.touying_bt, R.id.kongqi_bt,R.id.xia_bt,R.id.shang_bt,R.id.zuo_bt,R.id.you_bt,R.id.fanhui_bt,R.id.home_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lianwang_bt:
                break;
            case R.id.shexiang_bt:
                break;
            case R.id.zhuping_bt:
                break;
            case R.id.yinxiang_bt:
                break;
            case R.id.pingbi_bt:
                break;
            case R.id.zhupin_bt:
                break;
            case R.id.fenping_bt:
                break;
            case R.id.touying_bt:
                break;
            case R.id.kongqi_bt:
                break;
            case R.id.xia_bt:
                break;
            case R.id.shang_bt:
                break;
            case R.id.zuo_bt:
                break;
            case R.id.you_bt:
                break;
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
