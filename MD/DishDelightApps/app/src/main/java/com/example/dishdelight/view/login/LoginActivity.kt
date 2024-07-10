package com.example.dishdelight.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.dishdelight.view.main.MainActivity
import com.example.dishdelight.R
import com.example.dishdelight.data.local.pref.UserModel
import com.example.dishdelight.data.viewmodel.LoginViewModel
import com.example.dishdelight.data.viewmodel.MainViewModel
import com.example.dishdelight.databinding.ActivityLoginBinding
import com.example.dishdelight.factory.ViewModelFactory
import com.example.dishdelight.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRegis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            mainViewModel.loginUser(email,password)
            mainViewModel.message.observe(this@LoginActivity){
                message(it)
            }
            mainViewModel.error.observe(this@LoginActivity){
                if (it== false){
                    mainViewModel.token.observe(this@LoginActivity){
                        Log.d("token",it)
                        viewModel.saveSession(UserModel(email, it,password))
                    }
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("Anda berhasil Login. Sudah tidak sabar untuk mencari resep selera mu ya?")
                        setPositiveButton("lanjut") { _, _ ->
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }else{
                    Log.d("error","login gagal")
                }
            }

            mainViewModel.loading.observe(this){
                Loading(it)
            }

        }
    }

    private fun message(it: String?) {
        Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
    }
    private fun Loading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        binding.dimmedBackground.visibility = if (it) View.VISIBLE else View.GONE
    }

}