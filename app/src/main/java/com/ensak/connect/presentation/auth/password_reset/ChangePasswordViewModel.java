package com.ensak.connect.presentation.auth.password_reset;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.ChangePasswordRequest;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChangePasswordViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public ChangePasswordViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void changePassword(String password, String passwordConfirmation) {
        if(password.length() < 8) {
            errorMessage.setValue("Password must be at least 8 chars");
            return;
        }

        if(!password.equals(passwordConfirmation)) {
            errorMessage.setValue("Password does not match password confirmation");
            return;
        }

        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setPassword(password);
        isLoading.setValue(true);
        authRepository.changePassword(request, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void data) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                isSuccess.setValue(false);
                errorMessage.setValue("Failed to send request, try again");
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
