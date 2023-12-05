package com.ensak.connect.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.reponse.PostResponse;
import com.ensak.connect.repositories.HomeRepository;
import com.ensak.connect.repositories.NameRepository;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {

    private final LiveData<ArrayList<PostResponse>> posts;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        HomeRepository repository = new HomeRepository(application);
        this.posts = repository.getPosts();
    }

    public LiveData<ArrayList<PostResponse>> getPosts() {
        return posts;
    }
}