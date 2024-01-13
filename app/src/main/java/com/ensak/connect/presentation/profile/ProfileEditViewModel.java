package com.ensak.connect.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.ProfileRepository;
import com.ensak.connect.repository.profile.model.ProfileInformationRequest;
import com.ensak.connect.repository.profile.model.ProfileResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileEditViewModel extends ViewModel {
    private MutableLiveData<ProfileInformationRequest> information = new MutableLiveData<>(new ProfileInformationRequest());
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private ProfileRepository profileRepository;
    @Inject
    public ProfileEditViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void refresh() {
        isLoading.setValue(true);
        profileRepository.getProfile(1, new RepositoryCallBack<ProfileResponse>() {
            @Override
            public void onSuccess(ProfileResponse data) {
                ProfileInformationRequest refreshedData = new ProfileInformationRequest();
                refreshedData.setFullName(data.getFullName());
                refreshedData.setTitle(data.getTitle());
                refreshedData.setDescription(data.getDescription());
                information.setValue(refreshedData);
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

    public LiveData<ProfileInformationRequest> getInformation() {
        return information;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
