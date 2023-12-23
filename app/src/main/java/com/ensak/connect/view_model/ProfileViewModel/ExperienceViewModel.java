package com.ensak.connect.view_model.ProfileViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.models.ExperienceRequest;
import com.ensak.connect.reponse.ExperienceResponse;
import com.ensak.connect.repositories.ProfileRepository;
import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.question_post.QuestionRepository;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostRequest;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

public class ExperienceViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private ProfileRepository profileRepository;

    public ExperienceViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
    }

    public void createExperience(String title, String company, String contractType,String location, String from, String to, String description) {
        if(title.length() == 0) {
            errorMessage.setValue("Title is required.");
            return;
        }
        if(company.length() == 0) {
            errorMessage.setValue("Company is required.");
            return;
        }
        if(location.length() == 0) {
            errorMessage.setValue("Location is required.");
            return;
        }
        if(description.length() == 0) {
            errorMessage.setValue("Description is required.");
            return;
        }
        isLoading.setValue(true);
        ExperienceRequest request = new ExperienceRequest();
        request.setPositionTitle(title);
        request.setCompanyName(company);
        request.setLocation(location);
        request.setStartDate(from);
        request.setEndDate(to);
        request.setDescription(description);
        request.setContractType(contractType);
        profileRepository.uploadExperience(request);
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
