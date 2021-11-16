package com.example.securitykeyboard

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.View
import android.view.animation.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val mActivity: Activity? = this
    private var currentText: StringBuilder = StringBuilder()

    private lateinit var editText: EditText
    private lateinit var flipper: ViewFlipper
    private lateinit var tablelayout: TableLayout
    private lateinit var textView: TextView
    private lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val btnWidth: Int = getBtnWidth()

        flipper = password_viewflipper.apply {
            visibility = View.VISIBLE

            var inAni = AnimationUtils.loadAnimation(mActivity, R.anim.in_animation)
            inAnimation.interpolator = AccelerateInterpolator()
            setInAnimation(inAni)

            var outAni = AnimationUtils.loadAnimation(mActivity, R.anim.out_animation)
            outAnimation.interpolator = DecelerateInterpolator()
            setOutAnimation(outAni)
        }

        tablelayout = findViewById<TableLayout>(R.id.tableLayout)
        textView = findViewById<TextView>(R.id.password_text_view)
        val emptyView = TextView(this)
        flipper.addView(emptyView, 0)

        editText = findViewById<EditText>(R.id.password_text_view).apply {
            setOnClickListener { v: View ->
                imm.hideSoftInputFromWindow(editText.windowToken, 0)

                if(flipper.currentView.id != R.id.firstViewFlipper) {
                    reOrderKeyboard(btnWidth)
                    flipper.visibility = View.VISIBLE
                    flipper.displayedChild = 1
                }
            }
        }

        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        editText.requestFocus()

        findViewById<Button>(R.id.submitbtn).apply {
            setOnClickListener { v: View ->
                if(flipper.currentView.id == R.id.firstViewFlipper) {
                    flipper.displayedChild = 0
                    var showText = StringBuilder(textView.text)
                    showText.append("\n" + currentText)
                    textView.text = showText.toString()
                    currentText.clear()
                    editText.text.clear()
                }
            }
        }

        findViewById<Button>(R.id.deletebtn).apply {
            setOnClickListener { v: View ->
                val curIndex = editText.selectionStart
                var passwordStr = editText.text.toString()
                var passwordLendth = passwordStr.length

                if(curIndex == 0 || passwordLendth == 0) {
                    return@setOnClickListener
                }

                passwordStr.apply {
                    passwordStr = substring(0, curIndex - 1) + substring(curIndex, passwordLendth)
                }

                currentText.apply {
                    currentText = StringBuilder(toString().substring(0, curIndex - 1) + toString().substring(curIndex, passwordLendth))
                }

                passwordLendth = passwordStr.length
                editText.setText("")

                for(i in 1..passwordLendth) {
                    editText.append("*")
                }
                editText.setSelection(curIndex - 1)
            }
        }
        reOrderKeyboard(btnWidth)
    }

    private fun reOrderKeyboard(btnWidth: Int) {
        val keyNumberArr = ArrayList<String>()

        for(i in 0..9) {
            keyNumberArr.add(i.toString())
        }

        var tr: TableRow? = null
        var btn: Button? = null
        var randIndex: Int = 0;
        var random = Random

        for(i in 0..(tablelayout.childCount - 1)) {
            tr = tablelayout.getChildAt(i) as TableRow

            for(i in 0..(tr.childCount - 1)) {
                btn = tr.getChildAt(i) as Button

                if(btn.id == -1) {
                    randIndex = random.nextInt(keyNumberArr.size)
                    btn.text = keyNumberArr[randIndex]
                    btn.width = btnWidth / 3

                    val keyText = btn.text

                    keyNumberArr.removeIf { x -> x == keyNumberArr[randIndex] }
                    btn.setOnClickListener { v: View ->
                        val curIndex = editText.selectionStart
                        var passwordStr = editText.text.toString()
                        val passwordLength = passwordStr.length

                        passwordStr.apply {
                            substring(0, curIndex) + keyText + substring(curIndex, passwordLength)
                        }

                        currentText.append(keyText)
                        editText.setText("")

                        for(i in 0 until curIndex) {
                            editText.append("*")
                        }

                        editText.append(keyText)

                        for(i in curIndex + 1 until passwordLength + 1) {
                            editText.append("*")
                        }

                        editText.setSelection(curIndex + 1)
                        mHandler.sendEmptyMessageDelayed(0, 1000)
                    }
                }
            }
        }
    }

    private var mHandler = Handler() {
            msg: Message? ->
        val curIndex = editText.selectionStart
        editText.setText("")

        for(i in 0 until curIndex) {
            editText.append("*")
        }

        editText.setSelection(curIndex)
        false

    }

    private fun getBtnWidth(): Int {
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)

        return displaymetrics.widthPixels
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(flipper.currentView.id == R.id.firstViewFlipper) {
                flipper.setDisplayedChild(0)
            } else {
                return super.onKeyDown(keyCode, event)
            }
        }
        return true
    }
}