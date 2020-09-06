package com.example.dkdus.triptonature.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dkdus.triptonature.model.Place
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.util.place.NaturePlaceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    fun callPlace(page: Int) : MutableLiveData<MutableList<Item>>{
        var data : MutableList<Item> = mutableListOf()
        var result : MutableLiveData<MutableList<Item>> = MutableLiveData()
        val placeUtil = NaturePlaceUtil()

        placeUtil.getApi().getTest("AppTesting", "AND", "json", page.toString())
            .enqueue(object : Callback<Place> {
                override fun onResponse(call: Call<Place>, response: Response<Place>) {
                    var temp = response.body()?.response?.body?.items?.item
                    result.value = temp
                }

                override fun onFailure(call: Call<Place>, t: Throwable) {
                    Log.d("failure", "onfailure " + t.message)
                }
            })

        return result
    }

    fun callArea(page: Int) {

    }

}