package com.ensak.connect.presentation.question_post.show;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.question_post.QuestionRepository;
import com.ensak.connect.repository.question_post.model.QuestionPostResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowQuestionPostViewModel extends ViewModel {
    private QuestionRepository questionRepository;
    private Integer questionPostId;
    private MutableLiveData<QuestionPostResponse> question = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");

    @Inject
    public ShowQuestionPostViewModel(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void setQuestionPostId(Integer questionPostId) {
        this.questionPostId = questionPostId;
    }

    public void fetchQuestionPost() {
        isLoading.setValue(true);
        questionRepository.get(questionPostId, new RepositoryCallBack<QuestionPostResponse>() {
            @Override
            public void onSuccess(QuestionPostResponse data) {
                question.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error loading question");
            }
        });
    }

    public LiveData<QuestionPostResponse> getQuestion() {
        return question;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }
}
