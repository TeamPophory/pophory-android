package com.teampophory.pophory.feature.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityStartPophoryBinding
import com.teampophory.pophory.feature.home.HomeActivity

class StartPophoryActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityStartPophoryBinding::inflate)

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            val intent = Intent(this@StartPophoryActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnStartPophory.setOnClickListener {
            startActivity(HomeActivity.getIntent(this))
        }

        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, StartPophoryActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }
}
