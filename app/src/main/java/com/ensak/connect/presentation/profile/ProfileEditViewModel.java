package com.ensak.connect.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.ProfileRepository;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileEditViewModel extends ViewModel {
    private MutableLiveData<ProfileDetailedResponse> information = new MutableLiveData<>(new ProfileDetailedResponse());
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private ProfileRepository profileRepository;
    @Inject
    public ProfileEditViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void refresh() {
        isLoading.setValue(true);
        profileRepository.getProfile(4, new RepositoryCallBack<ProfileDetailedResponse>() {
            @Override
            public void onSuccess(ProfileDetailedResponse data) {
                information.setValue(data);
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
                isSuccess.setValue(true);
                information.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                isSuccess.setValue(false);
                errorMessage.setValue("Error occurred while updating profile, try again");
            }
        });
    }

    public LiveData<ProfileDetailedResponse> getInformation() {
        return information;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
