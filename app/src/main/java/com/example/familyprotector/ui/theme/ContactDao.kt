package com.example.familyprotector.ui.theme

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contactModel: ContactModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(contactModel: List<ContactModel>)

    @Query("SELECT * FROM ContactModel")
    fun getAllContacts(): LiveData<List<ContactModel>>

}


