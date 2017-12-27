package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;
import com.myrobot.IRecogListener;
import com.myrobot.R;
import com.myrobot.adapter.NewsAdapter;
import com.myrobot.api.ServiceApi;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.News;
import com.myrobot.control.InitConfig;
import com.myrobot.control.MyRecognizer;
import com.myrobot.control.MySyntherizer;
import com.myrobot.control.NonBlockSyntherizer;
import com.myrobot.listener.StatusRecogListener;
import com.myrobot.listener.UiMessageListener;
import com.myrobot.utils.OfflineResource;
import com.myrobot.utils.RecogResult;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.myrobot.MainHandlerConstant.INIT_SUCCESS;
import static com.myrobot.MainHandlerConstant.PRINT;
import static com.myrobot.utils.Constant.NESW_URL;

public class ShangWuActivity extends BaseActivity implements Callback<News> {
    Retrofit retrofit;
    ProgressDialog progressDialog;
    NewsAdapter newsAdapter;
    @BindView(R.id.list_vertical)
    RecyclerView listVertical;
    @BindView(R.id.msg_1)
    TextView msg1;
    @BindView(R.id.msg_2)
    TextView msg2;
    @BindView(R.id.msg_3)
    TextView msg3;
    @BindView(R.id.msg_4)
    TextView msg4;
    @BindView(R.id.msg_5)
    TextView msg5;
    @BindView(R.id.msg_6)
    TextView msg6;
    @BindView(R.id.msg_et)
    EditText msg_et;
    ServiceApi service;

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
        return R.layout.activity_shang_wu;
    }

    @Override
    protected void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NESW_URL)
                .build();
        progressDialog.show();
        service = retrofit.create(ServiceApi.class);
        Call<News> call = service.getNesw("机器人", "1");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //请求成功操作
                News news = response.body();
                if (news.getShowapi_res_code() == 0) {
                    showToastor("查询成功");
                    Log.d("news", news.toString());
                    newsAdapter = new NewsAdapter(news, ShangWuActivity.this);
                    initRecyclerView(listVertical, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), newsAdapter);

                } else {
                    showToastor("查询失败");

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //请求失败操作
                progressDialog.dismiss();
                showToastor("查询失败");
            }
        });
        Call<News> call2 = service.getNesw("人工智能", "1");
        call2.enqueue(this);
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

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final NewsAdapter adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.setMaxVisibleItems(2);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(false);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                final int position = recyclerView.getChildLayoutPosition(v);
                final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
                Toast.makeText(ShangWuActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, recyclerView, layoutManager);

        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

            @Override
            public void onCenterItemChanged(final int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    // final int value = adapter.mPosition[adapterPosition];
/*
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                    adapter.notifyItemChanged(adapterPosition);
*/
                }
            }
        });
    }

    private void initView(News.ShowapiResBodyBean.PagebeanBean pagebeanBean) {
        this.pagebeanBean = pagebeanBean;
        if (pagebeanBean.getContentlist().size() > 0)
            msg1.setText(pagebeanBean.getContentlist().get(0).getTitle());

        if (pagebeanBean.getContentlist().size() > 1)
            msg2.setText(pagebeanBean.getContentlist().get(1).getTitle());

        if (pagebeanBean.getContentlist().size() > 2)
            msg3.setText(pagebeanBean.getContentlist().get(2).getTitle());

        if (pagebeanBean.getContentlist().size() > 3)
            msg4.setText(pagebeanBean.getContentlist().get(3).getTitle());

        if (pagebeanBean.getContentlist().size() > 4)
            msg5.setText(pagebeanBean.getContentlist().get(4).getTitle());

        if (pagebeanBean.getContentlist().size() > 5)
            msg6.setText(pagebeanBean.getContentlist().get(5).getTitle());


    }

    @OnClick({R.id.shuaxin, R.id.yuyin_bt, R.id.shousuo_bt, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shuaxin:
                break;
            case R.id.shousuo_bt:
                if (!msg_et.getText().toString().equals("")) {
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", "http://www.baidu.com/s?wd=" + msg_et.getText().toString()));
                }
                break;
            case R.id.bt_1:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(0).getLink()));
                break;
            case R.id.bt_2:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(1).getLink()));
                break;
            case R.id.bt_3:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(2).getLink()));
                break;
            case R.id.bt_4:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(3).getLink()));
                break;
            case R.id.bt_5:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(4).getLink()));
                break;
            case R.id.bt_6:
                if (pagebeanBean != null)
                    startActivity(new Intent(this, WebActivity.class).putExtra("url", pagebeanBean.getContentlist().get(5).getLink()));
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
            case R.id.yuyin_bt:
                startASr();
                break;
            default:
                break;
        }
    }

    News.ShowapiResBodyBean.PagebeanBean pagebeanBean;

    @Override
    public void onResponse(Call<News> call, Response<News> response) {
        //请求成功操作
        News news = response.body();
        if (news.getShowapi_res_code() == 0) {
            showToastor("查询成功");
            initView(news.getShowapi_res_body().getPagebean());

        } else {
            showToastor("查询失败");

        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<News> call, Throwable t) {
        //请求失败操作
        progressDialog.dismiss();
        showToastor("查询失败");
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
        IRecogListener listener = new ShangWuActivity.TtsRecogListener(synthesizer);
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
                startActivity(new Intent(ShangWuActivity.this, WebActivity.class).putExtra("url", "http://www.baidu.com/s?wd=" + msg));
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
