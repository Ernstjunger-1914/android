package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.view_binding.*

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // UI로 사용할 레이아웃 XML 파일 지정
        setContentView(R.layout.view_binding)

        bmichk.setOnClickListener {
            // editTextNumberSigned1의 값( 키 )을 가져온다.
            val editTextNumberSigned1 = editTextNumberSigned.text.toString().toDouble()

            // editTextNumberSigned2의 값( 체중 )를 가져온다.
            val editTextNumberSigned2 = editTextNumberSigned2.text.toString().toDouble()
            val bmi = editTextNumberSigned2/Math.pow(editTextNumberSigned1/100, 2.0)

            resultlabel.text = "키 : ${editTextNumberSigned1}, 체중 : ${editTextNumberSigned2}, BMI : $bmi"
        }
    }
}