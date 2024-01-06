package com.ensak.connect.presentation.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.feed.FeedRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private LiveData<FeedResponse> feed;
    private FeedRepository repository;

    @Inject
    public HomeViewModel(FeedRepository feedRepository) {
        this.repository = feedRepository;
    }

    public LiveData<FeedResponse> getFeed(int page, String search, String filter) {
        feed = repository.getFeed(page, search, filter);
        return feed;
    }
}