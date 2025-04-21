package com.example.sorucozumpaylasimuygulamasi.roomSoru

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface questionDao {
    @Insert
    fun insert(question: Questions)

    @Query("SELECT * FROM questions")
    fun getAllQuestions(): LiveData<List<Questions>>
}