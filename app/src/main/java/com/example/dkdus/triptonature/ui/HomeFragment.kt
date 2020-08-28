package com.example.dkdus.triptonature.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.model.Place
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.util.NaturePlaceUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_pick.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    var datalist: MutableList<Item> = mutableListOf()
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        homeRecyclerview.layoutManager = LinearLayoutManager(context)

        val madapter = MyRecyclerAdapter(this, datalist){ item ->
            val intent : Intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("placeData",item)
            startActivity(intent)
        }
        homeRecyclerview.adapter = madapter
        viewModel.call().observe(viewLifecycleOwner, Observer {
            for((index, value) in it.withIndex()){
                datalist.add(value)
            }
            homeRecyclerview.adapter?.notifyDataSetChanged()
        })

    }

}