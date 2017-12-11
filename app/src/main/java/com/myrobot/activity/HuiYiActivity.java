package com.myrobot.activity;

import android.content.Intent;
import android.os.Bundle;

import com.inpor.fastmeetingcloud.ui.StartTheMiddleTierActivity;
import com.myrobot.R;
import com.myrobot.base.BaseActivity;

public class HuiYiActivity extends BaseActivity {


    String BUNDLE_USERNAME = "userName";
    String BUNDLE_PASSWORD = "userPwd";
    String BUNDLE_SERVER_ADDRESS = "svrAddress";
    String BUNDLE_SERVER_PORT = "svrPort";
    String BUNDLE_SERVER_ROOMID = "roomId";
    String BUNDLE_USERID = "userId";
    String BUNDLE_NICKNAME = "nickname";

    @Override
    protected int getContentView() {
        return R.layout.activity_hui_yi;
    }

    @Override
    protected void init() {
        String userName = "wel00";
        String password = "123456";
        String port = "1089";
        String serverAddress = "a.fsmeeting.com";
        String userId = "10005";
        Intent intent = new Intent(HuiYiActivity.this, StartTheMiddleTierActivity.class);

        intent.setAction("intent_app_action");
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_USERNAME, userName);
        bundle.putString(BUNDLE_PASSWORD, password);
        bundle.putString(BUNDLE_SERVER_PORT, port);
        bundle.putString(BUNDLE_SERVER_ADDRESS, serverAddress);
        bundle.putString(BUNDLE_USERID, userId);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
