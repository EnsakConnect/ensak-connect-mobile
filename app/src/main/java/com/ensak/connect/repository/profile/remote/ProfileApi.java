package com.ensak.connect.repository.profile.remote;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileApi {
    @GET("profile/{profile_id}/detailed")
    Call<ProfileDetailedResponse> getUserProfile(@Path("profile_id") Integer profileId);

    @PUT("profile")
    Call<ProfileDetailedResponse> updateProfile(@Body ProfileInformationRequest request);

    @POST("profile/experiences")
    Call<ExperienceResponse> addExperience(@Body ExperienceRequest experienceRequest);

    @POST("profile/educations")
    Call<EducationResponse> UploadEducation(@Body EducationRequest educationRequest);

    @PUT("profile/experiences/{experience_id}")
    Call<ExperienceResponse> updateExperience(@Path("experience_id") String experienceId, @Body ExperienceRequest experienceRequest);

    @PUT("profile/educations/{educations_id}")
    Call<EducationResponse> UpdateEducation(@Path("education_id") String educationIf, @Body EducationRequest educationRequest);

    @PUT("profile/profile-picture/{resource_id}")
    Call<UserResponse> updateProfilePicture(@Path("resource_id") Integer resource_id);

    @PUT("profile/banner/{resource_id}")
    Call<UserResponse> updateProfileBanner(@Path("resource_id") Integer resource_id);
}
