package com.example.dkdus.triptonature.util.place;

import com.example.dkdus.triptonature.model.Place;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NaturePlaceAPI {
    @GET("areaBasedList?ServiceKey=SNdFcz5%2F5pmDgBHMdTi%2F00QJ%2FNyiKAqKSi4peru95KPCdNNcmTUZx3E2uM87Kp0gEialj1Z2G2ap9mQq%2FA%2F4bw%3D%3D")
    Call<Place> getPlace(@Query("MobileApp") String name, @Query("MobileOS") String os,
                        @Query("_type") String type, @Query("numOfRows") String row, @Query("arrange") String arr);
}
