package com.view.nav

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_btn.setOnClickListener {
            // START : left, END : right
            layout_drawer.openDrawer(GravityCompat.START)
        }
        // navigation menu item에 클릭 속성 부여
        navView.setNavigationItemSelectedListener(this)
    }

    // navigation menu item 클릭 시 수행
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.access -> Toast.makeText(applicationContext, "접근성", Toast.LENGTH_SHORT).show()
            R.id.email -> Toast.makeText(applicationContext, "이메일", Toast.LENGTH_SHORT).show()
            R.id.message -> Toast.makeText(applicationContext, "메세지", Toast.LENGTH_SHORT).show()
        }
        // navigation 뷰 닫기
        layout_drawer.closeDrawers()

        return false
    }

    // 뒤로가기 버튼 누르면 수행하는 메소드
    override fun onBackPressed() {
        if(layout_drawer.isDrawerOpen(GravityCompat.START)) {
            layout_drawer.closeDrawers()
        } else {
            // 일반 뒤로가기 기능 실행
            super.onBackPressed()
        }
    }
}