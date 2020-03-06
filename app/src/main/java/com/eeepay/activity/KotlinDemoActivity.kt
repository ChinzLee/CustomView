package com.eeepay.activity

import com.eeepay.adapter.KotlinGridViewAdapter

/**
 * 描述：Kotlin
 * 作者：ChinzLee
 * 时间：2020/3/5 10:37
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class KotlinDemoActivity : BaseActivity() {

    var adapter : KotlinGridViewAdapter? = null

    var clickCount: Int = 0
    var showText = "Kotlin is ok!!!"

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
        adapter = KotlinGridViewAdapter(this)
    }

    override fun initEvent() {
//        tv_onclick?.setOnClickListener {
//            showText = when (clickCount) {
//                0 -> "start"
//                1 -> "1"
//                2 -> "2"
//                3 -> "3"
//                4 -> "4"
//                5 -> "5"
//                else -> "stop"
//            }
//            ToastUtils.showToast(this, showText)
//            clickCount++
//        }

    }


}
