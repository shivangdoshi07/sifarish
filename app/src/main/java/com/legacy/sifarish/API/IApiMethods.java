package com.legacy.sifarish.API;

import com.legacy.sifarish.POJO.FourSquareResponse;

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods {

    @GET("/users/self/checkins")
    FourSquareResponse search(
            @Query("oauth_token") String searchTerm,
            @Query("v") String date
    );
}
