package com.ensak.connect.presentation.auth.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.service.SessionManagerService;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.AuthenticationResponse;
import com.ensak.connect.repository.auth.model.RegisterRequest;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegistrationViewModel extends ViewModel {
    private AuthRepository authRepository;
    private SessionManagerService sessionManager;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasRegistered = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMsg = new MutableLiveData<>("");

    @Inject
    public RegistrationViewModel(AuthRepository authRepository, SessionManagerService sessionManager) {
        this.authRepository = authRepository;
        this.sessionManager = sessionManager;
    }

    public void register(String fullName, String email, String accountType, String password, String passwordConfirmation) {
        if(!password.equals(passwordConfirmation)){
            errorMsg.setValue("Password confirmation does not match.");
            return;
        }
        if(password.length() < 8){
            errorMsg.setValue("Password too short, must have at least 8 characters.");
            return;
        }
        isLoading.setValue(true);
        RegisterRequest request = new RegisterRequest();
        request.setFullname(fullName);
        request.setEmail(email);
        request.setRole(accountType.toUpperCase());
        request.setPassword(password);
        authRepository.register(request, new RepositoryCallBack<AuthenticationResponse>() {
            @Override
            public void onSuccess(AuthenticationResponse data) {
                sessionManager.setUserToken(data.getToken());
                hasRegistered.setValue(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMsg.setValue("An error occurred, please try again.");
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getHasRegistered() {
        return hasRegistered;
    }

    public LiveData<String> getErrorMsg() {
        return errorMsg;
    }
}
