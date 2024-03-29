package com.ensak.connect.presentation.job_post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.ensak.connect.repository.job_post.JobPostRepository;
import com.ensak.connect.repository.job_post.model.JobPostApplicationResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class JobPostViewModel extends ViewModel {
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> error = new MutableLiveData<>(false);

    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private MutableLiveData<List<JobPostApplicationResponse>> applications= new MutableLiveData<>();

    private JobPostRepository repository;

    @Inject
    public JobPostViewModel(JobPostRepository jobPostRepository) {
        repository = jobPostRepository;
    }

    public void applyToJob(int jobId){
        repository.apply(jobId,new RepositoryCallBack<JobPostApplicationResponse>(){
            @Override
            public void onSuccess(JobPostApplicationResponse data) {
                isSuccess.setValue(true);
            }
            @Override
            public void onFailure(Throwable throwable) {
                error.setValue(true);
                errorMessage.setValue("Error Applying for Job");
            }
        });
    }

    public  void getJobApplications(int jobId){
        repository.getApplications(jobId, new  RepositoryCallBack<List<JobPostApplicationResponse>>(){
            @Override
            public void onSuccess(List<JobPostApplicationResponse> data) {
                applications.setValue(data);
            }
            @Override
            public void onFailure(Throwable throwable) {
                error.setValue(true);
                errorMessage.setValue("Error getting job applications");
            }
        });
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public void setError(MutableLiveData<Boolean> error) {
        this.error = error;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(MutableLiveData<Boolean> isSuccess) {
        this.isSuccess = isSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LiveData<List<JobPostApplicationResponse>> getApplications() {
        return applications;
    }
}
