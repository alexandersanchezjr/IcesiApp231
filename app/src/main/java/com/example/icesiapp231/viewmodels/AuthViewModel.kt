package com.example.icesiapp231.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp231.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    val status :MutableLiveData<Int> = MutableLiveData(0)

    fun getAuthStatus () {
        if (Firebase.auth.currentUser != null){
            status.value = AuthState.AUTH
        }else {
            status.value = AuthState.NO_AUTH
        }
    }

    fun signup(user: User, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = Firebase.auth.createUserWithEmailAndPassword(user.email, password).await()
                user.id = response.user!!.uid
                Firebase.firestore
                    .collection("users")
                    .document(response.user!!.uid)
                    .set(user).await()
                withContext(Dispatchers.Main) {
                    status.value = 1
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    status.value = -1
                }
            }

        }

    }
}

object AuthState {
    const val LOADING = 0
    const val AUTH = 1
    const val NO_AUTH = 2
}