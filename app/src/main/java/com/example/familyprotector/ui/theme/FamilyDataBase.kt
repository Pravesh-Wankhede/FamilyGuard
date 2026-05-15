package com.example.familyprotector.ui.theme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContactModel::class], version = 1, exportSchema = false)
abstract class FamilyDataBase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        @Volatile
        private var INSTANCE: FamilyDataBase? = null

        fun getDatabase(context: Context): FamilyDataBase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FamilyDataBase::class.java,
                    "family_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}