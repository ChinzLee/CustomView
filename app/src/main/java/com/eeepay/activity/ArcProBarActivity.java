package com.eeepay.activity;

import com.eeepay.view.ArcProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ching on 2017/8/15.
 */

public class ArcProBarActivity extends BaseActivity {

    private ArcProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_arc_progress_bar;
    }

    @Override
    protected void initView() {
        progressBar = getViewById(R.id.progressBar);
    }

    @Override
    protected void initEvent() {
        progressBar = getViewById(R.id.progressBar);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBar.getProgress() < 85) {
                            progressBar.setProgress(progressBar.getProgress() + 1);
                        }
                    }
                });
            }
        }, 0,15);
    }
}
