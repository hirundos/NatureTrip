package com.example.dkdus.triptonature.model.place_material;

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var addr: String?,
    var areacode : Int,
    var contentid: String?,
    var createdtime: String?,
    var mainimage: String? ,
    var modifiedtime: String? ,
    var readcount: Int,
    var sigungucode : Int,
    var summary: String?,
    var tel: String?,
    var telname: String? ,
    var title: String?
) : Parcelable