package com.view.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.settings.javaScriptEnabled = true
        // web View에서 새 창이 뜨지 않도록 방지
        webView.webViewClient  = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.loadUrl("https://www.naver.com/")
    }

    // 뒤로가기 버튼을 눌렀을 때 수행할 메소드
    override fun onBackPressed() {
        // 웹 사이트에서 뒤로갈 페이지가 존재한다면
        if(webView.canGoBack()) {
            // 웹 사이트 뒤로가기
            webView.goBack()
        } else {
            // 본래의 뒤로가기기 기능 수행
            super.onBackPressed()
        }
    }
}