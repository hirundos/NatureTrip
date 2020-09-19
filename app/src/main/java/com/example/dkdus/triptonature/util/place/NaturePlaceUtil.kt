package com.example.dkdus.triptonature.util.place

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class NaturePlaceUtil {
    var natureplaceAPI: NaturePlaceAPI
    var placeDatailSiAPI : PlaceDatailSiAPI
    var placeDatailGuAPI : PlaceDetailGuAPI

    init {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.visitkorea.or.kr/openapi/service/rest/GreenTourService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        natureplaceAPI  = retrofit.create(NaturePlaceAPI::class.java)
        placeDatailSiAPI  = retrofit.create(PlaceDatailSiAPI::class.java)
        placeDatailGuAPI  = retrofit.create(PlaceDetailGuAPI::class.java)
    }
    fun getApi() : NaturePlaceAPI {
        return natureplaceAPI
    }
    fun getSiApi() : PlaceDatailSiAPI {
        return placeDatailSiAPI
    }
    fun getGuApi() : PlaceDetailGuAPI {
        return placeDatailGuAPI
    }
}