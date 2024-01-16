package com.ensak.connect.presentation.home;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.repository.feed.FeedRepository;
import com.ensak.connect.repository.like.LikeRepository;
import com.ensak.connect.repository.like.model.LikeResponse;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FeedViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<FeedResponse> feed = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLikeSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");

    private FeedRepository repository;
    private LikeRepository likeRepository;

    @Inject
    public FeedViewModel(FeedRepository feedRepository, LikeRepository likeRepository) {
        this.repository = feedRepository;
        this.likeRepository = likeRepository;
    }

    public void fetchFeed(Integer page, String search, String filter) {
        repository.getFeed(page, search, filter, new RepositoryCallBack<FeedResponse>() {
            @Override
            public void onSuccess(FeedResponse data) {
                feed.setValue(data);
                errorMessage.setValue("");
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load feed");
                isLoading.setValue(false);
                Log.d(TAG, "Error: " + throwable.getMessage());
            }
        });
    }

    public void likeDislikeQuestionPost(FeedContentResponse post) {
        if (post.isLiked()) {
            likeRepository.dislikeQuestionPost(post.getId(), new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    isLikeSuccess.setValue(false);
                    isLoading.setValue(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error disliking question");
                }
            });
        } else {
            likeRepository.likeQuestionPost(post.getId(), new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    isLoading.setValue(false);
                    isLikeSuccess.setValue(true);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error liking question");
                }
            });
        }
    }

    public void likeDislikeBlogPost(FeedContentResponse post) {
        if (post.isLiked()) {
            likeRepository.dislikeBlogPost(post.getId(), new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    isLikeSuccess.setValue(false);
                    isLoading.setValue(false);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error disliking question");
                }
            });
        } else {
            likeRepository.likeBlogPost(post.getId(), new RepositoryCallBack<LikeResponse>() {
                @Override
                public void onSuccess(LikeResponse data) {
                    isLoading.setValue(false);
                    isLikeSuccess.setValue(true);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    isLoading.setValue(false);
                    errorMessage.setValue("Error liking question");
                }
            });
        }
    }

    public LiveData<Boolean> getLikeStatus() {
        return isLikeSuccess;
    }

    public void fetchProfiles(Integer page, String fullname) {
        repository.getProfiles(page, fullname, new RepositoryCallBack<FeedResponse>() {
            @Override
            public void onSuccess(FeedResponse data) {
                feed.setValue(data);
                errorMessage.setValue("");
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load feed");
                isLoading.setValue(false);
                Log.d(TAG, "Error: " + throwable.getMessage());
            }
        });
    }

    public LiveData<FeedResponse> getFeed() {
        return feed;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}