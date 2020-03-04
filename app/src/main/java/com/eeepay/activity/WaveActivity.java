package com.eeepay.activity;

import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.eeepay.utils.LogUtils;
import com.eeepay.view.WaveView;

public class WaveActivity extends BaseActivity {

    private WaveView waveView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_waveview;
    }

    @Override
    protected void initView() {
        waveView = getViewById(R.id.waveView);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) waveView.getLayoutParams();
        LogUtils.d(" height = " + dm.heightPixels);
        layoutParams.height = dm.heightPixels / 2;
        waveView.setLayoutParams(layoutParams);
    }

    @Override
    protected void initEvent() {

    }

}
