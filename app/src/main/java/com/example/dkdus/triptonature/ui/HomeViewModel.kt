package com.example.dkdus.triptonature.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dkdus.triptonature.model.Place
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.util.NaturePlaceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    var data : MutableList<Item> = mutableListOf()

    fun call() : MutableLiveData<MutableList<Item>>{
        var result : MutableLiveData<MutableList<Item>> = MutableLiveData()
        val placeUtil = NaturePlaceUtil()
        placeUtil.getApi().getTest("AppTesting", "AND", "json")
            .enqueue(object : Callback<Place?> {
                override fun onResponse(call: Call<Place?>, response: Response<Place?>) {
                    var temp = response.body()?.response?.body?.items?.item

                    for((index, value)in temp?.withIndex()!!){
                        data.add(value)
                    }
                    result.value = data
                }

                override fun onFailure(call: Call<Place?>, t: Throwable) {
                    Log.d("failure","onfailure "+t.message)
                }
            })
        return result
    }

}