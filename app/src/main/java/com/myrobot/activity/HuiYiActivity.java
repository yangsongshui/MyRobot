package com.myrobot.activity;

import com.myrobot.R;
import com.myrobot.base.BaseActivity;

public class HuiYiActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_hui_yi;
    }

    @Override
    protected void init() {
       /* String userName = "wel03";
        String password = "123456";
        String serverAddress = "a.fsmeeting.com";
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setAction(StaticString.ACTION_PRIVACY_ACCOUNT);
        intent.putExtra(StaticString.INTENT_LINK_USERNAME, userName); //必传
        intent.putExtra(StaticString.INTENT_LINK_USERPWD, password);//必传 

        intent.putExtra(StaticString.INTENT_LINK_SERVER, new String[]{serverAddress + ":1089"}); //必传
        startActivity(intent);
        finish();*/
    }
}
