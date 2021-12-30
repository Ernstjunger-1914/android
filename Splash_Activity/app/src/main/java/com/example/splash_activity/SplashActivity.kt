package com.example.splash_activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

/**
 * Splash 화면 : 앱 데이터를 로딩하는 시간 동안 보여주는 화면
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, DURATION)
    }

    companion object {
        private const val DURATION: Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}