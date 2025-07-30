package com.hardi.shopin.data.model

data class User(
    val name: String ="",
    val email: String ="",
    val userId: String ="",
    val cartItems: Map<String, Long> = emptyMap()
)
