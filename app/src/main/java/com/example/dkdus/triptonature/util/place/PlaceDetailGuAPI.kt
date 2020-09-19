package com.example.dkdus.triptonature.util.place

import com.example.dkdus.triptonature.model.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface PlaceDetailGuAPI {
    @GET("areaBasedList?ServiceKey=SNdFcz5%2F5pmDgBHMdTi%2F00QJ%2FNyiKAqKSi4peru95KPCdNNcmTUZx3E2uM87Kp0gEialj1Z2G2ap9mQq%2FA%2F4bw%3D%3D")
    fun getPlaceGu(
        @Query("MobileApp") name: String?, @Query("MobileOS") os: String?,
        @Query("_type") type: String?, @Query("numOfRows") row: String?,
        @Query("areaCode") areaCode : String?, @Query("sigunguCode") sigunguCode : String?, @Query("arrange") arr :String?
    ): Call<Place>
}