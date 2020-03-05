package com.eeepay.activity;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eeepay.adapter.MainListAdapter;

import java.util.Arrays;


public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private String[] itemArrays = new String[]{};
    private MainListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        listView = getViewById(R.id.listview);
    }

    @Override
    protected void initEvent() {
//        itemArrays = AllUtils.getArrays(this,R.array.itemName);
        itemArrays = mContext.getResources().getStringArray(R.array.itemName);
        adapter = new MainListAdapter(mContext);
        adapter.setList(Arrays.asList(itemArrays));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                goActivity(ArcProBarActivity.class);
                break;
            case 1:
                goActivity(PoiLineActivity.class);
                break;
            case 2:
                goActivity(CameraActivtiy.class);
                break;
            case 3:
                goActivity(StartAppActivity.class);
                break;
            case 4:
                goActivity(FloatActivity.class);
                break;
            case 5:
                goActivity(WaveActivity.class);
                break;
            case 6:
                goActivity(HandWritingActivity.class);
                break;
            case 7:
                goActivity(CoverFlowActivity.class);
                break;
            case 8:
                goActivity(RoundRlAct.class);
                break;
            case 9:
                goActivity(BroLineAct.class);
                break;
            case 10:
                goActivity(BubbleActivity.class);
                break;
            case 11:
                goActivity(TestActivity.class);
                break;
            case 12:
                goActivity(WebViewActivity.class);
                break;
            case 13:
                goActivity(KotlinDemoActivity.class);
                break;
        }
    }
}
