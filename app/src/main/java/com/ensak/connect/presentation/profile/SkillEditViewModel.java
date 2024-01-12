package com.ensak.connect.presentation.profile;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.profile.EducationRepository;
import com.ensak.connect.repository.profile.SkillRepository;
import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.model.SkillRequest;
import com.ensak.connect.repository.profile.model.SkillResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SkillEditViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private SkillRepository skillRepository;

    @Inject
    public SkillEditViewModel(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public void createSkill(String skill, String level) {
        if(skill.length() == 0) {
            errorMessage.setValue("Field is required.");
            return;
        }


        isLoading.setValue(true);
        SkillRequest request = new SkillRequest();
        request.setName(skill);
        request.setLevel(level);

        skillRepository.addSkill(request, new RepositoryCallBack<SkillResponse>() {
            @Override
            public void onSuccess(SkillResponse data) {
                // TODO: add Education item to
                isLoading.setValue(false);
                successMessage.setValue("skill added successfully");
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
