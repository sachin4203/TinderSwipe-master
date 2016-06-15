package com.sachin.tinderswipe;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by kapil pc on 6/15/2016.
 */
public interface PicturesApi {

    @GET("/api/json/get/bHYevTXZpe?indent=2")
    public void getpics(Callback<List<PicturesData>> response);
}
