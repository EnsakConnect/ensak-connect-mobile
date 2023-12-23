package com.ensak.connect.view_model.ProfileViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.reponse.ProfileResponse;
import com.ensak.connect.repositories.ProfileRepository;

public class ProfileViewModel extends ViewModel {

    private LiveData<ProfileResponse> profileLiveData;
    private ProfileRepository profileRepository;

    public ProfileViewModel(Context context) {
        profileRepository = ProfileRepository.getInstance(context);
        profileLiveData = profileRepository.getProfile();
        Log.d("ProfileViewModel", "ViewModel initialized");
    }

    public LiveData<ProfileResponse> getProfileLiveData() {
        return profileLiveData;
    }

    public void fetchProfileData() {
        Log.d("ProfileViewModel", "Fetching profile data");
        profileLiveData = profileRepository.getProfile();
    }
}
