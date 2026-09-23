package com.example.dartscheckout.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckoutDao {

    @Query("SELECT * FROM checkouts WHERE targetNumber = :num ORDER BY isFavorite DESC, orderIndex ASC")
    fun observeForNumber(num: Int): Flow<List<CheckoutEntity>>

    @Query("SELECT * FROM checkouts ORDER BY targetNumber, orderIndex")
    suspend fun getAll(): List<CheckoutEntity>

    @Query("SELECT COUNT(*) FROM checkouts")
    suspend fun count(): Int

    @Query("DELETE FROM checkouts WHERE targetNumber = :num")
    suspend fun deleteAllForNumber(num: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CheckoutEntity): Long

    @Insert
    suspend fun insertAll(entities: List<CheckoutEntity>)

    @Delete
    suspend fun delete(entity: CheckoutEntity)

    @Query("DELETE FROM checkouts")
    suspend fun clear()
}
