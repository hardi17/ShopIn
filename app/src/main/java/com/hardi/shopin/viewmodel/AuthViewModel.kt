package com.hardi.shopin.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.hardi.shopin.data.model.User

class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth

    private val firestore = Firebase.firestore

    fun signUp(email: String, name: String, password: String, onResult: (Boolean, String) -> Unit) {
        if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = it.result?.user?.uid
                        val userModel = User(name, email, userId!!)
                        firestore.collection("user").document(userId)
                            .set(userModel)
                            .addOnCompleteListener { doTask ->
                                if (doTask.isSuccessful) {
                                    onResult(true, "Sign up successfully done")
                                } else {
                                    onResult(false, "Something went wrong")
                                }
                            }
                    } else {
                        onResult(false, it.exception?.localizedMessage.toString())
                    }
                }
        } else {
            onResult(false, "All fields are required")
        }
    }

    fun logIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        if(email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        onResult(true, "Login successfully done")
                    }else{
                        onResult(false, it.exception?.localizedMessage.toString())
                    }
                }
        }else{
            onResult(false, "All fields are required")
        }
    }
}