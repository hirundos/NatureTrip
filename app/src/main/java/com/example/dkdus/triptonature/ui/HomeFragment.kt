package com.example.dkdus.triptonature.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dkdus.triptonature.R
import com.example.dkdus.triptonature.adapter.MyRecyclerAdapter
import com.example.dkdus.triptonature.model.Place
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.util.NaturePlaceUtil
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    var datalist: MutableList<Item> = arrayListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        Log.d("프레그먼크 체크","onCreatedView")
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("프레그먼크 체크","oCreated")
        callPlaceData()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("프레그먼크 체크","onActivityCreated")
//        callPlaceData()

        homeRecyclerview.layoutManager = LinearLayoutManager(context)

        //Click Event
        val madapter = MyRecyclerAdapter(this, datalist){ item ->
            val intent : Intent = Intent(context, PlaceDetailActivity::class.java)
            intent.putExtra("placeData",item)
            startActivity(intent)
        }
        homeRecyclerview.adapter = madapter
        
    }


    fun callPlaceData(){
        val placeUtil = NaturePlaceUtil()
        placeUtil.getApi().getTest("AppTesting", "AND", "json")
            .enqueue(object : Callback<Place?> {
                override fun onResponse(call: Call<Place?>, response: Response<Place?>) {
                    var data = response.body()?.response?.body?.items?.item

                    for((index, value)in data?.withIndex()!!){
                        datalist.add(value)
                    }
                }

                override fun onFailure(call: Call<Place?>, t: Throwable) {
                    Log.d("failure","onfailure "+t.message)
                }
            })

    }


}