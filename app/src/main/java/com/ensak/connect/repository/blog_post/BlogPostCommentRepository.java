package com.ensak.connect.repository.blog_post;

import androidx.annotation.NonNull;

import com.ensak.connect.repository.blog_post.remote.BlogPostApi;
import com.ensak.connect.repository.blog_post.model.BlogPostCommentResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BlogPostCommentRepository {
    private static final String TAG = BlogPostCommentRepository.class.getSimpleName();
    private final BlogPostApi apiRequest;

    @Inject
    public BlogPostCommentRepository(Retrofit retrofit) {
        apiRequest = retrofit.create(BlogPostApi.class);
    }

    public void getComments(String postId, RepositoryCallBack<ArrayList<BlogPostCommentResponse>> callBack) {
        apiRequest.getComments(postId).enqueue(new Callback<ArrayList<BlogPostCommentResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<BlogPostCommentResponse>> call, @NonNull Response<ArrayList<BlogPostCommentResponse>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Empty body"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<BlogPostCommentResponse>> call, @NonNull Throwable t) {
                callBack.onFailure(t);
            }
        });
    }

    public void sendComment(String postId, String comment, RepositoryCallBack<BlogPostCommentResponse> callBack) {
        apiRequest.sendComment(postId, comment).enqueue(new Callback<BlogPostCommentResponse>() {
            @Override
            public void onResponse(@NonNull Call<BlogPostCommentResponse> call, @NonNull Response<BlogPostCommentResponse> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Empty body"));
                    return;
                }
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<BlogPostCommentResponse> call, @NonNull Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
