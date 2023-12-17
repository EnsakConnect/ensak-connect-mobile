package com.ensak.connect.repositories.question_post;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.question_post.remote.QuestionApi;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostRequest;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostResponse;
import com.ensak.connect.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionRepository {
    private final String TAG = getClass().getSimpleName();
    private QuestionApi api;

    public QuestionRepository(Context context) {
        api = RetrofitRequest.getRetrofitInstance(context).create(QuestionApi.class);
    }

    public void create(QuestionPostRequest request, RepositoryCallBack<QuestionPostResponse> callBack) {
        api.create(request).enqueue(new Callback<QuestionPostResponse>() {
            @Override
            public void onResponse(Call<QuestionPostResponse> call, Response<QuestionPostResponse> response) {
                Log.d(TAG, "Res: " + response.errorBody());
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    callBack.onFailure(new Exception("Error creating question"));
                }
            }

            @Override
            public void onFailure(Call<QuestionPostResponse> call, Throwable t) {
                callBack.onFailure(new Exception("Error creating question"));
            }
        });
    }
}