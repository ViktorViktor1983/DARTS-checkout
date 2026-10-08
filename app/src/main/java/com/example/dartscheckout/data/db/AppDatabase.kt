package com.example.dartscheckout.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [CheckoutEntity::class, CheckoutProgressEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun checkoutDao(): CheckoutDao
    abstract fun checkoutProgressDao(): CheckoutProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /** Сохраняем данные игрока при обновлении 1.0(6) -> 1.0(7). */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `checkout_progress` (" +
                        "`number` INTEGER NOT NULL, " +
                        "`trainingCount` INTEGER NOT NULL DEFAULT 0, " +
                        "`competitionCount` INTEGER NOT NULL DEFAULT 0, " +
                        "`trainingLastPath` TEXT, " +
                        "`competitionLastPath` TEXT, " +
                        "`trainingLastDate` INTEGER, " +
                        "`competitionLastDate` INTEGER, " +
                        "PRIMARY KEY(`number`))"
                )
            }
        }

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "darts-checkout.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
