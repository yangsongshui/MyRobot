package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.myrobot.IRecogListener;
import com.myrobot.R;
import com.myrobot.base.BaseActivity;
import com.myrobot.control.InitConfig;
import com.myrobot.control.MyRecognizer;
import com.myrobot.control.MySyntherizer;
import com.myrobot.control.NonBlockSyntherizer;
import com.myrobot.listener.StatusRecogListener;
import com.myrobot.listener.UiMessageListener;
import com.myrobot.utils.OfflineResource;
import com.myrobot.utils.RecogResult;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.myrobot.MainHandlerConstant.INIT_SUCCESS;
import static com.myrobot.MainHandlerConstant.PRINT;

public class RenJiActivity extends BaseActivity {


    @BindView(R.id.image1)
    ImageView image1;
    @BindView(R.id.image2)
    ImageView image2;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.image4)
    ImageView image4;

    ProgressDialog progressDialog;
// ================== 初始化参数设置开始 ==========================
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */
    protected String appId = "10543841";

    protected String appKey = "Ij8iZTyjdWn2WceqP0x5Egjn";

    protected String secretKey = "R914etRqV7vXipuu8G6hr1smPCKC212F";

    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    protected TtsMode ttsMode = TtsMode.MIX;

    // 离线发音选择，VOICE_FEMALE即为离线女声发音。

    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    protected String offlineVoice = OfflineResource.VOICE_FEMALE;

    // 主控制类，所有合成控制方法从这个类开始
    protected MySyntherizer synthesizer;
    Handler mainHandler;

    @Override
    protected int getContentView() {
        return R.layout.activity_ren_ji;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("初始化中...");
        progressDialog.show();
        mainHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                switch (what) {
                    case INIT_SUCCESS:
                        msg.what = PRINT;

                        break;
                    default:
                        break;
                }


            }

        };
        initialTts(); // 初始化TTS引擎
        initialAsr();
        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }, 2000);
    }


    @OnClick({R.id.yuyin_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.yuyin_bt:
                Glide.with(RenJiActivity.this).load(R.drawable.renjig).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image1);
                startASr();
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
        }
    }

    /**
     * 初始化引擎，需要的参数均在InitConfig类里
     * <p>
     * DEMO中提供了3个SpeechSynthesizerListener的实现
     * MessageListener 仅仅用log.i记录日志，在logcat中可以看见
     * UiMessageListener 在MessageListener的基础上，对handler发送消息，实现UI的文字更新
     * FileSaveListener 在UiMessageListener的基础上，使用 onSynthesizeDataArrived回调，获取音频流
     */
    protected void initialTts() {
        // 设置初始化参数
        SpeechSynthesizerListener listener = new UiMessageListener(mainHandler); // 此处可以改为 含有您业务逻辑的SpeechSynthesizerListener的实现类

        Map<String, String> params = getParams();

        // appId appKey secretKey 网站上您申请的应用获取。注意使用离线合成功能的话，需要应用中填写您app的包名。包名在build.gradle中获取。
        InitConfig initConfig = new InitConfig(appId, appKey, secretKey, ttsMode, offlineVoice, params, listener);

        synthesizer = new NonBlockSyntherizer(this, initConfig, mainHandler); // 此处可以改为MySyntherizer 了解调用过程
    }

    private MyRecognizer myRecognizer;

    private void initialAsr() {
        IRecogListener listener = new TtsRecogListener(synthesizer);
        Log.e("识别系统", "初始化开始");
        myRecognizer = new MyRecognizer(this, listener);
        Log.e("识别系统", "初始化结束");
    }

    /**
     * speak 实际上是调用 synthesize后，获取音频流，然后播放。
     * 获取音频流的方式见SaveFileActivity及FileSaveListener
     * 需要合成的文本text的长度不能超过1024个GBK字节。
     */
    private void speak(String msg) {

        String text = "已为你搜索" + msg;
        //需要合成的文本text的长度不能超过1024个GBK字节。

        // 合成前可以修改参数：
        Map<String, String> params = getParams();
        synthesizer.setParams(params);
        int result = synthesizer.speak(text);
        // checkResult(result, "speak");
    }

    /**
     * 合成的参数，可以初始化时填写，也可以在合成前设置。
     *
     * @return
     */
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        // 以下参数均为选填
        params.put(SpeechSynthesizer.PARAM_SPEAKER, "0"); // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        params.put(SpeechSynthesizer.PARAM_VOLUME, "5"); // 设置合成的音量，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_SPEED, "5");// 设置合成的语速，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_PITCH, "5");// 设置合成的语调，0-9 ，默认 5
        params.put(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);         // 该参数设置为TtsMode.MIX生效。即纯在线模式不生效。
        // MIX_MODE_DEFAULT 默认 ，wifi状态下使用在线，非wifi离线。在线状态下，请求超时6s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE_WIFI wifi状态下使用在线，非wifi离线。在线状态下， 请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_NETWORK ， 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        // MIX_MODE_HIGH_SPEED_SYNTHESIZE, 2G 3G 4G wifi状态下使用在线，其它状态离线。在线状态下，请求超时1.2s自动转离线
        return params;
    }

    @Override
    protected void onDestroy() {
        synthesizer.release();
        myRecognizer.release(); //销毁资源     Log.i(TAG, "onDestory"); 
        super.onDestroy();
    }

    public class TtsRecogListener extends StatusRecogListener {

        private final static String TAG = "TtsRecogListener";

        public TtsRecogListener(MySyntherizer mySyntherizer) {

        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {
            super.onAsrFinalResult(results, recogResult);
            String msg = results[0];
            if (!msg.equals("")) {
                startActivity(new Intent(RenJiActivity.this, WebActivity.class).putExtra("url", "http://www.baidu.com/s?word=" + msg));
                speak(msg);
            }

        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {
            super.onAsrFinishError(errorCode, subErrorCode, errorMessage, descMessage, recogResult);
            String msg = "错误码是：" + errorCode;
            Log.i(TAG, msg);


        }
    }

    private void startASr() {
        Map<String, Object> params = new TreeMap<>();
        myRecognizer.start(params);
        Toast.makeText(this, "请开始说话", Toast.LENGTH_LONG).show();
    }

}
