package com.ensak.connect.view_model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.repositories.NameRepository;
import com.ensak.connect.reponse.NameResponse;

public class NameViewModel extends AndroidViewModel {

    private final LiveData<NameResponse> testResponseLiveData;


    public NameViewModel(@NonNull Application application) {
        super(application);

        NameRepository testRepository = new NameRepository(application);
        this.testResponseLiveData = testRepository.getTestMessage();
    }

    public LiveData<NameResponse> getTestResponseLiveData() {
        return testResponseLiveData;
    }
}
