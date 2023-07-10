package com.example.passsave

data class PassState(
    val pass: List<Pass> = emptyList(),
    val title: String = "",
    val password: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.Title
)
