package com.myrobot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myrobot.OnItemCheckListener;
import com.myrobot.R;
import com.myrobot.api.Page;
import com.myrobot.app.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omni20170501 on 2017/6/6.
 */

public class PageAdapter2 extends RecyclerView.Adapter<PageAdapter2.ViewHolder> {
    private List<Page.DataBean> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public PageAdapter2(Context context) {

        data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page2, parent, false);
        return new PageAdapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Page.DataBean bleDevice = data.get(position);
        holder.page_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemCheckListener != null) {
                    onItemCheckListener.OnItemCheck(holder, position);
                }
            }
        });
        holder.device_name.setText(bleDevice.getName());
        if (bleDevice.getThumbnail().length() > 0)
            MyApplication.newInstance().getGlide().load(bleDevice.getThumbnail()).centerCrop().into(holder.page_iv);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView device_name;
        private ImageView page_iv;
        private LinearLayout page_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            device_name = (TextView) itemView.findViewById(R.id.page2_tv);
            page_ll = (LinearLayout) itemView.findViewById(R.id.page2_ll2);
            page_iv = (ImageView) itemView.findViewById(R.id.page_iv);


        }
    }

    public void setItems(List<Page.DataBean> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void clearDevice() {
        data.clear();
        notifyDataSetChanged();
    }


    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }


}
