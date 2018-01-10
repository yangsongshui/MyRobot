package com.myrobot.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.myrobot.R;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.psw_et)
    EditText pswEt;
    @BindView(R.id.rl)
    RelativeLayout rl;

    OkHttpClient client;
    Gson gs;
    private ProgressDialog progressDialog;
    Handler handler;
    SerialControl ComA;
    DispQueueThread DispQueue;//刷新显示线程
    String userid = "";
    int id = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        client = new OkHttpClient();
        gs = new Gson();
        ComA = new SerialControl();
        DispQueue = new DispQueueThread();
        DispQueue.start();
        ComA.setPort("/dev/ttyS4");
        ComA.setBaudRate("9600");
        OpenComPort(ComA);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {


                // showToastor(msg.getData().getString("msg"));


                return false;
            }
        });
    }

    @OnClick({R.id.login_bt, R.id.zhuche_bt, R.id.main_back, R.id.main_rl, R.id.zhiwenbt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.login_bt:
                // startActivity(new Intent(MainActivity.this, HomeActivity.class));
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                String phone = phoneEt.getText().toString();
                String psw = pswEt.getText().toString();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone);
                jsonObject.addProperty("password", MD5.getMD5(psw));
                post("http://112.74.196.237:81/robot_api/public/index.php/users/login?", jsonObject.toString());

                break;
            case R.id.zhuche_bt:

                startActivity(new Intent(MainActivity.this, ZhuCeActivity.class));
                break;
            case R.id.main_back:
                rl.setVisibility(View.GONE);
                //finish();
                break;
            case R.id.main_rl:
                rl.setVisibility(View.VISIBLE);
                break;
            case R.id.zhiwenbt:
                progressDialog.show();
                break;
            default:
                break;

        }
    }

    public void post(String url, String json) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().addHeader("Accept", "*/*").url(url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putString("msg", "网络异常");
                msg.setData(b);
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                User user = gs.fromJson(js, User.class);//把JSON字符串转为对象
          /*      Message msg = new Message();
                Bundle b = new Bundle();// 存放数据*/
                if (user != null) {
                 /*   b.putString("msg", user.getMsg());
                    msg.setData(b);
                    handler.sendMessage(msg);*/
                    if (user.getCode() == 1) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }
                } else {
                  /*  b.putString("msg", "数据获取失败");
                    msg.setData(b);*/
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
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
        if (sMsg.indexOf("@") != -1) {
            String msg = sMsg.substring(sMsg.indexOf("@") + 1, sMsg.length());
            String userid = msg.substring(1, 3);
            if (msg.substring(9, 10).equals("3")) {
                String phone = SpUtils.getString(userid + "phone", "");
                String psw = SpUtils.getString(userid + "psw", "");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("phone", phone);
                jsonObject.addProperty("password", MD5.getMD5(psw));
                post("http://112.74.196.237:81/robot_api/public/index.php/users/login?", jsonObject.toString());
            } else {
                showToastor("指纹识别失败");
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }
        }


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
