
package com.example.dkdus.triptonature.model;

import androidx.room.Room;

import com.example.dkdus.triptonature.model.place_material.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
