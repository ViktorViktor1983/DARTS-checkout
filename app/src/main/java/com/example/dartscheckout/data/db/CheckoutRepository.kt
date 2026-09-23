package com.example.dartscheckout.data.db

import com.example.dartscheckout.data.CheckoutVariant
import com.example.dartscheckout.data.DEFAULT_CHECKOUTS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckoutRepository(private val dao: CheckoutDao) {

    fun observeForNumber(num: Int): Flow<List<CheckoutVariant>> =
        dao.observeForNumber(num).map { list -> list.map { it.toDomain() } }

    suspend fun getAllOnce(): Map<Int, List<CheckoutVariant>> =
        dao.getAll()
            .groupBy { it.targetNumber }
            .mapValues { (_, list) ->
                list.sortedBy { it.orderIndex }.map { it.toDomain() }
            }

    suspend fun seedIfEmpty() {
        if (dao.count() == 0) {
            val all = mutableListOf<CheckoutEntity>()
            DEFAULT_CHECKOUTS.forEach { (num, variants) ->
                variants.forEachIndexed { idx, v ->
                    all.add(
                        CheckoutEntity(
                            targetNumber = num,
                            throwsSerialized = v.throws.joinToString(","),
                            label = v.label,
                            orderIndex = idx,
                            isFavorite = idx == 0,
                            isCustom = false
                        )
                    )
                }
            }
            dao.insertAll(all)
        }
    }

    suspend fun saveAllForNumber(num: Int, variants: List<CheckoutVariant>) {
        dao.deleteAllForNumber(num)
        val entities = variants.mapIndexed { idx, v ->
            CheckoutEntity(
                targetNumber = num,
                throwsSerialized = v.throws.joinToString(","),
                label = v.label,
                orderIndex = idx,
                isFavorite = idx == 0,
                isCustom = false
            )
        }
        dao.insertAll(entities)
    }

    /**
     * Полностью заменяет базу новыми данными (импорт из JSON).
     */
    suspend fun replaceAll(checkouts: Map<Int, List<CheckoutVariant>>) {
        dao.clear()
        val all = mutableListOf<CheckoutEntity>()
        checkouts.forEach { (num, variants) ->
            variants.forEachIndexed { idx, v ->
                all.add(
                    CheckoutEntity(
                        targetNumber = num,
                        throwsSerialized = v.throws.joinToString(","),
                        label = v.label,
                        orderIndex = idx,
                        isFavorite = idx == 0,
                        isCustom = false
                    )
                )
            }
        }
        dao.insertAll(all)
    }

    suspend fun resetToDefaults() {
        dao.clear()
        seedIfEmpty()
    }

    private fun CheckoutEntity.toDomain(): CheckoutVariant {
        val throws = if (throwsSerialized.isBlank()) emptyList()
        else throwsSerialized.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        return CheckoutVariant(label = label, throws = throws)
    }
}
