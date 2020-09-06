package com.example.dkdus.triptonature.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.dkdus.triptonature.model.place_material.Item

@Dao
interface ItemDao {
    @Query("SELECT * FROM Item")
    fun getAll() : LiveData<MutableList<Item>>

    @Query("SELECT EXISTS(SELECT * FROM Item WHERE title = :string)")
    fun exist(string: String) : Boolean

    @Query("SELECT id FROM Item")
    fun getId() :Int

    @Insert
    fun insert(item : Item)

    @Delete
    fun delete(item : Item)
}