package com.eeepay.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.eeepay.utils.AppManager;
import com.eeepay.utils.DialogUtil;
import com.eeepay.view.CustomDialog;

/**
 * Created by Ching on 2017/8/building.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected Activity mContext;
    protected CustomDialog progressDialog;
    private boolean cancelable = true;//返回键是否可以隐藏菊花  默认为true


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        initView();
        initEvent();
        AppManager.getAppManager().addActivity(this);
    }

    /**
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     *
     */
    protected abstract void initView();

    /**
     *
     */
    protected abstract void initEvent();

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

    public void goActivity(Class<?> descClass) {
        goActivityForResult(descClass, null, -1);
    }

    public void goActivity(Class<?> descClass, Bundle bundle) {
        goActivityForResult(descClass, bundle, -1);
    }

    public void goActivityForResult(Class<?> descClass, int requestCode) {
        switchActivity(mContext, descClass, null, requestCode);
    }

    public void goActivityForResult(Class<?> descClass, Bundle bundle, int requestCode) {
        switchActivity(mContext, descClass, bundle, requestCode);
    }

    public void switchActivity(Context context, Class<?> descClass, Bundle bundle, int requestCode) {
        if (context.getClass() == descClass)
            return;
        try {
            Intent intent = new Intent();
            intent.setClass(context, descClass);
            if (bundle != null)
                intent.putExtras(bundle);
            ((Activity) context).startActivityForResult(intent, requestCode);
            ((Activity) context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } catch (Exception e) {
        }
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = DialogUtil.getHorizontalProgressDialog(mContext);
            progressDialog.setCancelable(cancelable);
        }
        progressDialog.show();
    }

    public void showProgressDialog(String string) {
        if (progressDialog == null) {
            progressDialog = DialogUtil.getHorizontalProgressDialog(mContext, string);
            progressDialog.setCancelable(cancelable);
        }
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * @param text Toast提示
     */
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
