package com.ensak.connect.presentation.chat.conversation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.SearchProfileRepository;
import com.ensak.connect.repository.profile.model.ProfileResponseDTO;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddConversationViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProfileResponseDTO>> profiles = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private SearchProfileRepository repository;

    @Inject
    public AddConversationViewModel(SearchProfileRepository repository) {
        this.repository = repository;
    }

    public void searchProfiles(String name) {
        isLoading.setValue(true);
        repository.searchProfiles(name, new RepositoryCallBack<ArrayList<ProfileResponseDTO>>() {
            @Override
            public void onSuccess(ArrayList<ProfileResponseDTO> data) {
                profiles.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not search profiles");
                isLoading.setValue(false);
            }
        });
    }

    public LiveData<ArrayList<ProfileResponseDTO>> getProfilesList() {
        return profiles;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
