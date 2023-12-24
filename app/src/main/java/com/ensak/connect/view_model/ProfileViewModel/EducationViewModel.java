package com.ensak.connect.view_model.ProfileViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.models.EducationRequest;
import com.ensak.connect.repositories.EducationRepository;
import com.ensak.connect.repositories.ProfileRepository;

public class EducationViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private EducationRepository educationRepository;

    public EducationViewModel(@NonNull Application application) {
        super(application);
        educationRepository = new EducationRepository(application);
    }

    public void createEducation(String field, String degree, String school, String from, String to, String description) {
        if(field.length() == 0) {
            errorMessage.setValue("Field is required.");
            return;
        }
        if(degree.length() == 0) {
            errorMessage.setValue("Degree is required.");
            return;
        }
        if(school.length() == 0) {
            errorMessage.setValue("School is required.");
            return;
        }
        if(description.length() == 0) {
            errorMessage.setValue("Description is required.");
            return;
        }
        isLoading.setValue(true);
        EducationRequest request = new EducationRequest();
        request.setField(field);
        request.setDegree(degree);
        request.setSchool(school);
        request.setStartDate(from);
        request.setEndDate(to);
        request.setDescription(description);
        educationRepository.uploadEducation(request);
    }

    public void updateExperience(String id, String field, String degree, String school, String from, String to, String description) {
        if(field.length() == 0) {
            errorMessage.setValue("Field is required.");
            return;
        }
        if(degree.length() == 0) {
            errorMessage.setValue("Degree is required.");
            return;
        }
        if(school.length() == 0) {
            errorMessage.setValue("School is required.");
            return;
        }
        if(description.length() == 0) {
            errorMessage.setValue("Description is required.");
            return;
        }
        isLoading.setValue(true);
        EducationRequest request = new EducationRequest();
        request.setField(field);
        request.setDegree(degree);
        request.setSchool(school);
        request.setStartDate(from);
        request.setEndDate(to);
        request.setDescription(description);
        educationRepository.updateEducation(id,request);
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
