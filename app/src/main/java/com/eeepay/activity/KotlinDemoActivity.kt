package com.eeepay.activity

import android.widget.TextView

/**
 * 描述：Kotlin
 * 作者：ChinzLee
 * 时间：2020/3/5 10:37
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class KotlinDemoActivity : BaseActivity() {

    var textView: TextView? = null;

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin;
    }

    override fun initView() {
        textView = getViewById(R.id.textview);
        textView?.text ="Kotlin Demo";
    }

    override fun initEvent() {
    }


}