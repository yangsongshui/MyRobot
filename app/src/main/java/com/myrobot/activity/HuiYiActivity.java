package com.myrobot.activity;

import android.content.ComponentName;
import android.content.Intent;

import com.inpor.fastmeetingcloud.ui.StartTheMiddleTierActivity;
import com.inpor.fastmeetingcloud.util.Constant;
import com.myrobot.R;
import com.myrobot.base.BaseActivity;

public class HuiYiActivity extends BaseActivity {
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
        ComponentName apk2Component1 = new ComponentName("com.myrobot", "com.inpor.fastmeetingcloud.ui.StartTheMiddleTierActivity");
        Intent mIntent = new Intent();
        mIntent.setAction(Constant.INTENT_APP_ACTION);
        mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_USERNAME, userName);  //必传
        mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_PASSWORD, password);//必传
       // mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_ROOMID, et_roomid.getText().toString().trim());//选传
       // mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_ROOMPASSWD, "f1234567890");//选传
        mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_SERVER_ADDRESS, serverAddress);//必传
        mIntent.putExtra(StartTheMiddleTierActivity.ThirdLoginConstant.BUNDLE_SERVER_PORT, port);//必传
        mIntent.setComponent(apk2Component1);
        startActivity(mIntent);
        finish();
    }
}
