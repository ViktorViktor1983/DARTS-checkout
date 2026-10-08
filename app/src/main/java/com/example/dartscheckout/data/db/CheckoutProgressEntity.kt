package com.example.dartscheckout.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checkout_progress")
data class CheckoutProgressEntity(
    @PrimaryKey val number: Int,
    val trainingCount: Int = 0,
    val competitionCount: Int = 0,
    val trainingLastPath: String? = null,
    val competitionLastPath: String? = null,
    val trainingLastDate: Long? = null,
    val competitionLastDate: Long? = null
)
