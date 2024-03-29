package com.ensak.connect.presentation.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.EducationRepository;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EducationEditViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private EducationRepository educationRepository;

    @Inject
    public EducationEditViewModel(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
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
        educationRepository.addEducation(request, new RepositoryCallBack<EducationResponse>() {
            @Override
            public void onSuccess(EducationResponse data) {
                // TODO: add Education item to
                isLoading.setValue(false);
                successMessage.setValue("Education added successfully");
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                successMessage.setValue("");
                errorMessage.setValue("Error creating education item");
            }
        });
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
        educationRepository.updateEducation(id, request, new RepositoryCallBack<EducationResponse>() {
            @Override
            public void onSuccess(EducationResponse data) {
                isLoading.setValue(false);
                // TODO: update the item for the view
                successMessage.setValue("Education updated successfully");
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                successMessage.setValue("");
                errorMessage.setValue("Error updating education item");
            }
        });
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
