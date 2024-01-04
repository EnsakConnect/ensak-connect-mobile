package com.ensak.connect.view.question.QuestionShow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.question_post.QuestionRepository;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostResponse;

public class QuestionShowViewModel extends AndroidViewModel {
    private final String TAG = getClass().getSimpleName();
    private QuestionRepository questionRepository;
    private MutableLiveData<Boolean> isPostLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<QuestionPostResponse> post = new MutableLiveData<>();

    public QuestionShowViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }

    public void fetchPost(Integer id){
        questionRepository.getById(id, new RepositoryCallBack<QuestionPostResponse>() {
            @Override
            public void onSuccess(QuestionPostResponse data) {
                isPostLoading.setValue(false);
                errorMessage.setValue("");
                post.setValue(data);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isPostLoading.setValue(false);
                errorMessage.setValue(throwable.getMessage());
                post.setValue(null);
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isPostLoading;
    }

    public LiveData<QuestionPostResponse> getPost(){
        return post;
    }
}
