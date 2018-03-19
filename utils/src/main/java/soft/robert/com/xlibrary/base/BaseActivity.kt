package soft.robert.com.xlibrary.base

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.gyf.barlibrary.ImmersionBar
import java.util.HashMap
import soft.robert.com.utils.R
import soft.robert.com.xlibrary.utils.BToast
import soft.robert.com.xlibrary.utils.LogUtils
import soft.robert.com.xlibrary.utils.SupportMultipleScreensUtil
import soft.robert.com.xlibrary.utils.Utils
import java.lang.Exception


/**
 * Created by CC on 2017/5/3.
 */

abstract class BaseActivity : Activity() {
    var map: Map<String, String> = HashMap()
    private var rootView: View? = null
    private var mImmersionBar: ImmersionBar? = null

    abstract val noScale: Boolean//是否缩放
    abstract val isImmersion: Boolean//是否浸入式状态栏
    abstract val isLandscape: Boolean//是否竖屏
    abstract fun initView(savedInstanceState: Bundle?): Int //初始化View
    abstract fun initData(savedInstanceState: Bundle?) //初始化数据
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isLandscape) {//竖屏
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {//横屏
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        mImmersionBar = ImmersionBar.with(this)
        if (isImmersion) {
            mImmersionBar!!.init()   //所有子类都将继承这些相同的属性
        }
        try {
            val layoutResID = initView(savedInstanceState)
            if (layoutResID != 0) {//如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
                setContentView(layoutResID)
                rootView = findViewById(android.R.id.content)
                if (null != rootView && !noScale) {
                    if (isLandscape) {//竖屏
                        SupportMultipleScreensUtil.init(Utils.getContext(), false)
                    } else {//横屏
                        SupportMultipleScreensUtil.init(Utils.getContext(), true)
                    }
                    SupportMultipleScreensUtil.scale(rootView)
                }
                val back = rootView!!.findViewById<View>(R.id.iv_back)
                if (back == null) {
                    initData(savedInstanceState)
                    return
                }
                val titleColor = (application as BaseApplication).titleColor
                if (titleColor != 0) {
                    val titleBackground = rootView!!.findViewById<View>(R.id.rl_title)
                    titleBackground.setBackgroundColor(titleColor)
                } else {
                    BToast.info("需要在Application中为titleColor字段赋值！")
                }
                back?.setOnClickListener { finish() }
            }
        } catch (e: Exception) {
            LogUtils.e("----------" + e.toString())
        }

        initData(savedInstanceState)
    }

    fun gotoActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    fun gotoActivityAndFinish(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
        finish()
    }

    /**
     * 改变状态栏颜色
     * @param colorId
     */
    fun changeStatusBarColor(colorId: Int) {
        mImmersionBar!!.statusBarColor(colorId).fitsSystemWindows(true).init()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mImmersionBar != null) {
            mImmersionBar!!.destroy()  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        }
    }

}
