package com.eeepay.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.eeepay.utils.LogUtils;
import com.eeepay.view.CameraView;

/**
 * Created by Ching on 2018/3/16.
 * 自定义相机类
 *
 */

public class CameraActivtiy extends BaseActivity implements View.OnClickListener {

    private Button takePicBtn;
    private CameraView cameraView;
    private String[] PERMISSION = new String[]{android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Camera camera;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView() {
        takePicBtn = getViewById(R.id.btn_pic);
        cameraView = getViewById(R.id.cameraView);
        checkPermission();
    }

    @Override
    protected void initEvent() {
        takePicBtn.setOnClickListener(this);
    }

    public void initCamera() {
        int cameras = Camera.getNumberOfCameras();
        LogUtils.d("NumberOfCameras = " + cameras + "   camera = " + openCamera(0));
        camera = openCamera(0);
        cameraView.setCamera(camera);
        cameraView.startPreviewDisplay(cameraView.getHolder());
        followScreenOrientation(this, camera);
    }

    /**
     * @param cameraId
     * @return 开启摄像头
     */
    public static Camera openCamera(int cameraId) {
        try {
            return Camera.open(cameraId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param context
     * @param camera  设置相机预览方向
     */
    public static void followScreenOrientation(Context context, Camera camera) {
        final int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {//横向
            camera.setDisplayOrientation(180);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }
    }

    public void setQuality(){

    }

    /**
     * Android6.0以后的动态权限请求
     */
    public void checkPermission() {
        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            initCamera();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
