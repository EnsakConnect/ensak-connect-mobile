package com.ensak.connect.presentation.job_post.comments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.repository.job_post.JobPostCommentRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CommentViewModel extends ViewModel {

    private MutableLiveData<ArrayList<JobPostCommentResponse>> comments = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private JobPostCommentRepository repository;

    @Inject
    public CommentViewModel(JobPostCommentRepository jobPostCommentRepository) {
        repository = jobPostCommentRepository;
    }

    public void fetchComments(String postId) {
        isLoading.setValue(true);
        repository.getComments(postId, new RepositoryCallBack<ArrayList<JobPostCommentResponse>>() {
            @Override
            public void onSuccess(ArrayList<JobPostCommentResponse> data) {
                comments.setValue(data);
                isLoading.setValue(false);
                errorMessage.setValue(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Error loading comments");
                isLoading.setValue(false);
            }
        });
    }

    public void sendComment(String postId, String comment) {
        isLoading.setValue(true);
        repository.sendComment(postId, comment, new RepositoryCallBack<JobPostCommentResponse>() {
            @Override
            public void onSuccess(JobPostCommentResponse data) {
                ArrayList<JobPostCommentResponse> commentsValue = comments.getValue();
                commentsValue.add(0, data);
                errorMessage.setValue(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error posting a comment");
            }
        });
    }

    public LiveData<ArrayList<JobPostCommentResponse>> getComments() {
        return comments;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    };
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}