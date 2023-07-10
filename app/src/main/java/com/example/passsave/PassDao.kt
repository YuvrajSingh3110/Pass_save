package com.example.passsave

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PassDao {
    @Upsert  //mixture of insert and update
    suspend fun upsertPass(pass: Pass)

    @Delete
    suspend fun deletePass(pass: Pass)

    @Query("SELECT * FROM pass ORDER BY title ASC")
    fun orderByTitle(): Flow<List<Pass>>  //flow will notify if any changes occur

    @Query("SELECT * FROM pass ORDER BY pass ASC")
    fun orderByPass(): Flow<List<Pass>>
}