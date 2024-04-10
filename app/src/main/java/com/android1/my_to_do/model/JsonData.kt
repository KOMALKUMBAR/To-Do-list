package com.android1.my_to_do.model

data class JsonData(
    val limit: Int,
    val skip: Int,
    val todos: List<Todo>,
    val total: Int
)