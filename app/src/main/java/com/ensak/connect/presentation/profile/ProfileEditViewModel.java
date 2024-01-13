package com.ensak.connect.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.profile.ProfileRepository;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileEditViewModel extends ViewModel {
    private MutableLiveData<ProfileDetailedResponse> information = new MutableLiveData<>(new ProfileDetailedResponse());
    private MutableLiveData<String> profilePicture = new MutableLiveData<>("");
    private MutableLiveData<String> profileBanner = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private Integer userId;

    private ProfileRepository profileRepository;
    @Inject
    public ProfileEditViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void refresh() {
        isLoading.setValue(true);
        profileRepository.getProfile(userId, new RepositoryCallBack<ProfileDetailedResponse>() {
            @Override
            public void onSuccess(ProfileDetailedResponse data) {
                information.setValue(data);
                profilePicture.setValue(data.getProfilePicture());
                profileBanner.setValue(data.getBanner());
                isLoading.setValue(false);
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error loading profile information");
            }
        });
    }

    public void saveInformation(ProfileInformationRequest request) {
        // TODO: validate data and set error
        isLoading.setValue(true);
        profileRepository.updateProfile(request, new RepositoryCallBack<ProfileDetailedResponse>() {
            @Override
            public void onSuccess(ProfileDetailedResponse data) {
                successMessage.setValue("Profile updated successfully");
                information.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                successMessage.setValue("");
                errorMessage.setValue("Error occurred while updating profile, try again");
            }
        });
    }

    public void updateProfilePicture(Integer resourceId) {
        isLoading.setValue(true);
        profileRepository.updateProfilePicture(resourceId, new RepositoryCallBack<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                successMessage.setValue("Profile picture updated successfully");
                refresh();
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error uploading profile picture");
            }
        });
    }

    public void updateProfileBanner(Integer resourceId) {
        isLoading.setValue(true);
        profileRepository.updateProfileBanner(resourceId, new RepositoryCallBack<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                successMessage.setValue("Profile banner updated successfully");
                refresh();
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error uploading profile banner");
            }
        });
    }

    public LiveData<ProfileDetailedResponse> getInformation() {
        return information;
    }

    public LiveData<String> getProfilePicture() {
        return profilePicture;
    }

    public LiveData<String> getProfileBanner() {
        return profileBanner;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
