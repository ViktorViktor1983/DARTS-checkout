package com.example.dartscheckout.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checkouts",
    indices = [Index("targetNumber")]
)
data class CheckoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetNumber: Int,
    val throwsSerialized: String,
    val label: String,
    val orderIndex: Int,
    val isFavorite: Boolean,
    val isCustom: Boolean
)
