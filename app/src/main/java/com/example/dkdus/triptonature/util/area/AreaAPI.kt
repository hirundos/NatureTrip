package com.example.dkdus.triptonature.util.area

import com.example.dkdus.triptonature.model.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaAPI {
    @GET("areaBasedList?ServiceKey=SNdFcz5%2F5pmDgBHMdTi%2F00QJ%2FNyiKAqKSi4peru95KPCdNNcmTUZx3E2uM87Kp0gEialj1Z2G2ap9mQq%2FA%2F4bw%3D%3D")
    fun getTest(
        @Query("MobileApp") name: String?, @Query("MobileOS") os: String?,
        @Query("_type") type: String?, @Query("numOfRows") page: String?
    ): Call<Place?>?
//numOfRows = 100
}