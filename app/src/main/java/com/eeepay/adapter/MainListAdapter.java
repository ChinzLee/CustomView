package com.eeepay.adapter;

import android.content.Context;

import com.eeepay.activity.R;
import com.eeepay.utils.ABBaseAdapter;
import com.eeepay.utils.ABViewHolder;

/**
 * Created by Ching on 2017/8/16.
 */

public class MainListAdapter extends ABBaseAdapter<String> {


    public MainListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_main_listview;
    }

    @Override
    public void convert(ABViewHolder holder, String model) {
        holder.setText(R.id.tv_main, model);
    }

}
