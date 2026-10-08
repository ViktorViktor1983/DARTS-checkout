package com.example.dartscheckout.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckoutProgressDao {

    @Query("SELECT * FROM checkout_progress ORDER BY number ASC")
    fun getAll(): Flow<List<CheckoutProgressEntity>>

    @Query("SELECT * FROM checkout_progress WHERE number = :number LIMIT 1")
    suspend fun getByNumber(number: Int): CheckoutProgressEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNotExists(entity: CheckoutProgressEntity)

    // ----- ТРЕНИРОВКА -----

    @Query(
        "UPDATE checkout_progress " +
            "SET trainingCount = trainingCount + 1, trainingLastDate = :date " +
            "WHERE number = :number"
    )
    suspend fun incrementTraining(number: Int, date: Long)

    @Query(
        "UPDATE checkout_progress " +
            "SET trainingCount = CASE WHEN trainingCount > 0 THEN trainingCount - 1 ELSE 0 END " +
            "WHERE number = :number"
    )
    suspend fun decrementTraining(number: Int)

    @Query("UPDATE checkout_progress SET trainingLastPath = :path WHERE number = :number")
    suspend fun setTrainingPath(number: Int, path: String?)

    // ----- СОРЕВНОВАНИЯ -----

    @Query(
        "UPDATE checkout_progress " +
            "SET competitionCount = competitionCount + 1, competitionLastDate = :date " +
            "WHERE number = :number"
    )
    suspend fun incrementCompetition(number: Int, date: Long)

    @Query(
        "UPDATE checkout_progress " +
            "SET competitionCount = CASE WHEN competitionCount > 0 THEN competitionCount - 1 ELSE 0 END " +
            "WHERE number = :number"
    )
    suspend fun decrementCompetition(number: Int)

    @Query("UPDATE checkout_progress SET competitionLastPath = :path WHERE number = :number")
    suspend fun setCompetitionPath(number: Int, path: String?)

    @Query(
        "UPDATE checkout_progress SET " +
            "trainingCount = 0, competitionCount = 0, " +
            "trainingLastPath = NULL, competitionLastPath = NULL, " +
            "trainingLastDate = NULL, competitionLastDate = NULL"
    )
    suspend fun resetAll()
}
