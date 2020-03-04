package com.eeepay.activity;

import com.eeepay.view.BroLineChart2;

import java.util.ArrayList;
import java.util.List;


public class BroLineAct extends BaseActivity {

    private BroLineChart2 broChart;
    private int[] datas;
    private List<String> xString = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bro_line;
    }

    @Override
    protected void initView() {
        broChart = getViewById(R.id.bro_line);
    }

    @Override
    protected void initEvent() {
        datas = new int[]{30000, 26000, 9800, 6700, 6700, 19000, 39000};
        xString.add("05/09");
        xString.add("05/10");
        xString.add("05/11");
        xString.add("05/12");
        xString.add("05/13");
        xString.add("05/14");
        xString.add("05/15");
        broChart.setMax(40000);
        broChart.setDatas(datas);
        broChart.setXString(xString);
        broChart.setShowGrid(true);
    }
}
