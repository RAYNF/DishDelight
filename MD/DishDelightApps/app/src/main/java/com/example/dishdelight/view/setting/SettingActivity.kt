package com.example.dishdelight.view.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dishdelight.R
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.data.viewmodel.MainViewModelMain
import com.example.dishdelight.databinding.ActivitySettingBinding
import com.example.dishdelight.databinding.ActivitySplashBinding
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.login.LoginActivity

//foto profil masih pakai default karena di api blm ada
class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private val viewModel by viewModels<MainViewModelMain> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) { user ->
            Log.d("saved token", "ini token yang kesimpen: ${user.token}")
            if (user.token != null) {
                binding.tvName.text = getFirstPartOfEmail(user.email)
                binding.tvEmail.text = user.email
                binding.btnEmail.setOnClickListener {
                    AlertDialog.Builder(this).apply {
                        setTitle("Email")
                        setMessage(user.email)
                        create()
                        show()
                    }
                }
                binding.btnUsername.setOnClickListener {
                    AlertDialog.Builder(this).apply {
                        setTitle("Username")
                        setMessage(getFirstPartOfEmail(user.email))
                        create()
                        show()
                    }
                }
                binding.btnPassword.setOnClickListener {
                    AlertDialog.Builder(this).apply {
                        setTitle("Password")
                        setMessage(user.password)
                        create()
                        show()
                    }
                }

            }
        }

        val requestOptions = RequestOptions().error(R.drawable.image_siomay)

        Glide.with(this)
            .load("https://w7.pngwing.com/pngs/178/595/png-transparent-user-profile-computer-icons-login-user-avatars-thumbnail.png")
            .apply(requestOptions).into(binding.imgProfile)

        binding.btnAboutApp.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("About App")
                setMessage("In our daily routine, the food we order or make often doesn't meet our expectations in terms of taste, price, or nutritional value. To address this, we created DishDelight, a food menu recipe recommendation app that recognizes user preferences")
                create()
                show()
            }
        }

    }

    private fun getFirstPartOfEmail(email: String, length: Int = 4): String {
        return if (email.length > length) {
            email.substring(0, length)
        } else {
            email
        }
    }
}