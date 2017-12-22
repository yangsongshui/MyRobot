package com.myrobot.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

public class VideoActivity extends BaseActivity {


    VideoView videoView;
    @Override
    protected int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    protected void init() {
        videoView= (VideoView) findViewById(R.id.videoView);
        String url = getIntent().getStringExtra("url");
        Uri uri = Uri.parse( url );
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());

        //设置视频路径
        videoView.setVideoURI(uri);

        //开始播放视频
        videoView.start();
    }
    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
