package com.ensak.connect.repository.profile.remote;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.model.Skill;
import com.ensak.connect.repository.notification.model.NotificationResponse;
import com.ensak.connect.repository.profile.model.CertificateRequest;
import com.ensak.connect.repository.profile.model.CertificateResponse;
import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.profile.model.SkillRequest;
import com.ensak.connect.repository.profile.model.SkillResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @GET("profile/skills")
    Call<List<Skill>> getSkill();

    @PUT("profile/profile-picture/{resource_id}")
    Call<UserResponse> updateProfilePicture(@Path("resource_id") Integer resource_id);

    @PUT("profile/banner/{resource_id}")
    Call<UserResponse> updateProfileBanner(@Path("resource_id") Integer resource_id);

    @POST("profile/skills")
    Call<SkillResponse> addSkill(@Body SkillRequest skillRequest);

    @DELETE("profile/skills/{skillId}")
    Call<Void> deleteSkill(@Path("skillId") int skillId);

    @DELETE("profile/experiences/{educationId}")
    Call<Void> deleteExperience(@Path("educationId") int educationId);

    @DELETE("profile/educations/{experiencesId}")
    Call<Void> deleteEducation(@Path("experiencesId") int skillId);

    @POST("profile/certifications")
    Call<CertificateResponse> addCertification(@Body CertificateRequest certificateRequest);

    @DELETE("profile/certifications/{certificationId}")
    Call<Void> deleteCertification(@Path("certificationId") int certificationId);

}
