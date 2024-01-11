package com.ensak.connect.repository.notification;

import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.repository.notification.remote.NotificationApi;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationRepository {
    private final String TAG = getClass().getSimpleName();
    private NotificationApi api;

    @Inject
    public NotificationRepository(Retrofit retrofit) {
        api = retrofit.create(NotificationApi.class);
    }

    public void fetchNotification(RepositoryCallBack<List<NotificationResponse>> callBack) {
        api.getAllNotifications().enqueue(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(Call<List<NotificationResponse>> call, Response<List<NotificationResponse>> response) {
                if(!response.isSuccessful() || response.body() == null) {
                    callBack.onFailure(new Exception("Error fetching notifications"));
                } else {
                    callBack.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponse>> call, Throwable t) {
                callBack.onFailure(t);
            }
        });
    }
}
