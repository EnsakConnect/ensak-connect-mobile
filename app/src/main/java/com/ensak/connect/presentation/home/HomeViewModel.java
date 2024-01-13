package com.ensak.connect.presentation.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.UserResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.service.SessionManagerService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private AuthRepository authRepository;
    private SessionManagerService sessionManager;
    private MutableLiveData<UserResponse> userData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<Boolean> isError = new MutableLiveData<>(false);

    @Inject
    public HomeViewModel(AuthRepository authRepository, SessionManagerService sessionManagerService) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManagerService;
    }

    public void getAuthenticatedUser() {
        isLoading.setValue(true);
        authRepository.checkToken(new RepositoryCallBack<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                sessionManager.setUserId(data.getId());
                userData.setValue(data);
                isLoading.setValue(false);
                isError.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                isError.setValue(true);
            }
        });
    }

    public LiveData<UserResponse> getUserData() {
        return userData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }
}
