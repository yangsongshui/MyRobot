package com.myrobot.app;

import android.app.Activity;
import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.inpor.fmctv.application.HstApplication;
import com.myrobot.api.User;
import com.myrobot.utils.AppContextUtil;
import com.myrobot.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omni20170501 on 2017/6/8.
 */

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    private User user;


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
        AppContextUtil.init(this);
        SpUtils.init(this);
        HstApplication.initHstApplication(getApplicationContext());


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

    public RequestManager getGlide() {

        return Glide.with(this);


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
