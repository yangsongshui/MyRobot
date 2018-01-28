package com.myrobot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myrobot.R;
import com.myrobot.base.MyBaseAdapter;
import com.myrobot.bean.Zu;

import java.util.List;

/**
 * Created by yangsong on 2018/1/28.
 */

public class CrewAdapter extends MyBaseAdapter<Zu.DataBean.UsersBean> {
    Context context;
    List<Zu.DataBean.UsersBean> list;

    public CrewAdapter(List<Zu.DataBean.UsersBean> list, Context context) {
        super(list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_crew, parent, false);
            holder = new NewsViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NewsViewHolder) convertView.getTag();
        }

        holder.crew_name.setText(list.get(position).getName());
        return convertView;

    }

    public class NewsViewHolder {

        TextView crew_name;

        public NewsViewHolder(View itemView) {

            crew_name = itemView.findViewById(R.id.crew_name);

        }
    }
}
