package com.myrobot.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.myrobot.activity.MainFragment;
import com.myrobot.bean.Zu;

import java.util.List;

/**
 * Created by yangsong on 2017/12/7.
 */

public class ZuAdapter extends FragmentStatePagerAdapter {
    List<Zu.DataBean> news;


    public ZuAdapter(FragmentManager fm, List<Zu.DataBean> news) {
        super(fm);
        this.news = news;
    }

    @Override
    public Fragment getItem(int position) {
        return new MainFragment(news.get(position));

    }

    @Override
    public int getCount() {
        return news.size();
    }


    public void setCount(List<Zu.DataBean> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    /*继承FragmentStatePagerAdapter重写改方法 数据和页面可以动态更改*/
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
