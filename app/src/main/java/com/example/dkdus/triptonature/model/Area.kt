package com.example.dkdus.triptonature.model

import com.example.dkdus.triptonature.model.area_material.Response
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Area (
    @SerializedName("response")
    @Expose
    var response: Response
)