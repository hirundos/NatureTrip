package com.example.dkdus.triptonature.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.dkdus.triptonature.database.AppDatabase
import com.example.dkdus.triptonature.model.place_material.Item

class PickViewModel(application: Application) : AndroidViewModel(application) {
  private val db = Room.databaseBuilder(
      application, AppDatabase::class.java,"place-db")
      .build()

    fun getAll() : LiveData<MutableList<Item>> {
        return db.itemDao().getAll()
    }

}