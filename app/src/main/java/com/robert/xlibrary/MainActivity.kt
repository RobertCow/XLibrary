package com.robert.xlibrary

import android.os.Bundle
import kotlinx.android.synthetic.main.titlebar.*
import soft.robert.com.xlibrary.base.BaseActivity

class MainActivity : BaseActivity() {
    override val noScale: Boolean
        get() = false//To change body of created functions use File | Settings | File Templates.
    override val isLandscape: Boolean
        get() = false //To change body of created functions use File | Settings | File Templates.
    override val isImmersion: Boolean
        get() = false

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv_title.text
    }


}
