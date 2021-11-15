package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.layout_variable.*
import java.text.SimpleDateFormat
import java.util.*

class VariableActivity : AppCompatActivity() {
    /*
    var( variable ) : 변경 가능한 참조
    val( value ) : 변경 불가능 참조
    *
     */
    var clickCount = 0

    // Activity가 시작된 시간
    val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_variable)

        // Activity 시작시간을 text 형태로 변환
        val timeText = SimpleDateFormat("HH:mm:ss", Locale.KOREA).format(startTime)

        // Activity 시작시간을 text view에 보여줌
        starttimelabel.text = "Activity 시작시간 : ${timeText}"

        clickcnt.text = "버튼이 클릭된 횟수 : ${clickCount}"

        clickme.setOnClickListener {
            clickCount += 1
            clickcnt.text = "버튼이 클릭된 횟수 : ${clickCount}"
        }
    }
}