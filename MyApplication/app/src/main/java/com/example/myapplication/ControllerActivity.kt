package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.layout_control.*

class ControllerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_control)

        exebtn.setOnClickListener {
            // numberfield 값을 읽어 int 형으로 변환
            val number = numberfield.text.toString().toInt()

            if(number%2==0) {
                Toast.makeText(applicationContext, "${number} 는 2의 배수이다.", Toast.LENGTH_SHORT).show()
            } else if(number%3==0) {
                Toast.makeText(applicationContext, "${number}는 3의 배수이다.", Toast.LENGTH_SHORT).show()
            } else if(number%5==0) {
                Toast.makeText(applicationContext, "${number}는 5의 배수이다.", Toast.LENGTH_SHORT).show()
            } else if(number%7==0) {
                Toast.makeText(applicationContext, "${number}는 5의 배수이다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "${number}", Toast.LENGTH_SHORT).show()
            }

            // Kotlin에서는 when이 Switch/Case 문의 역할을 수행하며 break 문을 넣지 않아도 특정 케이스 하나만 실행됨
            when(number) {
                in 1..4 -> {
                    exebtn.text = "실행 - 4"
                }
                9, 99, 999, 9999, 99999, 999999 -> {
                    exebtn.text = "실행 - 비둘기"
                } else -> {
                    exebtn.text = "실행"
                }
            }
        }
    }
}