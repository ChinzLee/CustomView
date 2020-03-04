package com.eeepay.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.eeepay.adapter.VideoAdapter;
import com.eeepay.model.MediaInfo;
import com.eeepay.utils.FileUtils;
import com.eeepay.utils.ToastUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ching on 2018/4/23.
 */

public class ScannerAsyncTask extends AsyncTask<Void, Integer, List<MediaInfo>> {

    private List<MediaInfo> datas = new ArrayList<MediaInfo>();
    private VideoAdapter adapter = null;
    private Context context;

    public ScannerAsyncTask(List<MediaInfo> datas, VideoAdapter adapter, Context context) {
        super();
        this.datas = datas;
        this.adapter = adapter;
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ToastUtils.showToast(context, "......START......");
    }

    @Override
    protected List<MediaInfo> doInBackground(Void... params) {
        datas = getVideoFile(datas, Environment.getExternalStorageDirectory());
        return datas;
    }


    private List<MediaInfo> getVideoFile(final List<MediaInfo> datas, File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                int i = name.indexOf(".");
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4")
//                            || name.equalsIgnoreCase(".3gp") || name.equalsIgnoreCase(".wmv")
//                            || name.equalsIgnoreCase(".ts") || name.equalsIgnoreCase(".rmvb")
//                            || name.equalsIgnoreCase(".mov") || name.equalsIgnoreCase(".m4v")
//                            || name.equalsIgnoreCase(".avi") || name.equalsIgnoreCase(".m3u8")
//                            || name.equalsIgnoreCase(".3gpp") || name.equalsIgnoreCase(".3gpp2")
//                            || name.equalsIgnoreCase(".mkv") || name.equalsIgnoreCase(".flv")
//                            || name.equalsIgnoreCase(".divx") || name.equalsIgnoreCase(".f4v")
//                            || name.equalsIgnoreCase(".rm") || name.equalsIgnoreCase(".asf")
//                            || name.equalsIgnoreCase(".ram") || name.equalsIgnoreCase(".mpg")
//                            || name.equalsIgnoreCase(".v8") || name.equalsIgnoreCase(".swf")
//                            || name.equalsIgnoreCase(".m2v") || name.equalsIgnoreCase(".asx")
//                            || name.equalsIgnoreCase(".ra") || name.equalsIgnoreCase(".ndivx")
//                            || name.equalsIgnoreCase(".xvid")
                            ) {
                        MediaInfo video = new MediaInfo();
                        file.getUsableSpace();
                        video.setMediaName(file.getName());
                        video.setPath(file.getAbsolutePath());
                        video.setBitmap(FileUtils.getVideoThumbNail(file.getAbsolutePath()));
                        datas.add(video);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(datas, file);
                }
                return false;
            }
        });
        return datas;
    }

    @Override
    protected void onPostExecute(List<MediaInfo> mediaInfos) {
        super.onPostExecute(mediaInfos);
        adapter.setList(mediaInfos);
        ToastUtils.showToast(context, "......END......");
    }
}
