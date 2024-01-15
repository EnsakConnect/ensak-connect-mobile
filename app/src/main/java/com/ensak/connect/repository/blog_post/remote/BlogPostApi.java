package com.ensak.connect.repository.blog_post.remote;

import com.ensak.connect.repository.blog_post.model.BlogPostCommentResponse;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BlogPostApi {

    @GET("blog-posts/{job_post_id}/comments")
    Call<ArrayList<BlogPostCommentResponse>> getComments(@Path("job_post_id") String postId);

    @POST("blog-posts/{job_post_id}/comments")
    Call<BlogPostCommentResponse> sendComment(@Path("job_post_id") String postId, @Body String content);
}
