package com.ensak.connect.presentation.job_post.create;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.job_post.JobPostRepository;
import com.ensak.connect.repository.job_post.model.JobPostRequest;
import com.ensak.connect.repository.job_post.model.JobPostResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.Arrays;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class JobPostCreateViewModel extends ViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private JobPostRepository jobPostRepository;

    @Inject
    public JobPostCreateViewModel(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public void createJobPost(String jobTitle,String description,String companyName,String location,String companyType,String category, String tags) {
        if(jobTitle.length() == 0) {
            errorMessage.setValue("jobTitle is required.");
            return;
        }
        isLoading.setValue(true);
        String[] tagsList = (String[]) Arrays.stream(tags.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        JobPostRequest request=new JobPostRequest();
        request.setTitle(jobTitle);
        request.setDescription(description);
        request.setCompanyName(companyName);
        request.setLocation(location);
        request.setCompanyType(companyType);
        request.setCategory(category);
        request.setTags(Arrays.asList(tagsList));
        jobPostRepository.create(request, new RepositoryCallBack<JobPostResponse>() {
            @Override
            public void onSuccess(JobPostResponse data) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
                successMessage.setValue("job post created successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Cannot create job post. Please try again later.");
            }
        });


    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }


}
