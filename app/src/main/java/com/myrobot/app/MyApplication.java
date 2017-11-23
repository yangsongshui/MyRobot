package com.myrobot.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.jpble.ble.BLEService;
import com.jpble.ble.LinkBLE;
import com.jpble.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omni20170501 on 2017/6/8.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    public String deviceKey="";
    public String KEY;
    public String updateKey="";
    /**
     * 是否连接绑定了设备
     */
    public boolean isBind = false;
    public String bindMac;
    private LinkBLE linkBLE;


    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        linkBLE = new LinkBLE(this);
        SpUtils.init(this);
        //启动服务的Intent
        Intent gattServiceIntent = new Intent(this, BLEService.class);
        //启动服务
        startService(gattServiceIntent);

    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity)) {
            activitiesList.add(activity);
        }
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity)) {
            activitiesList.remove(activity);
        }
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null) {
                acty.finish();
            }
        }

    }

    /**
     * 获取蓝牙管理器对象
     */
    public LinkBLE getBleManager() {
        return linkBLE;
    }

    private String getdata(int id) {
        return getResources().getString(id);
    }
}
