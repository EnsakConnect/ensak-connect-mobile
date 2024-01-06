package com.ensak.connect.presentation.job_post.comments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.repository.job_post.JobPostCommentRepository;

import java.util.ArrayList;

public class CommentViewModel extends AndroidViewModel {

    private LiveData<ArrayList<JobPostCommentResponse>> comments;
    private LiveData<JobPostCommentResponse> sendCommentResponse;
    private JobPostCommentRepository repository;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repository = new JobPostCommentRepository(application);
    }

    public LiveData<ArrayList<JobPostCommentResponse>> getComments(String postId) {
        comments = repository.getComments(postId);
        return comments;
    }

    public LiveData<JobPostCommentResponse> sendComment(String postId, String comment) {
        sendCommentResponse = repository.sendComment(postId, comment);
        return sendCommentResponse;
    }
}