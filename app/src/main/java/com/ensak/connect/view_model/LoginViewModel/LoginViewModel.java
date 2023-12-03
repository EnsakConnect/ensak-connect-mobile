package com.ensak.connect.view_model.LoginViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ensak.connect.models.LoginRequest;
import com.ensak.connect.reponse.LoginResponse;
import com.ensak.connect.repositories.LoginRepository;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;


public class LoginViewModel extends AndroidViewModel {

    private final LoginRepository loginRepository;
    private final MutableLiveData<LoginResponse> loginResponseLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        ApiRequest apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        loginRepository = new LoginRepository(apiRequest);
        loginResponseLiveData = new MutableLiveData<>();
    }

    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }

    public void login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        LiveData<LoginResponse> responseLiveData = loginRepository.loginUser(loginRequest);
        responseLiveData.observeForever(new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                loginResponseLiveData.setValue(loginResponse);
                responseLiveData.removeObserver(this); // Avoid multiple subscriptions
            }
        });
    }
}



