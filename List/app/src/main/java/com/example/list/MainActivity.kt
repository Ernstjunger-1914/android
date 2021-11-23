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
        User(R.drawable.android, "손소독", "19", "안녕하세요."),
        User(R.drawable.android, "소독", "17", "손소독"),
        User(R.drawable.android, "독소손", "21", "손소소독"),
        User(R.drawable.android, "ssd", "18", "독소손"),
        User(R.drawable.android, "ㄴㄴㅇ", "20", "ssd"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val item = arrayOf("소독", "손소독", "청소", "방역", "청정")
        //listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)

        val Adapter = UserAdapter(this, UserList)
        listView.adapter = Adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectItem = parent.getItemAtPosition(position) as User
            Toast.makeText(this, selectItem.name, Toast.LENGTH_SHORT).show()
        }
    }
}