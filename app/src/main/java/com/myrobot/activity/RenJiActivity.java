package com.myrobot.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myrobot.R;
import com.myrobot.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RenJiActivity extends BaseActivity {


    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    @Override
    protected int getContentView() {
        return R.layout.activity_ren_ji;
    }

    @Override
    protected void init() {

        Glide.with(RenJiActivity.this).load(R.drawable.renjig).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image1);
    }


    @OnClick({R.id.yuyin_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.yuyin_bt:
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
