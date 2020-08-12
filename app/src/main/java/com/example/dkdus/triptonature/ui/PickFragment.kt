package com.example.dkdus.triptonature.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.database.AppDatabase
import com.example.dkdus.triptonature.model.place_material.Item
import kotlinx.android.synthetic.main.fragment_pick.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PickFragment : Fragment() {

    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    lateinit var madapter: MyRecyclerAdapter
    lateinit var thiscontext : Context
    lateinit var db : AppDatabase
    var datalist: MutableList<Item> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pick, container, false)
        thiscontext = container?.context!!
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        db = Room.databaseBuilder(thiscontext.applicationContext, AppDatabase::class.java,"place-db")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            var items = db.itemDao().getAll()
            for((index, value) in items.withIndex()){
                datalist.add(value)
            }
        }

        PickRecyclerview.layoutManager = linearLayoutManager
        madapter = MyRecyclerAdapter(this, datalist){item ->
        }
        PickRecyclerview.adapter = madapter

    }
}