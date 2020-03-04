package com.eeepay.model;

import android.graphics.Bitmap;

/**
 * Created by Ching on 2018/4/23.
 */

public class MediaInfo {

    private String mediaName;
    private String path;
    private Bitmap bitmap;
    private String size;

    public MediaInfo() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
