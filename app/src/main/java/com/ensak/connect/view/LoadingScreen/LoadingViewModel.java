package com.ensak.connect.view.LoadingScreen;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.core.SessionManager;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.auth.AuthRepository;
import com.ensak.connect.repositories.auth.model.UserResponse;
import com.ensak.connect.repositories.health.HealthRepository;
import com.ensak.connect.repositories.health.model.HealthResponse;

import org.jetbrains.annotations.NotNull;

public class LoadingViewModel extends AndroidViewModel {
    public enum REDIRECT_TO {
        LOGIN,
        HOME
    }
    private final String TAG = getClass().getSimpleName();
    private HealthRepository healthRepository;
    private AuthRepository authRepository;
    private SessionManager sessionManager;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private final MutableLiveData<Boolean> isError = new MutableLiveData<>(true);
    private final MutableLiveData<String> currentAction = new MutableLiveData<>("");
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<REDIRECT_TO> redirectTo = new MutableLiveData<>(null);

    public LoadingViewModel(@NotNull Application application) {
        super(application);
        healthRepository = new HealthRepository(application);
        authRepository = new AuthRepository(application);
        sessionManager = new SessionManager(application);
    }

    public void startChecks() {
        isLoading.setValue(true);
        checkServerStatus();
    }

    private void checkServerStatus() {
        currentAction.setValue("Checking server status...");
        healthRepository.healthCheck(new RepositoryCallBack<HealthResponse>() {
            @Override
            public void onSuccess(HealthResponse data) {
                Log.d(TAG, "Res: " + data);
                if(!data.getSuccess()) {
                    isError.setValue(true);
                    errorMessage.setValue("Server error, returned success = false");
                }
                isError.setValue(false);
                currentAction.setValue("Connected to server successfully.");
                checkTokenIsValid();
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                isError.setValue(true);
                errorMessage.setValue("Could not connect to server, check if the server is running!");
            }
        });
    }

    private void checkTokenIsValid() {
        if(! sessionManager.isLoggedIn()){
            redirectToLogin();
            return;
        }
        currentAction.setValue("Verifying user token...");
        authRepository.checkToken(new RepositoryCallBack<UserResponse>() {
            @Override
            public void onSuccess(UserResponse data) {
                isError.setValue(false);
                isLoading.setValue(false);
                currentAction.setValue("Token is valid, id: "+data.getId()+", email: " + data.getEmail());
                redirectedToHome();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.w(TAG, throwable.getMessage());
                isError.setValue(false);
                isLoading.setValue(false);
                currentAction.setValue("Invalid user token, redirecting to login");
                sessionManager.logoutUser();
                redirectToLogin();
            }
        });
    }

    private void redirectToLogin() {
        redirectTo.setValue(REDIRECT_TO.LOGIN);
    }

    private void redirectedToHome() {
        redirectTo.setValue(REDIRECT_TO.HOME);
    }

    public void setIsLoading(Boolean loading) {
        isLoading.setValue(loading);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }

    public LiveData<String> getCurrentAction() {
        return currentAction;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<REDIRECT_TO> getRedirectionTo() {
        return redirectTo;
    }
}
