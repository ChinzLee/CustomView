package com.eeepay.activity;

import com.eeepay.view.BroLineChart;

/**
 * Created by Ching on 2017/8/16.
 */

public class PoiLineActivity extends BaseActivity {

    private BroLineChart broLineChart;
    private int[] datas;
    private String[] xString;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_point_line;
    }

    @Override
    protected void initView() {
        broLineChart = getViewById(R.id.pointLineView);
    }

    @Override
    protected void initEvent() {
        broLineChart.setMax(80000);
//        pointLineView.showGrid(true);
        datas = new int[]{30000, 34000, 44000, 36000,42000, 41500, 80000};
        xString  = new String[]{"10/29","10/30","10/31","11/01","11/02","11/03","11/04"};
        broLineChart.setDatas(datas);
        broLineChart.setXString(xString);
    }
}
