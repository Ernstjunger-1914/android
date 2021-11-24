package com.example.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var UserList = arrayListOf<User>(
        User(R.drawable.android, "Go", "12", "가자!"),
        User(R.drawable.android, "Kotlin", "10", "모두 코틀린쓰세요."),
        User(R.drawable.android, "Java", "26", "저 놈을 자바라"),
        User(R.drawable.android, "C", "49", "아직 건재하지"),
        User(R.drawable.android, "Python", "30", "뱀"),
        User(R.drawable.android, "F#", "16", "함수"),
        User(R.drawable.android, "PHP", "26", "이미 죽은 언어입니다.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Adapter = UserAdapter(this, UserList)
        listView.adapter = Adapter

        //val item = arrayOf("소독", "손소독", "청소", "방역", "청정")
        //listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)
    }
}