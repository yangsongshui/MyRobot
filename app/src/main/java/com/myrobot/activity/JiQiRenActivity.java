package com.myrobot.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.AssistBean;
import com.myrobot.bean.ComBean;
import com.myrobot.utils.SerialHelper;
import com.myrobot.widget.CommomDialog;
import com.myrobot.widget.OnOffDialog;
import com.myrobot.widget.VolumeDialog;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android_serialport_api.SerialPortFinder;
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
    private OnOffDialog onOffDialog;
    private VolumeDialog volumeDialog;
    SerialControl ComA;
    DispQueueThread DispQueue;//刷新显示线程
    SerialPortFinder mSerialPortFinder;//串口设备搜索

    AssistBean AssistData;//用于界面数据序列化和反序列化

    @Override
    protected int getContentView() {
        return R.layout.activity_ji_qi_ren;
    }

    @Override
    protected void init() {
        ComA = new SerialControl();
        DispQueue = new DispQueueThread();
        DispQueue.start();
        AssistData = getAssistData();
        dialog = new CommomDialog(this, R.style.dialog);
        onOffDialog = new OnOffDialog(this, R.style.dialog);
        volumeDialog = new VolumeDialog(this, R.style.dialog);
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
        mSerialPortFinder = new SerialPortFinder();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();
        List<String> allDevices = new ArrayList<String>();
        for (int i = 0; i < entryValues.length; i++) {
            allDevices.add(entryValues[i]);
            Log.e("串口", entryValues[i]);
        }
        ComA.setPort("/dev/ttyS4");
        ComA.setBaudRate("9600");
        OpenComPort(ComA);
    }


    @OnClick({R.id.lianwang_bt, R.id.shexiang_bt, R.id.zhuping_bt, R.id.yinxiang_bt, R.id.pingbi_bt, R.id.zhupin_bt, R.id.fenping_bt,
            R.id.touying_bt, R.id.kongqi_bt, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.lianwang_bt:
                sendPortData(ComA,"[51000000000000@0200000]");
                break;
            case R.id.shexiang_bt:
                dialog.show();
                sendPortData(ComA,"[51000000000000@0210000]");
                break;
            case R.id.zhuping_bt:
                sendPortData(ComA,"[51000000000000@0300000]");
                dialog.show();
                break;
            case R.id.yinxiang_bt:
                volumeDialog.show();
                break;
            case R.id.pingbi_bt:
                onOffDialog.show();
                break;
            case R.id.zhupin_bt:
                onOffDialog.show();
                break;
            case R.id.fenping_bt:
                onOffDialog.show();
                break;
            case R.id.touying_bt:
                onOffDialog.show();
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
            DispQueue.AddQueue(ComRecData);//线程定时刷新显示(推荐)
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
        sMsg.append(ComRecData.sRecTime);
        sMsg.append("[");
        sMsg.append(ComRecData.sComPort);
        sMsg.append("]");
        sMsg.append("[Txt] ");
        sMsg.append(new String(ComRecData.bRec));
        sMsg.append("\r\n");
        Log.e("收到数据", sMsg.toString());

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
}
