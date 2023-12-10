package com.ensak.connect.view.create_question_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CreateQuestionViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);

    public CreateQuestionViewModel(@NonNull Application application) {
        super(application);
    }
}
