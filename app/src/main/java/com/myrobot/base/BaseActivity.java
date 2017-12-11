package com.myrobot.base;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.myrobot.R;
import com.myrobot.utils.Toastor;

import butterknife.ButterKnife;


public abstract class BaseActivity extends FragmentActivity {

    Toastor toastor;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        ButterKnife.bind(this);
        //用于显示当前位于哪个活动
        Log.d("BaseActivity", getClass().getSimpleName());
        sp= new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.start, 1); //把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
        init();
        toastor = new Toastor(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //注入布局
    protected abstract int getContentView();

    //初始化
    protected abstract void init();
    public void showToastor(String msg){
        toastor.showSingletonToast(msg);
    }
    public void play(){
        sp.play(music, 1, 1, 0, 0, 1);
    }
}
