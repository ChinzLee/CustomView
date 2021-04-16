package com.eeepay.activity

import android.widget.TextView
import butterknife.BindView
import com.eeepay.impl.Subject
import com.eeepay.model.RealSubject

/**
 * 描述：Kotlin
 * 作者：ChinzLee
 * 时间：2020/3/5 10:37
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class KotlinDemoActivity : BaseActivity() {

//    val tv1 : TextView by BindView(R.id.textView1);
//
//    @BindView(R.id.textView2)
//    lateinit var tv2: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
//        var subject = RealSubject(tv1)
    }

    override fun initEvent() {

    }

}
