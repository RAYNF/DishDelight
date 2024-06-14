package com.example.dishdelight.view.welcoming

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dishdelight.R
import com.example.dishdelight.databinding.ActivityWelcomingBinding
import com.example.dishdelight.view.login.LoginActivity

class WelcomingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnMulai.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        playAnimation()

    }

    private fun playAnimation() {
        val title1 = ObjectAnimator.ofFloat(binding.title1,View.ALPHA,1f).setDuration(500)
        val title2 = ObjectAnimator.ofFloat(binding.title2,View.ALPHA,1f).setDuration(500)
        val title3 = ObjectAnimator.ofFloat(binding.title3,View.ALPHA,1f).setDuration(500)
        val title4 = ObjectAnimator.ofFloat(binding.title4,View.ALPHA,1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnMulai,View.ALPHA,1f).setDuration(500)

        val togather = AnimatorSet().apply {
            playTogether(title3,title4)
        }

        AnimatorSet().apply {
            playSequentially(title1,title2,btnLogin,togather)
            start()
        }
    }
}