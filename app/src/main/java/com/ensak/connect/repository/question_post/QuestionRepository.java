package com.ensak.connect.repository.question_post;

import android.content.Context;
import android.util.Log;

import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.question_post.remote.QuestionApi;
import com.ensak.connect.repository.question_post.model.QuestionPostRequest;
import com.ensak.connect.repository.question_post.model.QuestionPostResponse;
import com.ensak.connect.service.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionRepository {
    private final String TAG = getClass().getSimpleName();
    private QuestionApi api;

    public QuestionRepository(Context context) {
        api = RetrofitService.getRetrofitInstance(context).create(QuestionApi.class);
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
