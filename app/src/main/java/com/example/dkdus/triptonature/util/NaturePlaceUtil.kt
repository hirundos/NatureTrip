package com.example.dkdus.triptonature.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class NaturePlaceUtil {
    var natureplaceAPI: NaturePlaceAPI
    init {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.visitkorea.or.kr/openapi/service/rest/GreenTourService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
       natureplaceAPI  = retrofit.create(NaturePlaceAPI::class.java)
    }
    public fun getApi() : NaturePlaceAPI {
        return natureplaceAPI
    }
}