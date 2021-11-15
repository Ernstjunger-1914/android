package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

// Kotlin은 상속을 위해 " : "를 사용
class MainActivity : AppCompatActivity() {
    // 변수 이름 뒤에 타입을 붙이고 NULL 가능 여부는 " ? "로 표현, 변수 이름을 먼저 쓰고 뒤에 타입을 쓰며 함수의 반환값이 없으면 void를 쓰지 않고 생략
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // layout button1이라는 ID로 선언된 뷰에 클릭 이벤트 리스너 등록
        button1.setOnClickListener{
            // Intent로 BMIActivity를 타겟으로 지정 후, startActivity로 실행
            startActivity(Intent(this@MainActivity, BMIActivity::class.java))
        }

        varbtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, VariableActivity::class.java))
        }
    }
}