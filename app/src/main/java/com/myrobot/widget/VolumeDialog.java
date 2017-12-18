package com.myrobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.ImageButton;

import com.myrobot.R;


/**
 * Created by ys on 2017/9/1.
 */

public class VolumeDialog extends Dialog implements View.OnClickListener {

    ImageButton zhuche_back;
    View.OnClickListener onClickListener;
    private Context mContext;

    public VolumeDialog(@NonNull Context context) {
        super(context);
    }

    public VolumeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected VolumeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_volume);
        setCanceledOnTouchOutside(true);
        initView();
        findViewById(R.id.zhuche_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initView() {


    }

    @Override
    public void onClick(View v) {
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
        this.dismiss();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


}
