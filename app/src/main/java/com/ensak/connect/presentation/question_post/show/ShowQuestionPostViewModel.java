package com.ensak.connect.presentation.question_post.show;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.interaction.InteractionRepository;
import com.ensak.connect.repository.interaction.model.InteractionResponse;
import com.ensak.connect.repository.like.LikeRepository;
import com.ensak.connect.repository.like.model.LikeResponse;
import com.ensak.connect.repository.question_post.QuestionRepository;
import com.ensak.connect.repository.question_post.model.QuestionPostAnswerRequest;
import com.ensak.connect.repository.question_post.model.QuestionPostAnswerResponse;
import com.ensak.connect.repository.question_post.model.QuestionPostResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShowQuestionPostViewModel extends ViewModel {
    private QuestionRepository questionRepository;
    private final LikeRepository likeRepository;
    private final InteractionRepository interactionRepository;
    private Integer questionPostId;
    private MutableLiveData<QuestionPostResponse> question = new MutableLiveData<>();
    private MutableLiveData<List<QuestionPostAnswerResponse>> answers = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<String> successMessage = new MutableLiveData<>("");

    @Inject
    public ShowQuestionPostViewModel(QuestionRepository questionRepository, LikeRepository likeRepository, InteractionRepository interactionRepository) {
        this.questionRepository = questionRepository;
        this.likeRepository = likeRepository;
        this.interactionRepository = interactionRepository;
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

    public void fetchAllAnswers() {
        isLoading.setValue(true);
        questionRepository.getAnswer(questionPostId, new RepositoryCallBack<List<QuestionPostAnswerResponse>>() {
            @Override
            public void onSuccess(List<QuestionPostAnswerResponse> data) {
                isLoading.setValue(false);
                answers.setValue(data);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error loading answers");
            }
        });
    }

    public void createAnswer(String answer) {
        if(answer.isEmpty()){
            errorMessage.setValue("Answer cannot be empty");
            return;
        }
        QuestionPostAnswerRequest request = new QuestionPostAnswerRequest();
        request.setContent(answer);
        isLoading.setValue(true);
        questionRepository.createAnswer(questionPostId, request, new RepositoryCallBack<QuestionPostAnswerResponse>() {
            @Override
            public void onSuccess(QuestionPostAnswerResponse data) {
                isLoading.setValue(false);
                successMessage.setValue("Answer added successfully");
                fetchAllAnswers();
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error adding your answer");
            }
        });
    }

    public void interactAnswerUp(Integer answerId) {
        isLoading.setValue(true);
        interactionRepository.interactAnswerUp(answerId, new RepositoryCallBack<InteractionResponse>() {
            @Override
            public void onSuccess(InteractionResponse data) {
                fetchAllAnswers();
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error up answer");
            }
        });
    }

    public void interactAnswerDown(Integer answerId) {
        isLoading.setValue(true);
        interactionRepository.interactAnswerDown(answerId, new RepositoryCallBack<InteractionResponse>() {
            @Override
            public void onSuccess(InteractionResponse data) {
                fetchAllAnswers();
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                isLoading.setValue(false);
                errorMessage.setValue("Error up answer");
            }
        });
    }

    public void likeDislikeQuestionPost() {
        isLoading.setValue(true);
        if (this.question.getValue().getLiked()) {
            likeRepository.dislikeQuestionPost(questionPostId, new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    question.getValue().setLiked(false);
                    question.getValue().setLikesCount(question.getValue().getLikesCount()-1);
                    question.setValue(question.getValue());
                    isLoading.setValue(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error disliking question");
                }
            });
        }else {
            likeRepository.likeQuestionPost(questionPostId, new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    question.getValue().setLiked(true);
                    question.getValue().setLikesCount(question.getValue().getLikesCount()+1);
                    question.setValue(question.getValue());
                    isLoading.setValue(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error liking question");
                }
            });
        }

    }

    public LiveData<QuestionPostResponse> getQuestion() {
        return question;
    }

    public LiveData<List<QuestionPostAnswerResponse>> getAnswers() {
        return answers;
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
