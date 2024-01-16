package com.ensak.connect.presentation.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.feed.FeedRepository;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserPostViewModel extends ViewModel {

    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private MutableLiveData<Boolean> isSuccess = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> isError = new MutableLiveData<>(false);

    private MutableLiveData<FeedResponse> feed = new MutableLiveData<>();

    private FeedRepository repository;

    @Inject
    public UserPostViewModel(FeedRepository repository){
        this.repository = repository;
    }

    public void getUserPosts(){
        repository.getUserPosts( new RepositoryCallBack<FeedResponse>() {
            @Override
            public void onSuccess(FeedResponse data) {
                feed.setValue(data);
                isSuccess.setValue(true);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load feed");
                isError.setValue(true);
                Log.d("UserPostViewModel", "Error: " + throwable.getMessage());
            }
        });
    }

    public LiveData<FeedResponse> getFeed() {
        return feed;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<Boolean> getIsError() {
        return isError;
    }
}
