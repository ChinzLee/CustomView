package com.eeepay.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;

import java.io.File;

/**
 * Created by Ching on 2018/4/19.
 * 文件工具类
 */

public class FileUtils {


    /**
     * @param filePath
     * @return 根据路径获取视频的预览图
     */
    public static Bitmap getVideoThumbNail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bitmap;
    }

    /**
     * @return
     * 获取SD卡目录
     */
    public static String getSDPath() {
        File sdDir = null;
        try {
            boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdDir.toString();
    }

}
