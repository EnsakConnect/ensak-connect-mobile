package com.ensak.connect.presentation.profile;

import android.telecom.Call;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.model.Skill;
import com.ensak.connect.repository.profile.EducationRepository;
import com.ensak.connect.repository.profile.SkillRepository;
import com.ensak.connect.repository.profile.model.EducationRequest;
import com.ensak.connect.repository.profile.model.EducationResponse;
import com.ensak.connect.repository.profile.model.ProfileResponse;
import com.ensak.connect.repository.profile.model.SkillRequest;
import com.ensak.connect.repository.profile.model.SkillResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.Callback;
import okhttp3.Response;

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

    private MutableLiveData<List<Skill>> skills = new MutableLiveData<>();

    public LiveData<List<Skill>> getSkills() {
        return skills;
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

    public void fetchSkills() {
        isLoading.setValue(true);
        skillRepository.getSkills(new RepositoryCallBack<List<Skill>>() {
            @Override
            public void onSuccess(List<Skill> data) {
                skills.setValue(data); // This will be a List<Skill>
                isLoading.setValue(false);
                errorMessage.setValue("");
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Error fetching skills: " + throwable.getMessage());
                isLoading.setValue(false);
            }
        });
    }

    public void deleteSkill(int skillId) {
        isLoading.setValue(true);
        skillRepository.deleteSkill(skillId, new RepositoryCallBack<Void>() {
            @Override
            public void onSuccess(Void result) {
                removeSkillFromList(skillId);
                isLoading.setValue(false);
                successMessage.setValue("Skill deleted successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error deleting skill: " + throwable.getMessage());
            }
        });
    }
    private void removeSkillFromList(int skillid) {
        List<Skill> currentSkills = skills.getValue();
        if (currentSkills != null) {
            currentSkills.removeIf(skill -> skill.getid() == skillid);
            skills.setValue(currentSkills);
        }
    }

}

