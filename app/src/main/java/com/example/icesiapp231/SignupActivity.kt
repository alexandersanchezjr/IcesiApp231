package com.example.icesiapp231

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.icesiapp231.databinding.ActivitySignupBinding
import com.example.icesiapp231.model.User
import com.example.icesiapp231.viewmodels.AuthViewModel

class SignupActivity : AppCompatActivity() {

    val binding by lazy{
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val viewmodel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewmodel.status.observe(this) {
            if (it == 1) {
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
                finish()
            } else if (it == -1) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        binding.signupBtn.setOnClickListener {
            viewmodel.signup(User(
                null,
                binding.nameInput.editText?.text.toString(),
                binding.usernameInput.editText?.text.toString(),
                binding.emailInput.editText?.text.toString()
            ), binding.passwordInput.editText?.text.toString())
        }
    }
}