package soft.robert.com.xlibrary.dialog


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import soft.robert.com.utils.R
import soft.robert.com.xlibrary.utils.SupportMultipleScreensUtil

/**
 * Created on 2017/12/28.
 *
 * @author cpf
 * @2227039052@qq.com
 */

class UniversalDialog(context: Context      // 上下文
                      , private val layoutResID: Int      // 布局文件id
                      , private val gravity: Int          //Dialog位置
                      , private val listenedItems: IntArray  // 要监听的控件id
)//dialog的样式
    : Dialog(context, R.style.dialog_custom), View.OnClickListener {

    private var listener: OnDialogItemClickListener? = null

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        val window = window
        window!!.setGravity(gravity) // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.bottom_menu_animation) // 添加动画效果
        val view = LayoutInflater.from(context).inflate(layoutResID, null)
        SupportMultipleScreensUtil.scale(view)//缩放布局，适配UI
        setContentView(view)

        val windowManager = (context as Activity).windowManager
        val display = windowManager.defaultDisplay
        val lp = getWindow()!!.attributes
        lp.width = display.width * 4 / 5 // 设置dialog宽度为屏幕的4/5
        getWindow()!!.attributes = lp
        setCanceledOnTouchOutside(true)// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (id in listenedItems) {
            findViewById<View>(id).setOnClickListener(this)
        }
    }

    interface OnDialogItemClickListener {
        fun OnDialogItemClick(dialog: UniversalDialog, view: View)
    }

    fun setOnDialogItemClickListener(listener: OnDialogItemClickListener) {
        this.listener = listener
    }


    override fun onClick(view: View) {
        dismiss()
        listener!!.OnDialogItemClick(this, view)
    }
}
