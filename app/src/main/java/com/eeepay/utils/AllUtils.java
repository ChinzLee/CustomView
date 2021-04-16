package com.eeepay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.*;
import android.text.TextUtils;

/**
 * Created by Ching on 2017/8/15.
 */

public class AllUtils {


    public static int getResColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }


    /**
     * @param context
     * @param arrayId
     * @return
     */
    public static int[] getArrays(Context context, int arrayId) {
        try {
            TypedArray ar = context.getResources().obtainTypedArray(arrayId);
            int len = ar.length();
            int[] resIds = new int[len];
            for (int i = 0; i < len; i++)
                resIds[i] = ar.getResourceId(i, 0);
            ar.recycle();
            return resIds;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @param permissions Android6.0以后的动态权限请求
     */
    public static void setPermissions(Context context, String[] permissions) {
        try {
            if (permissions == null && permissions.length == 0)
                return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (String permission : permissions) {
                    if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permission)) {
                        ActivityCompat.requestPermissions((Activity) context, permissions, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param permission Android6.0以后的动态权限请求
     */
    public static void setSinglePermission(Context context, String permission) {
        try {
            if (TextUtils.isEmpty(permission))
                return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(context, permission)) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param permission Android6.0 特殊权限
     *                   SYSTEM_ALERT_WINDOW，设置悬浮窗，进行一些黑科技
     *                   WRITE_SETTINGS 修改系统设置
     */
    public static void setSpcPermission(Context context, String permission) {
        try {
            if (TextUtils.isEmpty(permission))
                return;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(context)) {
                    context.startActivity(new Intent(permission));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
