package com.hardi.shopin.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hardi.shopin.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    fun signUp(email: String, name: String, password: String, onResult: (Boolean, String) -> Unit) {
        if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid
                        val userModel = User(name, email, userId!!)
                        storeUserData(userId, userModel, onResult)
                    } else {
                        onResult(false, it.exception?.localizedMessage.toString())
                    }
                }
        } else {
            onResult(false, "All fields are required")
        }
    }

    fun storeUserData(userId: String, userModel: User, onResult: (Boolean, String) -> Unit) {
        firestore.collection("user").document(userId)
            .set(userModel)
            .addOnCompleteListener { doTask ->
                if (doTask.isSuccessful) {
                    onResult(true, "Sign up successfully done")
                } else {
                    onResult(false, "Something went wrong")
                }
            }
    }

    fun logIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        onResult(true, "Login successfully done")
                    } else {
                        onResult(false, it.exception?.localizedMessage.toString())
                    }
                }
        } else {
            onResult(false, "All fields are required")
        }
    }
}