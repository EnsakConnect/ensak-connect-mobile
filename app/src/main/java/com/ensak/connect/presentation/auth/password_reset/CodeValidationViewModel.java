package com.ensak.connect.presentation.auth.password_reset;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.auth.AuthRepository;
import com.ensak.connect.repository.auth.model.CodeVerificationRequest;
import com.ensak.connect.repository.auth.model.CodeVerificationResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;
import com.ensak.connect.service.SessionManagerService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CodeValidationViewModel extends ViewModel {
    private AuthRepository authRepository;
    private SessionManagerService sessionManagerService;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    @Inject
    public CodeValidationViewModel(AuthRepository authRepository, SessionManagerService sessionManager) {
        this.authRepository = authRepository;
        this.sessionManagerService = sessionManager;
    }

    public void sendCodeVerification(String email, String code) {
        if(code.isEmpty()){
            errorMessage.setValue("Code cannot be empty");
            return;
        }

        CodeVerificationRequest request = new CodeVerificationRequest();
        request.setEmail(email);
        request.setCode(code);
        isLoading.setValue(true);
        authRepository.sendCodeVerification(request, new RepositoryCallBack<CodeVerificationResponse>() {
            @Override
            public void onSuccess(CodeVerificationResponse data) {
                if(data.getStatus()){
                    sessionManagerService.createSession(data.getToken());
                    isSuccess.setValue(true);
                } else {
                    errorMessage.setValue("Please verify the code you provided.");
                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not verify code, try again");
                isLoading.setValue(false);
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
