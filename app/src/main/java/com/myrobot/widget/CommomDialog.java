package com.myrobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.myrobot.R;


/**
 * Created by ys on 2017/9/1.
 */

public class CommomDialog extends Dialog implements View.OnTouchListener {

    ImageButton zhuche_back;
    private Context mContext;
    View.OnTouchListener onTouchListener;

    public CommomDialog(@NonNull Context context) {
        super(context);
    }

    public CommomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CommomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(true);
        initView();
        findViewById(R.id.zhuche_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.shang_bt).setOnTouchListener(this);
        findViewById(R.id.you_bt).setOnTouchListener(this);
        findViewById(R.id.xia_bt).setOnTouchListener(this);
        findViewById(R.id.zuo_bt).setOnTouchListener(this);
        findViewById(R.id.shang_bt).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.e("焦点控制", hasFocus + "");
            }
        });

    }

    private void initView() {


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (onTouchListener != null)
            onTouchListener.onTouch(v, event);
        return false;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }
}
