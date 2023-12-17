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

    // Constructor
    public ProfileViewModel(Context context) {
        profileRepository = ProfileRepository.getInstance(context);
        profileLiveData = profileRepository.getProfile();
        Log.d("ProfileViewModel", "ViewModel initialized");
    }

    // Method to expose LiveData to the UI
    public LiveData<ProfileResponse> getProfileLiveData() {
        return profileLiveData;
    }

    // Method to trigger fetching profile data
    public void fetchProfileData() {
        Log.d("ProfileViewModel", "Fetching profile data");
        profileLiveData = profileRepository.getProfile();
    }
}
