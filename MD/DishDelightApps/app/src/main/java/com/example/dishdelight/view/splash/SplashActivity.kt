package com.example.dishdelight.view.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.R
import com.example.dishdelight.databinding.ActivitySplashBinding
import com.example.dishdelight.view.welcoming.WelcomingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, WelcomingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        playAnimator()
    }

    private fun playAnimator() {
        val logo = ObjectAnimator.ofFloat(binding.iclogo, View.ALPHA, 1f).setDuration(500)
        val title1 = ObjectAnimator.ofFloat(binding.title1,View.ALPHA,1f).setDuration(500)
        val title2 = ObjectAnimator.ofFloat(binding.title2,View.ALPHA,1f).setDuration(500)

        val togather = AnimatorSet().apply {
            playTogether(title2,title1)
        }

        AnimatorSet().apply {
            playSequentially(logo,togather)
            start()
        }
    }
}