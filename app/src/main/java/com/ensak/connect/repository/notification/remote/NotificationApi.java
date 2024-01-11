package com.ensak.connect.repository.notification.remote;

import com.ensak.connect.repository.notification.model.NotificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NotificationApi {

    @GET("notifications")
    Call<List<NotificationResponse>> getAllNotifications();
}
