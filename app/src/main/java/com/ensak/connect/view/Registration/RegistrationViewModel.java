package com.ensak.connect.view.Registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.core.SessionManager;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.auth.AuthRepository;
import com.ensak.connect.repositories.auth.remote.dto.AuthenticationResponse;
import com.ensak.connect.repositories.auth.remote.dto.RegisterRequest;

public class RegistrationViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private SessionManager sessionManager;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasRegistered = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMsg = new MutableLiveData<>("");

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        sessionManager = new SessionManager(application);
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
        request.setRole(accountType);
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
