package com.ensak.connect.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.model.ProfileResponse;
import com.ensak.connect.repository.profile.ProfileRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ProfileResponse> profile = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private ProfileRepository profileRepository;

    @Inject
    public ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void fetchProfileData() {
        isLoading.setValue(true);
        profileRepository.getProfile(4, new RepositoryCallBack<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
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

    public LiveData<ProfileResponse> getProfile() {
        return profile;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
