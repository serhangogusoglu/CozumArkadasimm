package com.example.sorucozumpaylasimuygulamasi.roomSoru

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val detail: String,
    val category: String
)