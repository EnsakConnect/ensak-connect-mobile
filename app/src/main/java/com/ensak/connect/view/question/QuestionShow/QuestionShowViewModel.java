package com.ensak.connect.view.question.QuestionShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ensak.connect.repositories.question_post.QuestionRepository;

public class QuestionShowViewModel extends AndroidViewModel {

    QuestionRepository questionRepository;

    public QuestionShowViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }
}
