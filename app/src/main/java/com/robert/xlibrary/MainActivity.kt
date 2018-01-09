package com.robert.xlibrary

import android.os.Bundle
import soft.robert.com.xlibrary.base.BaseActivity

class MainActivity : BaseActivity() {
    override val isScale: Boolean
        get() = true
    override val isImmersion: Boolean
        get() = false
    override val isScreenNormal: Boolean
        get() = true

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


}
