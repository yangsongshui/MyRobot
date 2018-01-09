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

import com.myrobot.R;


/**
 * Created by ys on 2017/9/1.
 */

public class OnOffDialog extends Dialog implements CompoundButton.OnCheckedChangeListener {

    ImageButton zhuche_back;
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    private Context mContext;

    public OnOffDialog(@NonNull Context context) {
        super(context);
    }

    public OnOffDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected OnOffDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_onoff);
        setCanceledOnTouchOutside(true);

        findViewById(R.id.zhuche_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((CheckBox) findViewById(R.id.onoff_check)).setOnCheckedChangeListener(this);
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
}
