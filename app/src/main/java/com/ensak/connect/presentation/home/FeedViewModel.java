package com.ensak.connect.presentation.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.feed.FeedRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FeedViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<FeedResponse> feed = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private FeedRepository repository;

    @Inject
    public FeedViewModel(FeedRepository feedRepository) {
        this.repository = feedRepository;
    }

    public void fetchFeed(Integer page, String search, String filter) {
        repository.getFeed(page, search, filter, new RepositoryCallBack<FeedResponse>() {
            @Override
            public void onSuccess(FeedResponse data) {
                feed.setValue(data);
                errorMessage.setValue("");
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load feed");
                isLoading.setValue(false);
                Log.d(TAG, "Error: " + throwable.getMessage());
            }
        });
    }

    public LiveData<FeedResponse> getFeed() {
        return feed;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}