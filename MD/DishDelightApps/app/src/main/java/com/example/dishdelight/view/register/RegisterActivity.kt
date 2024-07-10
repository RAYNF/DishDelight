package com.example.dishdelight.view.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dishdelight.R
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.databinding.ActivityRegisterBinding
import com.example.dishdelight.view.login.LoginActivity
import com.example.dishdelight.view.main.MainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.registerButton.setOnClickListener {
            val username = binding.fullnameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            mainViewModel.registerUser(username, email, password)
            mainViewModel.message.observe(this@RegisterActivity) {
                message(it)
            }
            mainViewModel.error.observe(this@RegisterActivity) {
                if (it == false) {
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan lanjut mencari resep seleramu.")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        create()
                        show()
                    }
                } else {
                    Log.d("error", "ini error")
                }
            }

            mainViewModel.loading.observe(this) {
                Loading(it)
            }

        }



        binding.btnLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun Loading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        binding.dimmedBackground.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun message(it: String) {
        Toast.makeText(this@RegisterActivity, it, Toast.LENGTH_SHORT).show()
    }

}