package com.example.sliding_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sliding_layout.databinding.ActivityMainBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slidePanel = binding.mainFrame
        slidePanel.addPanelSlideListener(PanelEventListener())

        binding.toggleBtn.setOnClickListener {
            val state = slidePanel.panelState

            if(state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            } else if(state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidePanel.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }

        // 터치로 슬라이드 가능 여부 지정
        binding.touchBtn.setOnClickListener {
            val touchEnable = slidePanel.isTouchEnabled

            if(touchEnable) {
                binding.touchBtn.text = "잠금해제"
                slidePanel.isTouchEnabled = false
            } else {
                binding.touchBtn.text = "터치잠금"
                slidePanel.isTouchEnabled = true
            }
        }

        // 패널 활성화 여부 지정
        binding.enableBtn.setOnClickListener {
            val enabled = slidePanel.isEnabled

            if(enabled) {
                binding.enableBtn.text = "활성화"
                slidePanel.isEnabled = false
            } else {
                binding.touchBtn.text = "비활성화"
                slidePanel.isEnabled = true
            }
        }
    }

    // Event Listener
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            binding.tvSlideOffset.text = slideOffset.toString()
        }

        // 패널 상태가 변화했을 때
        override fun onPanelStateChanged(
            panel: View?,
            previousState: SlidingUpPanelLayout.PanelState?,
            newState: SlidingUpPanelLayout.PanelState?
        ) {
            if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.toggleBtn.text = "Open"
            } else if(newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                binding.toggleBtn.text = "Close"
            }
        }
    }
}