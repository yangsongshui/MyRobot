package com.myrobot.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;
import com.myrobot.widget.CommomDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class JiQiRenActivity extends BaseActivity {


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
    private CommomDialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_ji_qi_ren;
    }

    @Override
    protected void init() {
        dialog = new CommomDialog(this, R.style.dialog);
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


    @OnClick({R.id.lianwang_bt, R.id.shexiang_bt, R.id.zhuping_bt, R.id.yinxiang_bt, R.id.pingbi_bt, R.id.zhupin_bt, R.id.fenping_bt,
            R.id.touying_bt, R.id.kongqi_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.lianwang_bt:

                break;
            case R.id.shexiang_bt:
                dialog.show();
                break;
            case R.id.zhuping_bt:
                dialog.show();
                break;
            case R.id.yinxiang_bt:
                dialog.show();
                break;
            case R.id.pingbi_bt:
                dialog.show();
                break;
            case R.id.zhupin_bt:
                dialog.show();
                break;
            case R.id.fenping_bt:
                dialog.show();
                break;
            case R.id.touying_bt:
                dialog.show();
                break;
            case R.id.kongqi_bt:
                dialog.show();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        shun1.clearAnimation();
        shun2.clearAnimation();
        ni1.clearAnimation();
        ni2.clearAnimation();
    }
}
