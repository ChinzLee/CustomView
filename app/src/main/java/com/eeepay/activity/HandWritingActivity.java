package com.eeepay.activity;

import android.view.View;
import android.widget.Button;

import com.eeepay.view.HandWritingView;

public class HandWritingActivity extends BaseActivity {

    private Button btnReset;
    private HandWritingView handWritingView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hand_writing;
    }

    @Override
    protected void initView() {
        btnReset = getViewById(R.id.btn_reset);
        handWritingView = getViewById(R.id.hwview);
    }

    @Override
    protected void initEvent() {
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handWritingView.reset();
            }
        });
    }
}
