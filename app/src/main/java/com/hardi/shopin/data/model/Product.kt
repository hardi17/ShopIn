package com.hardi.shopin.data.model

data class Product(
    val id: String = " ",
    val title: String = " ",
    val description: String = " ",
    val price: String = " ",
    val actualPrice: String = " ",
    val size: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val category: String = " "
)
