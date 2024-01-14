package com.ensak.connect.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.profile.CertificateRepository;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.profile.EducationRepository;
import com.ensak.connect.repository.profile.ExperienceRepository;
import com.ensak.connect.repository.profile.ProfileRepository;
import com.ensak.connect.repository.resource.ResourceRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ProfileDetailedResponse> profile = new MutableLiveData<>();
    private MutableLiveData<String> cvFile = new MutableLiveData<>();
    private MutableLiveData<Boolean> isDownloading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private Integer userId;


    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(MutableLiveData<String> successMessage) {
        this.successMessage = successMessage;
    }

    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private ProfileRepository profileRepository;
    private EducationRepository educationRepository;
    private ExperienceRepository experienceRepository;
    private CertificateRepository certificateRepository;
    private ResourceRepository resourceRepository;


    @Inject
    public ProfileViewModel(
            ProfileRepository profileRepository,
            EducationRepository educationRepository,
            ExperienceRepository experienceRepository,
            CertificateRepository certificateRepository,
            ResourceRepository resourceRepository
    ) {
        this.profileRepository = profileRepository;
        this.educationRepository = educationRepository;
        this.experienceRepository = experienceRepository;
        this.certificateRepository = certificateRepository;
        this.resourceRepository = resourceRepository;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void fetchProfileData() {
        isLoading.setValue(true);
        profileRepository.getProfile(userId, new RepositoryCallBack<ProfileDetailedResponse>() {
            @Override
            public void onSuccess(ProfileDetailedResponse data) {
                profile.setValue(data);
                isLoading.setValue(false);
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load profile");
                isLoading.setValue(false);
            }
        });
    }

    public void deleteEducation(int educationId) {
        isLoading.setValue(true);
        educationRepository.deleteEducation(educationId, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Handle the successful deletion
                // You might need to update your LiveData that holds the list of educations
                fetchProfileData();
                isLoading.setValue(false);
                successMessage.setValue("Education deleted successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Handle the failure case
                errorMessage.setValue("Error deleting education: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void deleteExperience(int experienceId) {
        isLoading.setValue(true);
        experienceRepository.deleteExperience(experienceId, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Handle the successful deletion
                // You might need to update your LiveData that holds the list of educations
                fetchProfileData(); // Optionally, fetch the updated profile data
                isLoading.setValue(false);
                successMessage.setValue("Education deleted successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Handle the failure case
                errorMessage.setValue("Error deleting education: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }


    public void deleteCertification(int certificationId) {
        isLoading.setValue(true);
        certificateRepository.deleteCertification(certificationId, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                // Handle the successful deletion
                // You might need to update your LiveData that holds the list of educations
                fetchProfileData(); // Optionally, fetch the updated profile data
                isLoading.setValue(false);
                successMessage.setValue("Education deleted successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Handle the failure case
                errorMessage.setValue("Error deleting education: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void updateResume(Integer resourceId) {
        isLoading.setValue(true);
        profileRepository.updateResume(resourceId, new RepositoryCallBack<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                isLoading.setValue(false);
                successMessage.setValue("Resume uploaded successfully");
                fetchProfileData();
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error: could not update resume");
            }
        });
    }

    public void downloadResume() {
        String resourceName = profile.getValue().getResume();
        String downLoadedName = "CV " + profile.getValue().getFullName() + ".pdf";
        isDownloading.setValue(true);
        resourceRepository.downloadResource(resourceName, downLoadedName, new RepositoryCallBack<String>() {
            @Override
            public void onSuccess(String name) {
                isDownloading.setValue(false);
                cvFile.setValue(name);
                successMessage.setValue("Cv downloaded successfully.");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isDownloading.setValue(false);
                errorMessage.setValue(throwable.getMessage());
            }
        });
    }

    public LiveData<ProfileDetailedResponse> getProfile() {
        return profile;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public LiveData<String> getCvName() {
        return cvFile;
    }
    public LiveData<Boolean> getIsDownloading() {
        return isDownloading;
    }
}
