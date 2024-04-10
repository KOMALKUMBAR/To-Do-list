package com.android1.my_to_do.model

data class Todo(
    val completed: Boolean,
    val id: Int,
    val todo: String,
    val userId: Int
)