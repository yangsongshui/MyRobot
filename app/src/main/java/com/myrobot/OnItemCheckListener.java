package com.myrobot;

import android.support.v7.widget.RecyclerView;


/**
 * RecyclerView item监听
 * Created by omni20170501 on 2017/6/7.
 */

public interface OnItemCheckListener {
    void OnItemCheck(RecyclerView.ViewHolder viewHolder, int position);

}
