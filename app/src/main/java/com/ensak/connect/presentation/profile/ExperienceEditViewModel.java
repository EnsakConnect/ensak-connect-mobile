package com.ensak.connect.presentation.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.model.ExperienceRequest;
import com.ensak.connect.repository.profile.ExperienceRepository;
import com.ensak.connect.repository.profile.model.ExperienceResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExperienceEditViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private ExperienceRepository experienceRepository;

    @Inject
    public ExperienceEditViewModel(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
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
        ExperienceRequest request = new ExperienceRequest();
        request.setPositionTitle(title);
        request.setCompanyName(company);
        request.setLocation(location);
        request.setStartDate(from);
        request.setEndDate(to);
        request.setDescription(description);
        request.setContractType(contractType);
        isLoading.setValue(true);
        experienceRepository.addExperience(request, new RepositoryCallBack<ExperienceResponse>() {
            @Override
            public void onSuccess(ExperienceResponse data) {
                // TODO: Add this item to the list of experiences
                successMessage.setValue("Experience created successfully");
                errorMessage.setValue("");
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                successMessage.setValue("");
                errorMessage.setValue("Error creating the experience item");
                isLoading.setValue(false);
            }
        });
    }

    public void updateExperience(String id,String title, String company, String contractType,String location, String from, String to, String description) {
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
        ExperienceRequest request = new ExperienceRequest();
        request.setPositionTitle(title);
        request.setCompanyName(company);
        request.setLocation(location);
        request.setStartDate(from);
        request.setEndDate(to);
        request.setDescription(description);
        request.setContractType(contractType);
        isLoading.setValue(true);
        experienceRepository.updateExperience(id, request, new RepositoryCallBack<ExperienceResponse>() {
            @Override
            public void onSuccess(ExperienceResponse data) {
                // TODO: update the item changed, or refresh data
                isLoading.setValue(false);
                successMessage.setValue("Experience updated successfully");
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                successMessage.setValue("");
                errorMessage.setValue("Error updating Experience");
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
