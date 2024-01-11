package com.ensak.connect.presentation.auth.password_reset;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.PasswordResetRequest;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class PasswordResetViewModel extends ViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public PasswordResetViewModel(AuthRepository authRepository){
        this.authRepository = authRepository;
    }

    public void sendPasswordResetRequest(String email) {
        if(email.isEmpty()){
            errorMessage.setValue("Email cannot be empty");
            return;
        }
        isLoading.setValue(true);
        PasswordResetRequest request = new PasswordResetRequest();
        request.setEmail(email);
        authRepository.passwordReset(request, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void data) {
                isSuccess.setValue(true);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error: could not send request, try again.");
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
