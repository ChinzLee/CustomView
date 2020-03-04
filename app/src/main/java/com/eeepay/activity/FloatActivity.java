package com.eeepay.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eeepay.adapter.VideoAdapter;
import com.eeepay.model.MediaInfo;
import com.eeepay.service.FloatService;
import com.eeepay.utils.Constants;
import com.eeepay.utils.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ching on 2018/4/10.
 * 悬浮窗
 */

public class FloatActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private List<MediaInfo> datas = new ArrayList<MediaInfo>();

    private ListView listView;
    private VideoAdapter adapter = null;

    private final int MSG_START = 0x00;
    private final int MSG_END = 0x01;

    private Message message = null;

    private Intent intent = null;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dismissProgressDialog();
            showToast(">>>>>>END<<<<<<");
            adapter = new VideoAdapter(mContext);
            adapter.setList(datas);
            listView.setAdapter(adapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acitivity_float_dialog;
    }

    @Override
    protected void initView() {
        listView = getViewById(R.id.listview);
    }

    @Override
    protected void initEvent() {
        listView.setOnItemClickListener(this);
        getVideo();
    }


    private void getVideo() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                showProgressDialog(">>>>>>搜索中<<<<<<");
            }

            @Override
            protected Void doInBackground(Void... params) {
//                file.listFiles(new FileFilter() {
//                    @Override
//                    public boolean accept(File file) {
//
//                        String name = file.getName();
//                        int i = name.indexOf(".");
//                        if (i != -1) {
//                            name = name.substring(i);
//                            if (name.equalsIgnoreCase(".mp4")
////                            || name.equalsIgnoreCase(".3gp") || name.equalsIgnoreCase(".wmv")
////                            || name.equalsIgnoreCase(".ts") || name.equalsIgnoreCase(".rmvb")
////                            || name.equalsIgnoreCase(".mov") || name.equalsIgnoreCase(".m4v")
////                            || name.equalsIgnoreCase(".avi") || name.equalsIgnoreCase(".m3u8")
////                            || name.equalsIgnoreCase(".3gpp") || name.equalsIgnoreCase(".3gpp2")
////                            || name.equalsIgnoreCase(".mkv") || name.equalsIgnoreCase(".flv")
////                            || name.equalsIgnoreCase(".divx") || name.equalsIgnoreCase(".f4v")
////                            || name.equalsIgnoreCase(".rm") || name.equalsIgnoreCase(".asf")
////                            || name.equalsIgnoreCase(".ram") || name.equalsIgnoreCase(".mpg")
////                            || name.equalsIgnoreCase(".v8") || name.equalsIgnoreCase(".swf")
////                            || name.equalsIgnoreCase(".m2v") || name.equalsIgnoreCase(".asx")
////                            || name.equalsIgnoreCase(".ra") || name.equalsIgnoreCase(".ndivx")
////                            || name.equalsIgnoreCase(".xvid")
//                                    ) {
//                                MediaInfo video = new MediaInfo();
//                                file.getUsableSpace();
//                                video.setMediaName(file.getName());
//                                video.setPath(file.getAbsolutePath());
//                                video.setBitmap(FileUtils.getVideoThumbNail(file.getAbsolutePath()));
//                                datas.add(video);
//                                return true;
//                            }
//                        } else if (file.isDirectory()) {
//                            accept(file);
//                        }
//                        return false;
//                    }
//                });
                getVideoFile(datas, Environment.getExternalStorageDirectory());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                datas = filterVideo(datas);
                mHandler.sendEmptyMessage(1);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void getVideoFile(final List<MediaInfo> datas, final File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {

                String name = file.getName();
                int i = name.indexOf(".");
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4") && file.length() > 10485760
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
                        video.setSize(String.valueOf((file.length() / (1024 * 1024))));
                        datas.add(video);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(datas, file);
                }
                return false;
            }
        });
    }


    /**
     * @param videoList
     * @return
     */
    private List<MediaInfo> filterVideo(List<MediaInfo> videoList) {
        List<MediaInfo> newVideos = new ArrayList<MediaInfo>();
        for (MediaInfo mediaInfo : newVideos) {
            File f = new File(mediaInfo.getPath());
            if (f.exists() && f.isFile() && f.length() > 10485760) {//大于10M
                newVideos.add(mediaInfo);
            }
        }
        return newVideos;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent = new Intent();
        intent.putExtra(Constants.URL, ((MediaInfo) parent.getAdapter().getItem(position)).getPath());
        intent.setClass(this, FloatService.class);
        startService(intent);
    }

//    /**
//     * 接受消息和处理消息
//     */
//    class UIHandler extends Handler {
//
//        public UIHandler() {
//        }
//
//        public UIHandler(Looper looper) {
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MSG_START:
//                    showToast("START");
//                    break;
//                case MSG_END:
//                    showToast("END");
//                    adapter.setList(filterVideo(datas));
//                    listView.setAdapter(adapter);
//                    break;
//                default:
//                    super.handleMessage(msg);
//                    break;
//            }
//        }
//    }
}
