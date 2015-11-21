package com.legacy.sifarish.API;

import com.legacy.sifarish.POJO.FourSquareResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit.http.GET;
import retrofit.http.Query;

public interface IApiMethods {
//    @GET("/get/curators.json")
//    Curator getCurators(
//            @Query("api_key") String key
//    );
    //    (@Path("id") int groupId)
    @GET("/users/self")
    FourSquareResponse search(
            @Query("oauth_token") String searchTerm,
            @Query("v") String date
    );
}
