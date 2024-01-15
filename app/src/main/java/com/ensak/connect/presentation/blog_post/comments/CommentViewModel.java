package com.ensak.connect.presentation.blog_post.comments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.blog_post.model.BlogPostCommentResponse;
import com.ensak.connect.repository.blog_post.BlogPostCommentRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CommentViewModel extends ViewModel {

    private MutableLiveData<ArrayList<BlogPostCommentResponse>> comments = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private BlogPostCommentRepository repository;

    @Inject
    public CommentViewModel(BlogPostCommentRepository blogPostCommentRepository) {
        repository = blogPostCommentRepository;
    }

    public void fetchComments(String postId) {
        isLoading.setValue(true);
        repository.getComments(postId, new RepositoryCallBack<ArrayList<BlogPostCommentResponse>>() {
            @Override
            public void onSuccess(ArrayList<BlogPostCommentResponse> data) {
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
        repository.sendComment(postId, comment, new RepositoryCallBack<BlogPostCommentResponse>() {
            @Override
            public void onSuccess(BlogPostCommentResponse data) {
                comments.getValue().add(0,data);
                errorMessage.setValue(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error posting a comment");
            }
        });
    }

    public LiveData<ArrayList<BlogPostCommentResponse>> getComments() {
        return comments;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    };
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}