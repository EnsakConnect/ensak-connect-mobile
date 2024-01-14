package com.ensak.connect.presentation.chat.chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.chat.model.ChatMessageRequest;
import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.chat.ChatRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();
    private MutableLiveData<ArrayList<ChatMessageResponse>> messages = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private ChatRepository repository;

    @Inject
    public ChatViewModel(ChatRepository chatRepository) {
        this.repository = chatRepository;
    }

    public void fetchChatMessages(int conversationId) {
        isLoading.setValue(true);
        repository.fetchChatMessage(conversationId, new RepositoryCallBack<ArrayList<ChatMessageResponse>>() {
            @Override
            public void onSuccess(ArrayList<ChatMessageResponse> data) {
                messages.setValue(data);
                isLoading.setValue(false);
                Log.d(TAG, String.valueOf(data));
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Error loading messages!");
                isLoading.setValue(false);
            }
        });
    }

    public void sendMessage(ChatMessageRequest chatMessage) {
        isLoading.setValue(true);
        repository.sendChatMessage(chatMessage, new RepositoryCallBack() {
            @Override
            public void onSuccess(Object data) {
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Error sending message, try again");
                isLoading.setValue(false);
            }
        });
    }

    public LiveData<ArrayList<ChatMessageResponse>> getMessage() {
        return messages;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
