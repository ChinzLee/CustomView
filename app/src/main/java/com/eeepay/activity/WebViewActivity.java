package com.eeepay.activity;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 描述：
 * 作者：ChinzLee
 * 时间：2019/11/27 9:oneplus_49
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        mWebView = getViewById(R.id.webview);
    }

    @Override
    protected void initEvent() {
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setBuiltInZoomControls(true);

//        mWebView.setWebChromeClient(new WebChromeClient());

//        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();//接受证书
                //handleMessage(Message msg); 其他处理
            }
        });

//        mWebView.loadUrl("https://www.baidu.com/");//添加浏览器地址
        mWebView.loadUrl("https://web.bitus.com/");//添加浏览器地址
    }
}
