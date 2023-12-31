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
    @GET("job-posts")
    Call<ArrayList<JobPostResponse>> getPosts();

    @GET("job-posts/{job_post_id}/comments")
    Call<ArrayList<JobPostCommentResponse>> getComments(@Path("job_post_id") String postId);

    @POST("job-posts/{job_post_id}/comments")
    Call<JobPostCommentResponse> sendComment(@Path("job_post_id") String postId, @Body String content);

    @GET("conversations")
    Call<ArrayList<ConversationResponse>> getConversations();

    @POST("conversations")
    Call<ConversationResponse> addConversation();

    @GET("chat/{conversation_id}")
    Call<ArrayList<ChatMessageResponse>> getChatMessages(@Path("conversation_id") String conversationId);

    @POST("chat/{conversation_id}")
    Call<ChatMessageResponse> sendChatMessage(@Path("conversation_id") String conversationId, @Body String message);

    @POST("auth/password-reset")
    Call<PasswordResetRequest> sendmail(@Body PasswordResetRequest emailrequest);

    @POST("auth/password-reset/verify")
    Call<CodeVerificationRequest> sendcode(@Body CodeVerificationRequest codeVerificationRequest);

    @POST("auth/change-password")
    Call<ChangePasswordRequest> changepasswd(@Body ChangePasswordRequest changePasswordRequest);

    @GET("profile/52/detailed")
    Call<ProfileResponse> getUserProfile();

    @GET("notifications")
    Call<List<NotificationResponse>> getAllNotifications();

    @POST("profile/experiences")
    Call<ExperienceResponse> UploadExperience(@Body ExperienceRequest experienceRequest);

    @POST("profile/educations")
    Call<EducationResponse> UploadEducation(@Body EducationRequest educationRequest);

    @PUT("profile/experiences/{experience_id}")
    Call<ExperienceResponse> UpdateExperience(@Path("experience_id") String experienceId, @Body ExperienceRequest experienceRequest);

    @PUT("profile/educations/{educations_id}")
    Call<EducationResponse> UpdateEducation(@Path("education_id") String educationIf, @Body EducationRequest educationRequest);
}
