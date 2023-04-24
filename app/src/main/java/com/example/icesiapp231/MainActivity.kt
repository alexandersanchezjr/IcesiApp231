package com.example.icesiapp231

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.lifecycleScope
import com.example.icesiapp231.databinding.ActivityLoginBinding
import com.example.icesiapp231.databinding.ActivityMainBinding
import com.example.icesiapp231.fragments.ProfileFragment
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    val profileFragment = ProfileFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                val res = Firebase.firestore.collection("users")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().await()

                val me = res.toObject(User::class.java)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Welcome ${me?.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragmentContainer,profileFragment)
            .commit()

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        } else {
            lifecycleScope.launch(Dispatchers.Main) {
                val res = Firebase.firestore.collection("users")
                    .document(Firebase.auth.currentUser!!.uid)
                    .get().await()

                val me = res.toObject(User::class.java)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Welcome ${me?.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}