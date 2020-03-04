package com.eeepay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eeepay.activity.R;

import java.util.ArrayList;


public class CoverFlowAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;
    private int mCreateHolder = 0;
    private int[] mPics = new int[]{R.drawable.item_1, R.drawable.item_2, R.drawable.item_3,
            R.drawable.item_4, R.drawable.item_5, R.drawable.item_6};

    public CoverFlowAdapter(Context mContext, ArrayList<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mCreateHolder ++;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CoverHolder(inflater.inflate(R.layout.item_cover_flow,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CoverHolder mHolder = (CoverHolder) holder;
        mHolder.mTV.setText(mDatas.get(position));
        mHolder.mImg.setImageDrawable(mContext.getResources().getDrawable(mPics[position%mPics.length]));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class CoverHolder extends ViewHolder{

        public TextView mTV;
        public ImageView mImg;

        public CoverHolder(View itemView) {
            super(itemView);
            mTV = (TextView) itemView.findViewById(R.id.tv_title);

            mImg = (ImageView)itemView.findViewById(R.id.iv_img);
        }
    }
}
