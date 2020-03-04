package com.eeepay.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ching on 2018/3/16.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;

    public CameraView(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }


    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
    }


    /**
     * @param camera
     */
    public void setCamera(Camera camera) {
        mCamera = camera;
        if (mCamera == null)
            return;
        final Camera.Parameters params = mCamera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        params.setSceneMode(Camera.Parameters.SCENE_MODE_BARCODE);
        setPreviewSize();
    }

    /**
     * 设置预览大小
     */
    private void setPreviewSize() {
        Camera.Parameters parameters = getCameraParameters();
        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Camera.Size currentSize = null;
        Camera.Size nextSize = null;
        for (int i = 0; i < previewSizes.size() - 1; i++) {
            currentSize = previewSizes.get(i);
            nextSize = previewSizes.get(i + 1);

            if (currentSize.width < nextSize.width) {
                currentSize = nextSize;
            }
        }
        parameters.setPreviewSize(currentSize.width, currentSize.height);
        mCamera.setParameters(parameters);
    }


    /**
     * @return 获取相机参数
     */
    private Camera.Parameters getCameraParameters() {
        Camera.Parameters parameters = null;
        if (mCamera != null)
            parameters = mCamera.getParameters();
        return parameters;
    }

    /**
     * @param camera
     * @param parameters 设置相机参数
     */
    private void setParameters(Camera camera, Camera.Parameters parameters) {
        if (camera != null)
            camera.setParameters(parameters);
    }

    /**
     * @param mSurfaceHolder 开启预览
     */
    public void startPreviewDisplay(SurfaceHolder mSurfaceHolder) {
        checkCamera();
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止预览
     */
    public void stopPreviewDisplay() {
        checkCamera();
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCamera() {
        if (mCamera == null) {
            throw new IllegalStateException("Camera must be set when start/stop preview, call <setCamera(Camera)> to set");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreviewDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }
//        Cameras.followScreenOrientation(getContext(), mCamera);
        stopPreviewDisplay();
        startPreviewDisplay(mSurfaceHolder);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreviewDisplay();
    }

}
