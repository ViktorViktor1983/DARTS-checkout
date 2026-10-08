package com.example.dartscheckout.data.db

import kotlinx.coroutines.flow.Flow

class CheckoutProgressRepository(
    private val dao: CheckoutProgressDao
) {

    val allProgress: Flow<List<CheckoutProgressEntity>> = dao.getAll()

    private suspend fun ensureRow(number: Int) {
        dao.insertIfNotExists(CheckoutProgressEntity(number = number))
    }

    // ----- ТРЕНИРОВКА -----

    suspend fun incrementTraining(number: Int) {
        ensureRow(number)
        dao.incrementTraining(number, System.currentTimeMillis())
    }

    suspend fun decrementTraining(number: Int) {
        ensureRow(number)
        dao.decrementTraining(number)
    }

    suspend fun setTrainingPath(number: Int, path: String?) {
        ensureRow(number)
        dao.setTrainingPath(number, path)
    }

    // ----- СОРЕВНОВАНИЯ -----

    suspend fun incrementCompetition(number: Int) {
        ensureRow(number)
        dao.incrementCompetition(number, System.currentTimeMillis())
    }

    suspend fun decrementCompetition(number: Int) {
        ensureRow(number)
        dao.decrementCompetition(number)
    }

    suspend fun setCompetitionPath(number: Int, path: String?) {
        ensureRow(number)
        dao.setCompetitionPath(number, path)
    }

    // ----- СБРОС -----

    suspend fun resetAll() {
        dao.resetAll()
    }
}
