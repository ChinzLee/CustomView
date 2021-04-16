package com.eeepay.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by Ching on 2018/4/8.
 */

public class StartAppActivity extends BaseActivity implements View.OnClickListener {

    private Button button1, button2;
    private String PACKAGE_NAME = "com.eeepay.eeepay_v2_hkhb";
    private String ACTIVITY_NAME = "com.eeepay.eeepay_v2.ui.activity";



    @Override
    protected int getLayoutId() {
        return R.layout.activity_aidl;
    }

    @Override
    protected void initView() {
        button1 = getViewById(R.id.button_1);
        button2 = getViewById(R.id.button_2);
    }

    @Override
    protected void initEvent() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(PACKAGE_NAME, ACTIVITY_NAME);
                intent.setComponent(cn);
                startActivity(intent);
                break;
            case R.id.button_2:
                startAppByActName();
                break;
        }
    }

    /**
     * 通过ResolveInfo类来取得启动Acitivty的类名
     */
    public void startAppByActName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageInfo.packageName);

            List<ResolveInfo> apps = getPackageManager().queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packname = ri.activityInfo.packageName;
                String calssname = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packname, calssname);

                intent.setComponent(cn);
                startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
