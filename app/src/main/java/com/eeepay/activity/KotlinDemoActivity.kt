package com.eeepay.activity

import com.eeepay.adapter.KotlinGridViewAdapter
import com.eeepay.model.ItemInfo
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * 描述：Kotlin
 * 作者：ChinzLee
 * 时间：2020/3/5 10:37
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class KotlinDemoActivity : BaseActivity() {

    var adapter: KotlinGridViewAdapter? = null

    var clickCount: Int = 0
    var showText = "Kotlin is ok!!!"
    var datas: MutableList<ItemInfo> = ArrayList();

    override fun getLayoutId(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
        adapter = KotlinGridViewAdapter(this)
        for (index in 1..10) {
            var itemText: ItemInfo = ItemInfo("阿言" + index + "号");
            datas.add(itemText);
        }
        adapter!!.setList(datas);
        gridView.adapter = adapter;
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
        gridView.setOnItemClickListener { _, _, position, _ ->
            var content: String = datas[position].text
            showToast("点击了$content")
        }
    }


}
