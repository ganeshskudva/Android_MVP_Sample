package com.example.gkudva.doordash.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by gkudva on 25/08/17.
 */

public interface DoorDashService {

    public static final String BASE_URL = "https://api.doordash.com/v2/restaurant/";

    @GET("?lat=37.422740&lng=-122.139956")
    Call<List<Restaurant>> getResponse();

    class Factory {
        public static DoorDashService create()
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(DoorDashService.class);
        }
    }
}
