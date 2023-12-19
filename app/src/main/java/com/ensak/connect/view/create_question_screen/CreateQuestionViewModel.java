package com.ensak.connect.view.create_question_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ensak.connect.repositories.RepositoryCallBack;
import com.ensak.connect.repositories.question_post.QuestionRepository;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostRequest;
import com.ensak.connect.repositories.question_post.remote.dto.QuestionPostResponse;

import java.util.Arrays;
import java.util.List;

public class CreateQuestionViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> isLoading = new MutableLiveData(false);
    private MutableLiveData<Boolean> isSuccess = new MutableLiveData(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");
    private QuestionRepository questionRepository;

    public CreateQuestionViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }

    public void createQuestionPost(String question, String tags) {
        if(question.length() == 0) {
            errorMessage.setValue("Question is required.");
            return;
        }
        isLoading.setValue(true);
        String[] tagsList = (String[]) Arrays.stream(tags.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        QuestionPostRequest request = new QuestionPostRequest();
        request.setQuestion(question);
        request.setTags(Arrays.asList(tagsList));
        questionRepository.create(request, new RepositoryCallBack<QuestionPostResponse>() {
            @Override
            public void onSuccess(QuestionPostResponse data) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
                successMessage.setValue("Question created successfully");
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Cannot create question. Please try again later.");
            }
        });
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
