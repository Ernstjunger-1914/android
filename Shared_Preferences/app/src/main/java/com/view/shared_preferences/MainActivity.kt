package com.view.shared_preferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // edit text에 저장되어있던 값을 setText
        loadData()
    }

    private fun loadData() {
        val pref = getSharedPreferences("pref", 0)

        et_hello.setText(pref.getString("name", ""))
    }

    private fun saveData() {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()

        // 1번째 인자에는 key, 2번째 인자에는 value을 담아 저장
        edit.putString("name", et_hello.text.toString())
        edit.apply()
    }

    // 앱의 종료 시점이 다가올 때 호출되는 메소드
    override fun onDestroy() {
        super.onDestroy()

        // edit text 값을 저장
        saveData()
    }
}