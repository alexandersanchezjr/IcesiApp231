package com.example.icesiapp231

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.contextaware.withContextAvailable
import androidx.lifecycle.lifecycleScope
import com.example.icesiapp231.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    Firebase.auth.signInWithEmailAndPassword(
                        binding.emailInput.editText?.text.toString(),
                        binding.passwordInput.editText?.text.toString(),
                    ).await()
                    withContext(Dispatchers.Main) {
                        startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java)
                        )
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}