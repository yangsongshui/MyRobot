package com.myrobot.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;
import com.myrobot.widget.VideoView;

public class LoadingActivity extends BaseActivity {

    private VideoView video;


    @Override
    protected int getContentView() {
        return R.layout.activity_loading;
    }

    @Override
    protected void init() {
        video = (VideoView) findViewById(R.id.video);

        MediaController mc = new MediaController(LoadingActivity.this);
        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logdin));
        video.setMediaController(mc);
        video.requestFocus();
        try {
            video.start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {


            @Override
            public void onCompletion(MediaPlayer mp) {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            }


        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
