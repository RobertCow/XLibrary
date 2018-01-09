package soft.robert.com.xlibrary.base

import android.app.Application

import com.facebook.drawee.backends.pipeline.Fresco

import soft.robert.com.xlibrary.utils.Utils

/**
 * Created on 2017/12/25.
 *
 * @author cpf
 * @2227039052@qq.com
 */

open class BaseApplication : Application() {
    var titleColor = 0
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        Fresco.initialize(this)
    }
}
