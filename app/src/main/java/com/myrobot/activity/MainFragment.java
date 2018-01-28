package com.myrobot.activity;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.myrobot.R;
import com.myrobot.adapter.CrewAdapter;
import com.myrobot.base.BaseFragment;
import com.myrobot.bean.Zu;

import okhttp3.OkHttpClient;

/**
 * Created by yangsong on 2017/5/14.
 */
@SuppressLint("ValidFragment")
public class MainFragment extends BaseFragment implements AdapterView.OnItemLongClickListener {
    Zu.DataBean dataBean;
    ListView listView;
    TextView textView;

    CrewAdapter crewAdapter;
    OkHttpClient client;
    Gson gs;
    private ProgressDialog progressDialog;

    public MainFragment(Zu.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("加载中...");
        client = new OkHttpClient();
        gs = new Gson();
        listView = layout.findViewById(R.id.zu_lv);
        textView = layout.findViewById(R.id.zu_name);
        textView.setText(dataBean.getDepartment_name());
        crewAdapter = new CrewAdapter(dataBean.getUsers(), getActivity());
        listView.setAdapter(crewAdapter);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.item_zu;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        return false;
    }


}
