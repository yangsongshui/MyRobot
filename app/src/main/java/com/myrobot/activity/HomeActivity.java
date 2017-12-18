package com.myrobot.activity;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.app.MyApplication;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_name)
    TextView homeName;
    @BindView(R.id.shun1)
    ImageView shun1;
    @BindView(R.id.ni)
    ImageView ni1;
    @BindView(R.id.shun2)
    ImageView shun2;
    @BindView(R.id.ni2)
    ImageView ni2;
    Animation operatingAnim;
    Animation operatingAnim2;
    Animation operatingAnim3;
    Animation operatingAnim4;
    OkHttpClient client ;
    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void init() {
        client = MyApplication.newInstance().getmOkHttpClient();
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        operatingAnim2 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim2);
        operatingAnim3 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        operatingAnim4 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim2);
        LinearInterpolator interpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(interpolator);
        operatingAnim2.setInterpolator(interpolator);
        operatingAnim3.setInterpolator(interpolator);
        operatingAnim4.setInterpolator(interpolator);
        shun1.startAnimation(operatingAnim);
        shun2.startAnimation(operatingAnim3);
        ni1.startAnimation(operatingAnim2);
        ni2.startAnimation(operatingAnim4);




    }


    @OnClick({R.id.lianwang_bt, R.id.huyi_bt, R.id.renji_bt, R.id.jiqiren_bt, R.id.shangwu_bt, R.id.qiye_bt, R.id.yonghu_bt})
    public void onViewClicked(View view) {
        play();
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
                startActivity(new Intent(this, ShangWuActivity.class));
                break;
            case R.id.qiye_bt:
                startActivity(new Intent(this, QiYeActivity.class));
                break;
            case R.id.yonghu_bt:
                startActivity(new Intent(this, GuanLiYuanActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shun1.clearAnimation();
        shun2.clearAnimation();
        ni1.clearAnimation();
        ni2.clearAnimation();
    }



}
