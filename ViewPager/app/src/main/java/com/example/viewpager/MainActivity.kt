package com.example.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // adapter 생성 및 방향을 가로로 설정, 애니메이션 적용
        ViewPager_logo.adapter = ViewPagerAdapter(getIdolList())
        ViewPager_logo.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        ViewPager_logo.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun getIdolList() : ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.apache_logo, R.drawable.nginx_logo, R.drawable.firefox_logo, R.drawable.centos_logo)
    }

    inner class ZoomOutPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageWidth = width
                val pageHeight = height

                when {
                    position <= 1 -> {
                        val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2

                        translationX = if(position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        alpha = (MIN_ALPHA + (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    }
                    else -> {
                        alpha = 0f
                    }
                }
            }
        }
    }
}