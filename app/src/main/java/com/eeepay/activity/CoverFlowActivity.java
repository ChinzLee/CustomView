package com.eeepay.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eeepay.adapter.CoverFlowAdapter;

import java.util.ArrayList;


public class CoverFlowActivity extends BaseActivity {

    private RecyclerView flowView;

    private CoverFlowAdapter adapter;
    private ArrayList<String> mDatas;
    private LinearLayoutManager layoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cover_flow;
    }

    @Override
    protected void initView() {
        flowView = getViewById(R.id.coverFlowView);
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas.add("图片" + i + 1);
        }
        adapter = new CoverFlowAdapter(mContext, mDatas);
        layoutManager = new LinearLayoutManager(this);
        flowView.setLayoutManager(new LinearLayoutManager(this));
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        flowView.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {

    }
}
