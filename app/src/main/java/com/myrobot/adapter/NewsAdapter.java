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
import com.myrobot.app.MyApplication;
import com.myrobot.bean.News;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by yangsong on 2017/12/7.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    News news;
    Context context;

    public NewsAdapter(News news, Context context) {
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
        return news.getShowapi_res_body().getPagebean().getContentlist().size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        News.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = news.getShowapi_res_body().getPagebean().getContentlist().get(position);
        holder.item_tv.setText(contentlistBean.getTitle());
        Log.e("onBindViewHolder", contentlistBean.toString());
        if (contentlistBean.getImageurls().size() > 0)
            MyApplication.newInstance().getGlide().load(contentlistBean.getImageurls().get(0).getUrl()).centerCrop().into(holder.item_iv);
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

    public void setmList(News news) {

        this.news = news;
        this.notifyDataSetChanged();
    }
}
