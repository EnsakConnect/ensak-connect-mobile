package com.ensak.connect.repository.feed.remote;

import com.ensak.connect.repository.feed.model.FeedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FeedApi {
    @GET("feeds")
    Call<FeedResponse> getFeed(
            @Query("page") int page,
            @Query("size") int size,
            @Query("search") String search,
            @Query("filter") String filter);

    @GET("profile/search/{fullname}")
    Call<FeedResponse> getProfiles(
            @Path("fullname") String fullname,
            @Query("page") int page,
            @Query("size") int size
            );

}
