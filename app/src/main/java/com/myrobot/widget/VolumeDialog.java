package com.myrobot.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.myrobot.R;


/**
 * Created by ys on 2017/9/1.
 */

public class VolumeDialog extends Dialog implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    ImageButton zhuche_back;

    private Context mContext;
    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener;
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    View.OnClickListener onClickListener;
    public VolumeDialog(@NonNull Context context) {
        super(context);
    }

    public VolumeDialog(@NonNull Context context, @StyleRes int themeResId, SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        super(context, themeResId);
        this.onSeekBarChangeListener = onSeekBarChangeListener;
    }

    protected VolumeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_volume);
        setCanceledOnTouchOutside(true);

        findViewById(R.id.zhuche_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.da).setOnClickListener(this);
        findViewById(R.id.xiao).setOnClickListener(this);
        ((SeekBar) findViewById(R.id.seekbar)).setOnSeekBarChangeListener(onSeekBarChangeListener);
        ((CheckBox) findViewById(R.id.vol_chekbox)).setOnCheckedChangeListener(this);
    }




    public void setOnClickListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(compoundButton, b);
        }
    }
    public void setOnClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    public void onClick(View view) {
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }
}
