package com.example.dkdus.triptonature.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.database.AppDatabase
import com.example.dkdus.triptonature.model.place_material.Item
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceDetailActivity : AppCompatActivity() {
    lateinit var placeItem : Item
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        db = Room.databaseBuilder(application, AppDatabase::class.java,"place-db")
            .build()

        if(intent.hasExtra("placeData")){
            placeItem = intent.getParcelableExtra<Item>("placeData")
        }

        detail_title.text = placeItem.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_summary.text = Html.fromHtml(placeItem.summary,Html.FROM_HTML_MODE_LEGACY)
        }else{
            detail_summary.text = Html.fromHtml(placeItem.summary)
        }

        Glide.with(this).load(placeItem.mainimage).into(detail_image)
        detail_loc.text = placeItem.addr
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.place_detail_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_pick
            -> insertData(placeItem)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertData(item : Item){

        GlobalScope.launch(Dispatchers.IO) {
            if(!db.itemDao().exist(item.title.toString()))
            { db.itemDao().insert(item)
                Log.d("삽입","완료")
            }
            else
                Log.d("삽입","중복")
        }


    }
}