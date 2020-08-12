package com.example.dkdus.triptonature.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dkdus.triptonature.model.place_material.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase :  RoomDatabase(){
    abstract fun itemDao() : ItemDao

}