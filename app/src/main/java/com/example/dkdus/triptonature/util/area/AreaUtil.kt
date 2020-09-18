package com.example.dkdus.triptonature.util.area

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class AreaUtil {
    var areaAPI: AreaAPI
    init {
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.visitkorea.or.kr/openapi/service/rest/GreenTourService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        areaAPI  = retrofit.create(AreaAPI::class.java)
    }
    public fun getApi() : AreaAPI {
        return areaAPI
    }
}