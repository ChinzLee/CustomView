package com.eeepay.model

import android.widget.TextView
import com.eeepay.impl.Subject

/**
 * 描述：
 * 作者：ChinzLee
 * 时间: 2021/4/15 15:56
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class RealSubject(var textView: TextView) : Subject {

    override fun coding() {
        textView.text = "静态 --> coding"
    }

    override fun debug() {
        textView.text = "静态 --> debug"
    }
}