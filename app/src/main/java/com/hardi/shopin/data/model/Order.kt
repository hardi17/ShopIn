package com.hardi.shopin.data.model

import com.google.firebase.Timestamp


data class Order(
    val id: String = "",
    val date: Timestamp = Timestamp.now(),
    val userId: String,
    val items: Map<String, Long> = mapOf(),
    val status: String = "",
    val address: String = "",
    val paymentMethod: String = ""
)
