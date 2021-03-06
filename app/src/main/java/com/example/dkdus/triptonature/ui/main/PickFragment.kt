package com.example.dkdus.triptonature.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.ui.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_pick.*

class PickFragment : Fragment() {

    var datalist: MutableList<Item> = arrayListOf()
    lateinit var viewModel : PickViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_pick, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(PickViewModel::class.java)

        PickRecyclerview.layoutManager = LinearLayoutManager(context)
        val madapter = MyRecyclerAdapter(this, datalist){item ->
            val intent : Intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("placeData",item)
            startActivity(intent)
        }
        PickRecyclerview.adapter = madapter

        viewModel.getAll().observe(viewLifecycleOwner, Observer {
            (PickRecyclerview.adapter as MyRecyclerAdapter).addPost(it)
        })

    }
}