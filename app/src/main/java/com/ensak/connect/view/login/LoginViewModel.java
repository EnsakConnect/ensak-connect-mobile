package com.ensak.connect.view.login;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.core.SessionManager;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.auth.AuthRepository;
import com.ensak.connect.repositories.auth.model.LoginRequest;
import com.ensak.connect.repositories.auth.model.AuthenticationResponse;


public class LoginViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private SessionManager sessionManager;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasLoggedIn = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMsg = new MutableLiveData<>("");


    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        sessionManager = new SessionManager(application);
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



