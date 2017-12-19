package com.myrobot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myrobot.OnItemCheckListener;
import com.myrobot.R;
import com.myrobot.api.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omni20170501 on 2017/6/6.
 */

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {
    private List<Page.DataBean> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public PageAdapter(Context context) {

        data = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new PageAdapter.ViewHolder(view);
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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView device_name;
        private RelativeLayout page_ll;

        public ViewHolder(View itemView) {
            super(itemView);
            device_name = (TextView) itemView.findViewById(R.id.tv);
            page_ll = (RelativeLayout) itemView.findViewById(R.id.page_ll);


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
