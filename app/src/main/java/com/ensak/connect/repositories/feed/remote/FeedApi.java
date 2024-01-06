package com.ensak.connect.repositories.feed.remote;

import com.ensak.connect.reponse.feed.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FeedApi {
    @GET("feeds")
    Call<FeedResponse> getFeed(@Query("page") int page,
                               @Query("size") int size,
                               @Query("search") String search,
                               @Query("filter") String filter);

}
