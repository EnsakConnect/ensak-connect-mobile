package com.ensak.connect.view.login;

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
import com.ensak.connect.repositories.auth.AuthRepository;
import com.ensak.connect.retrofit.ApiRequest;
import com.ensak.connect.retrofit.RetrofitRequest;


public class LoginViewModel extends AndroidViewModel {

    private AuthRepository authRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> hasLoggedIn = new MutableLiveData<>(false);
    private SessionManager sessionManager;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
        sessionManager = new SessionManager(application);
    }

    public void login(String email, String password) {
        isLoading.setValue(true);
        // Send login request using auth repository
    }

}



