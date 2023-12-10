package com.ensak.connect.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.repositories.CommentRepository;

import java.util.ArrayList;

public class CommentViewModel extends AndroidViewModel {

    private LiveData<ArrayList<CommentResponse>> comments;
    private CommentRepository repository;

    public CommentViewModel(@NonNull Application application) {
        super(application);
        repository = new CommentRepository(application);
    }

    public LiveData<ArrayList<CommentResponse>> getPosts(String postId) {
        comments = repository.getComments(postId);
        return comments;
    }
}