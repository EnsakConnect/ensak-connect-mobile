package com.ensak.connect.presentation.auth.login;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.service.SessionManagerService;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.LoginRequest;
import com.ensak.connect.repository.auth.model.AuthenticationResponse;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class LoginViewModel extends ViewModel {

    private AuthRepository authRepository;
    private SessionManagerService sessionManager;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasLoggedIn = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMsg = new MutableLiveData<>("");


    @Inject
    public LoginViewModel(AuthRepository authRepository, SessionManagerService sessionManager) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManager;
    }

    public void login(String email, String password) {
        isLoading.setValue(true);
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);
        authRepository.login(request, new RepositoryCallBack<AuthenticationResponse>() {
            @Override
            public void onSuccess(AuthenticationResponse data) {
                sessionManager.setUserToken(data.getToken());
                hasLoggedIn.setValue(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                hasLoggedIn.setValue(false);
                errorMsg.setValue("An error occurred, please try again.");
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getHasLoggedIn() {
        return hasLoggedIn;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }

}



