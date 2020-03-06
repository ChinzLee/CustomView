package com.eeepay.adapter

import android.content.Context
import com.eeepay.activity.R
import com.eeepay.model.ItemInfo
import com.eeepay.utils.ABBaseAdapter
import com.eeepay.utils.ABViewHolder

/**
 * 描述：Kotlin学习demo Gridview
 * 作者：ChinzLee
 * 时间：2020/3/6 17:38
 * 邮箱：ljq@eeepay.cn
 * 备注:
 */
class KotlinGridViewAdapter(context: Context?) : ABBaseAdapter<ItemInfo>(context) {

    override fun getLayoutId(): Int {
        return R.layout.item_kotlin_gridview
    }

    override fun convert(holder: ABViewHolder?, model: ItemInfo?) {
        holder?.setText(R.id.content, model?.text)
    }
}