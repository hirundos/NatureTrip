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
import androidx.recyclerview.widget.RecyclerView
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
    var page : Int = 1
    val maxPage = 224
    var loading = true

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

        val madapter = MyRecyclerAdapter(this, datalist) { item ->
            val intent: Intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("placeData", item)
            startActivity(intent)
        }
        homeRecyclerview.adapter = madapter
        (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(true)
        call()
        checkScroll()
    }

    private fun call(){
        (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(false)
        viewModel.call(page).observe(viewLifecycleOwner, Observer {
            (homeRecyclerview.adapter as MyRecyclerAdapter).addPost(it)
            (homeRecyclerview.adapter as MyRecyclerAdapter).setLoadingView(true)
        })
    }

    private fun checkScroll() {
        homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(!homeRecyclerview.canScrollVertically(1) && hasPage()
                    ){
                    page++;
                    call()
                }
            }
        })
    }

    private fun hasPage(): Boolean {
        if(page <= maxPage)
            return true

        return false
    }
}
