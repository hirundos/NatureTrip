package com.example.dkdus.triptonature.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dkdus.triptonature.model.Area
import com.example.dkdus.triptonature.model.Place
import com.example.dkdus.triptonature.model.area_material.Items
import com.example.dkdus.triptonature.model.place_material.Item
import com.example.dkdus.triptonature.util.area.AreaUtil
import com.example.dkdus.triptonature.util.place.NaturePlaceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    fun callPlace(siArea : Int=0, arr : String) : MutableLiveData<MutableList<Item>>{
        var result : MutableLiveData<MutableList<Item>> = MutableLiveData()
        val placeUtil = NaturePlaceUtil()

        if(siArea != 0) {
            placeUtil.getApi().getPlaceSi("AppTesting", "AND", "json", "224",siArea.toString(),arr)
                .enqueue(object : Callback<Place>{
                    override fun onResponse(call: Call<Place>, response: Response<Place>) {
                        result.value = response.body()?.response?.body?.items?.item
                    }
                    override fun onFailure(call: Call<Place>, t: Throwable) {
                        Log.d("failure", "onfailure " + t.message)
                    }
                })
        }
        else{
            placeUtil.getApi().getPlace("AppTesting", "AND", "json", "224", arr)
                .enqueue(object : Callback<Place> {
                    override fun onResponse(call: Call<Place>, response: Response<Place>) {
                        result.value = response.body()?.response?.body?.items?.item
                    }
                    override fun onFailure(call: Call<Place>, t: Throwable) {
                        Log.d("failure", "onfailure " + t.message)
                    }
                })
        }

        return result
    }

    fun callArea(areaCode : Int) : MutableLiveData<Items>{
        var result : MutableLiveData<Items> = MutableLiveData()
        val areaUtil = AreaUtil()

        areaUtil.getApi().getArea("AppTesting", "AND", "json", areaCode.toString(), "100")
            .enqueue(object : Callback<Area>{
                override fun onResponse(call: Call<Area>, response: Response<Area>) {
                    var temp = response.body()?.response?.body?.items
                    result.value = temp
                }

                override fun onFailure(call: Call<Area>, t: Throwable) {
                    Log.d("failure", "onfailure " + t.message)
                }
            })
        return result
    }

}