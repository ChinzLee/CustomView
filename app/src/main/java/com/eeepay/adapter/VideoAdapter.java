package com.eeepay.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.eeepay.activity.R;
import com.eeepay.model.MediaInfo;
import com.eeepay.utils.ABBaseAdapter;
import com.eeepay.utils.ABViewHolder;

/**
 * Created by Ching on 2018/4/23.
 */

public class VideoAdapter extends ABBaseAdapter<MediaInfo> {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_video_list;
    }

    @Override
    public void convert(ABViewHolder holder, MediaInfo model) {
        holder.setText(R.id.tv_title, model.getMediaName());
        holder.setText(R.id.tv_path, model.getPath());
        holder.setBitmap(R.id.iv_thumb, model.getBitmap());
        holder.setText(R.id.tv_size, model.getSize()+"M");
    }


}
