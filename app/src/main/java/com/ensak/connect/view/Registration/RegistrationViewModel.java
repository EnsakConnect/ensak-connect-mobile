package com.ensak.connect.view.Registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.core.SessionManager;
import com.ensak.connect.repositories.auth.AuthRepository;

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
        // Call auth repository
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
