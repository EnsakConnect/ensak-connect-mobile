package com.ensak.connect.view_model.LoginViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ensak.connect.core.SessionManager;
import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.repositories.LoginRepository;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;


public class LoginViewModel extends AndroidViewModel {


    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginResponse> loginResponseLiveData;
    private final SessionManager sessionManager;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance(application).create(ApiRequest.class);
        loginRepository = new LoginRepository(apiRequest);
        loginResponseLiveData = new MutableLiveData<>();
        sessionManager = new SessionManager(application);
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }

    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        LiveData<LoginResponse> responseLiveData = loginRepository.loginUser(loginRequest);
        Observer<LoginResponse> observer = new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                loginResponseLiveData.setValue(loginResponse);
                if (loginResponse != null && loginResponse.getToken() != null) {
                    sessionManager.createSession(loginResponse.getToken());
                }
                // Remove the observer to prevent multiple subscriptions
                responseLiveData.removeObserver(this);
            }
        };

        responseLiveData.observeForever(observer);
    }

}



