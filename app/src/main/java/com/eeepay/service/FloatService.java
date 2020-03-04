package com.eeepay.service;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.eeepay.activity.R;
import com.eeepay.adapter.VideoAdapter;
import com.eeepay.model.MediaInfo;
import com.eeepay.utils.AppManager;
import com.eeepay.utils.Constants;
import com.eeepay.utils.DialogUtil;
import com.eeepay.utils.FileUtils;
import com.eeepay.view.CustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ching on 2018/4/10.
 * 悬浮
 */

public class FloatService extends Service implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener {

    private static final int UPDATE_PIC = 0x100;
    private int statusBarHeight;// 状态栏高度
    private boolean isAdded = false;

    //    private Button button;
//    private TextView textView;
    private View view;

    private Display mDisplay;
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer player;

    private WindowManager windowManager = null;
    private WindowManager.LayoutParams layoutParams;

    private float mRawX;
    private float mRawY;
    private float mStartX;
    private float mStartY;
    private float moveDistance = 10;
    private String path = null;

    private List<MediaInfo> datas = new ArrayList<MediaInfo>();
    private VideoAdapter adapter = null;

    @Override
    public void onCreate() {
        super.onCreate();
        createFloatView();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (intent != null) {
            path = intent.getStringExtra(Constants.URL);
        }
        refresh();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void createFloatView() {

        view = LayoutInflater.from(this).inflate(R.layout.service_float_view, null);
        //给SurfaceView添加CallBack监听
        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        holder = surfaceView.getHolder();
        holder.addCallback((SurfaceHolder.Callback) AppManager.getAppManager().currentActivity());
        //为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(FileUtils.getSDPath());

        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        DisplayMetrics d = getApplicationContext().getResources().getDisplayMetrics();

        layoutParams = new WindowManager.LayoutParams(d.widthPixels,
                (int) (d.heightPixels * 0.5), LayoutParams.TYPE_SYSTEM_ERROR,
                LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT | LayoutParams.FLAG_NOT_TOUCH_MODAL);

        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                mRawX = event.getRawX();
                mRawY = event.getRawY();
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN://按下事件，记录按下时手指在悬浮窗的XY坐标值
                        mStartX = event.getX();
                        mStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        refreshView();
                        break;
                }
                return true;
            }
        });
        initMediaPlayer();
    }

//    private void startTask() {
//        customDialog = DialogUtil.getHorizontalProgressDialog(AppManager.getAppManager().currentActivity());
//        adapter = new VideoAdapter(getApplicationContext());
//        ScannerAsyncTask scannerAsyncTask = new ScannerAsyncTask(datas, adapter,AppManager.getAppManager().currentActivity());
//        scannerAsyncTask.execute();
//    }

    /**
     * 初始化MediaPlayer
     */
    private void initMediaPlayer() {
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);

        try {
            player.setDataSource(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mDisplay =
    }


    /**
     * 刷新悬浮窗
     */
    private void refreshView() {
        // 状态栏高度不能立即取，不然得到的值是0
        if (statusBarHeight == 0) {
            View rootView = view.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }
        layoutParams.x = (int) (mRawX - mStartX);
        // y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
        layoutParams.y = (int) (mRawY - mStartY - statusBarHeight);
        refresh();
    }

    /**
     *
     */
    private void refresh() {
        if (isAdded) {
            windowManager.updateViewLayout(view, layoutParams);
        } else {
            windowManager.addView(view, layoutParams);
            isAdded = true;
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

    }


    /**
     * 接受消息和处理消息
     */
    class UIHandler extends Handler {

        public UIHandler() {
        }

        public UIHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_PIC) {
//                textView.setText(SysInfoUtils.getTotalRam());
                refresh();
            } else {
                super.handleMessage(msg);
            }
        }
    }


}
