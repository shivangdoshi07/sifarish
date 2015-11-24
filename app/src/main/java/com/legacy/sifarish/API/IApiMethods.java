package com.legacy.sifarish.API;

import com.legacy.sifarish.POJO.FourSquareResponse;
import com.legacy.sifarish.POJO.RecommendationItem;
import com.legacy.sifarish.POJO.UserPost;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface IApiMethods {

    @GET("/users/self/checkins")
    FourSquareResponse search(
            @Query("oauth_token") String searchTerm,
            @Query("v") String date
    );

    @GET("/recommend")
    ArrayList<RecommendationItem> getRecommendation(@Query("userId") String userId);

    @POST("/user")
    UserPost createUser(@Body UserPost user);
}
