package com.eeepay.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ching on 2018/4/27.
 */

public class ToastUtils {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
