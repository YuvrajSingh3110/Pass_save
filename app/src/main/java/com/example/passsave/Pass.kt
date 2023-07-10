package com.example.passsave

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pass(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val pass: String
)
