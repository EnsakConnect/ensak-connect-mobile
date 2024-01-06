package com.ensak.connect.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.feed.FeedRepository;

public class HomeViewModel extends AndroidViewModel {

    private LiveData<FeedResponse> feed;
    private FeedRepository repository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new FeedRepository(application);

    }

    public LiveData<FeedResponse> getFeed(int page, String search, String filter) {
        feed = repository.getFeed(page, search, filter);
        return feed;
    }
}