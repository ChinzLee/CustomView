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
        switch (itemArrays[position]) {
            case "ArcProgressBar":
                goActivity(ArcProBarActivity.class);
                break;
            case "PointLineView":
                goActivity(PoiLineActivity.class);
                break;
            case "WaveView":
                goActivity(WaveActivity.class);
                break;
            case "HandWritingView":
                goActivity(HandWritingActivity.class);
                break;
            case "RoundRelativeLayout":
                goActivity(RoundRlAct.class);
                break;
            case "BroLine":
                goActivity(BroLineAct.class);
                break;
            case "BubbleView":
                goActivity(BubbleActivity.class);
                break;
//            case 7:
//                goActivity(CoverFlowActivity.class);
//                break;
//            case 8:
//                goActivity(RoundRlAct.class);
//                break;
//            case 9:
//                goActivity(BroLineAct.class);
//                break;
//            case 10:
//                goActivity(BubbleActivity.class);
//                break;
//            case 11:
//                goActivity(TestActivity.class);
//                break;
//            case 12:
//                goActivity(WebViewActivity.class);
//                break;
            case "kotlin":
                goActivity(KotlinDemoActivity.class);
                break;
        }
    }
}
