package com.myrobot.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myrobot.R;
import com.myrobot.api.Code;
import com.myrobot.api.User;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.AssistBean;
import com.myrobot.bean.ComBean;
import com.myrobot.utils.MD5;
import com.myrobot.utils.SerialHelper;
import com.myrobot.utils.SpUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Queue;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.myrobot.utils.Constant.JSON;

public class ZhuCeActivity extends BaseActivity {
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.coed_ed)
    EditText coedEd;
    @BindView(R.id.rl)
    RelativeLayout relativeLayout;
    @BindView(R.id.zhuche_back)
    ImageView zhuche_back;
    @BindView(R.id.zhiwen)
    ImageView zhiwen;
    OkHttpClient client;
    Gson gs;
    Handler handler;
    SerialControl ComA;
    DispQueueThread DispQueue;//刷新显示线程
    String userid = "";
    int id = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_zhu_ce;
    }

    @Override
    protected void init() {
        gs = new Gson();
        client = new OkHttpClient();
        ComA = new SerialControl();
        DispQueue = new DispQueueThread();
        DispQueue.start();
        ComA.setPort("/dev/ttyS4");
        ComA.setBaudRate("9600");
        OpenComPort(ComA);
        id = SpUtils.getInt("id", 0);

        if (id < 10) {
            userid = "0" + 1;
        } else {
            userid = String.valueOf(id);
        }
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    String js = msg.getData().getString("user");
                    User user = gs.fromJson(js, User.class);//把JSON字符串转为对象
                    showToastor(user.getMsg());
                    if (user.getCode() == 1) {
                        sendPortData(ComA, "[51000000000000@10000" + userid + "]");
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendPortData(ComA, "[51000000000000@10000" + userid + "]");
                            }
                        }, 200);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendPortData(ComA, "[51000000000000@10000" + userid + "]");
                            }
                        }, 400);
                        SpUtils.putString(userid + "phone", phoneEt.getText().toString().trim());
                        SpUtils.putString(userid + "psw", pswEt.getText().toString().trim());
                        id++;
                        SpUtils.putInt("id", id);
                    }
                } else if (msg.arg1 == 2) {
                    showToastor("网络异常");
                } else if (msg.arg1 == 3) {
                    showToastor((String) msg.obj);
                }
                relativeLayout.setVisibility(View.VISIBLE);
                zhuche_back.setVisibility(View.VISIBLE);
                zhiwen.setVisibility(View.GONE);
                return false;
            }
        });


    }


    @OnClick({R.id.zhuche_back, R.id.zhuche_bt, R.id.get_code})
    public void onViewClicked(View view) {
        JsonObject jsonObject;
        play();
        switch (view.getId()) {
            case R.id.zhuche_back:
                finish();
                break;
            case R.id.zhuche_bt:
                relativeLayout.setVisibility(View.GONE);
                Glide.with(ZhuCeActivity.this).load(R.drawable.zhiweng).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(zhiwen);
                zhuche_back.setVisibility(View.GONE);
                zhiwen.setVisibility(View.VISIBLE);
                String phone2 = phoneEt.getText().toString();
                String psw = pswEt.getText().toString();
                String code = coedEd.getText().toString();
                jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone2);
                jsonObject.addProperty("vcode", "888888");
                jsonObject.addProperty("password", MD5.getMD5(psw));
                int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                jsonObject.addProperty("fingerprint", String.valueOf(numcode));
                jsonObject.addProperty("robot_mac", "80984093298574");
                postZhuce("http://112.74.196.237:81/robot_api/public/index.php/users/register?", jsonObject.toString());
                break;
            case R.id.get_code:
                String phone = phoneEt.getText().toString();
                jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone);
                postCoed("http://112.74.196.237:81/robot_api/public/index.php/users/vcode?", jsonObject.toString());


                break;
        }
    }

    Code jsonObject = new Code();

    private void postCoed(String url, final String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                jsonObject = gs.fromJson(js, Code.class);//把JSON字符串转为对象
                Message msg = new Message();
                msg.arg1 = 3;
                msg.obj = jsonObject.getMsg();
                handler.sendMessage(msg);
            }
        });
    }

    private void postZhuce(String url, final String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.arg1 = 2;
                handler.sendMessage(msg);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("user", js);
                msg.arg1 = 1;
                msg.setData(b);
                handler.sendMessage(msg);

            }
        });
    }

    //----------------------------------------------------串口控制类
    private class SerialControl extends SerialHelper {

        //		public SerialControl(String sPort, String sBaudRate){
//			super(sPort, sBaudRate);
//		}
        public SerialControl() {
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData) {
            //数据接收量大或接收时弹出软键盘，界面会卡顿,可能和6410的显示性能有关
            //直接刷新显示，接收数据量大时，卡顿明显，但接收与显示同步。
            //用线程定时刷新显示可以获得较流畅的显示效果，但是接收数据速度快于显示速度时，显示会滞后。
            //最终效果差不多-_-，线程定时刷新稍好一些。
            //线程定时刷新显示(推荐)
            DispQueue.AddQueue(ComRecData);
            /*
            runOnUiThread(new Runnable()//直接刷新显示
			{
				public void run()
				{
					DispRecData(ComRecData);
				}
			});*/
        }
    }

    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread {
        private Queue<ComBean> QueueList = new LinkedList<ComBean>();

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                final ComBean ComData;
                while ((ComData = QueueList.poll()) != null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            DispRecData(ComData);
                        }
                    });
                    try {
                        Thread.sleep(100);//显示性能高的话，可以把此数值调小。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        public synchronized void AddQueue(ComBean ComData) {
            QueueList.add(ComData);
        }
    }

    //----------------------------------------------------显示接收数据
    private void DispRecData(ComBean ComRecData) {
        StringBuilder sMsg = new StringBuilder();
        sMsg.append("[");
        sMsg.append(ComRecData.sComPort);
        sMsg.append("]");
        sMsg.append(new String(ComRecData.bRec));
        Log.e("收到数据", sMsg.toString());
        if (sMsg.indexOf("@") != -1)
            finish();

    }

    private AssistBean getAssistData() {
        SharedPreferences msharedPreferences = getSharedPreferences("ComAssistant", Context.MODE_PRIVATE);
        AssistBean AssistData = new AssistBean();
        try {
            String personBase64 = msharedPreferences.getString("AssistData", "");
            byte[] base64Bytes = Base64.decode(personBase64.getBytes(), 0);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            AssistData = (AssistBean) ois.readObject();
            return AssistData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AssistData;
    }

    //----------------------------------------------------关闭串口
    private void CloseComPort(SerialHelper ComPort) {
        if (ComPort != null) {
            ComPort.stopSend();
            ComPort.close();
        }
    }

    //----------------------------------------------------打开串口
    private void OpenComPort(SerialHelper ComPort) {
        try {
            ComPort.open();

        } catch (SecurityException e) {
            showToastor("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            showToastor("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            showToastor("打开串口失败:参数错误!");
        }
    }

    //----------------------------------------------------串口发送
    private void sendPortData(SerialHelper ComPort, String sOut) {
        if (ComPort != null && ComPort.isOpen()) {
            ComPort.sendTxt(sOut);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloseComPort(ComA);
    }

}
