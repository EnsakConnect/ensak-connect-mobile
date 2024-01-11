package com.ensak.connect.service.retrofit;

import com.ensak.connect.repository.auth.model.ChangePasswordRequest;
import com.ensak.connect.repository.auth.model.CodeVerificationRequest;
import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.auth.model.PasswordResetRequest;
import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.repository.job_post.model.JobPostResponse;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.repository.profile.model.ProfileResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRequest {
    @POST("auth/password-reset")
    Call<PasswordResetRequest> sendmail(@Body PasswordResetRequest emailrequest);

    @POST("auth/password-reset/verify")
    Call<CodeVerificationRequest> sendcode(@Body CodeVerificationRequest codeVerificationRequest);

    @POST("auth/change-password")
    Call<ChangePasswordRequest> changepasswd(@Body ChangePasswordRequest changePasswordRequest);

    @GET("notifications")
    Call<List<NotificationResponse>> getAllNotifications();
}
