package com.example.dkdus.triptonature.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.database.AppDatabase
import com.example.dkdus.triptonature.database.ItemDao
import com.example.dkdus.triptonature.model.place_material.Item
import kotlinx.android.synthetic.main.activity_place_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceDetailActivity : AppCompatActivity() {
    lateinit var placeItem : Item
    lateinit var db : AppDatabase
    var checkStar = false
    lateinit var itemDao : ItemDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_detail)

        db = Room.databaseBuilder(application, AppDatabase::class.java,"place-db")
            .build()

        itemDao = db.itemDao()

        if(intent.hasExtra("placeData")){
            placeItem = intent.getParcelableExtra<Item>("placeData")
        }

        checkDuplicate(placeItem)

        detail_title.text = placeItem.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_summary.text = Html.fromHtml(placeItem.summary,Html.FROM_HTML_MODE_LEGACY)
        }else{
            detail_summary.text = Html.fromHtml(placeItem.summary)
        }

        Glide.with(this).load(placeItem.mainimage).into(detail_image)
        detail_loc.text = placeItem.addr

        btn_call.setOnClickListener {
            dialPhoneNumber(placeItem.tel.toString())
        }
        btn_star.setOnClickListener{
            insertData(placeItem)
        }

    }

    private fun checkDuplicate(item : Item){
        GlobalScope.launch(Dispatchers.IO) {
            if(itemDao.exist(item.title.toString())){
                checkStar = true
                placeItem.id = itemDao.getId()
                btn_star.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_star_24,0,0)
            }
        }

    }

    private fun insertData(item : Item){
        if (!checkStar) {
            GlobalScope.launch(Dispatchers.IO) {
                itemDao.insert(item)
                checkStar = true
            }
            btn_star.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_star_24,0,0)
        }else{
            GlobalScope.launch(Dispatchers.IO) {
                itemDao.delete(item)
            }
            btn_star.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_baseline_star_border_24,0,0)
            finish()
        }
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}