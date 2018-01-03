package com.myrobot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.api.Page;
import com.myrobot.app.MyApplication;

import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by yangsong on 2017/12/7.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    List<Page.DataBean> news;
    Context context;

    public NewsAdapter(List<Page.DataBean> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        Page.DataBean contentlistBean = news.get(position);
        holder.item_tv.setText(contentlistBean.getName());
        Log.e("onBindViewHolder", contentlistBean.toString());
        if (contentlistBean.getThumbnail().length() > 0)
            MyApplication.newInstance().getGlide().load(contentlistBean.getThumbnail()).centerCrop().into(holder.item_iv);
    }

    public class NewsViewHolder extends ViewHolder {
        ImageView item_iv;
        TextView item_tv;

        public NewsViewHolder(View itemView) {
            super(itemView);
            item_iv = (ImageView) itemView.findViewById(R.id.news_iv);
            item_tv = (TextView) itemView.findViewById(R.id.news_tv);
        }
    }

    public void setmList(List<Page.DataBean> news) {

        this.news = news;
        this.notifyDataSetChanged();
    }
}
