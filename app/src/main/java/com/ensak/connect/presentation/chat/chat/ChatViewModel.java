package com.ensak.connect.presentation.chat.chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.ensak.connect.repository.chat.ChatRepository;

import java.util.ArrayList;

public class ChatViewModel extends AndroidViewModel {

    private LiveData<ArrayList<ChatMessageResponse>> messages;
    private LiveData<ChatMessageResponse> addMessage;
    private ChatRepository repository;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        repository = new ChatRepository(application);
    }

    public LiveData<ArrayList<ChatMessageResponse>> getChatMessages(String conversationId) {
        messages = repository.getChatMessages(conversationId);
        return messages;
    }

    public LiveData<ChatMessageResponse> sendChatMessage(String conversationId, String message) {
        addMessage = repository.sendChatMessage(conversationId, message);
        return addMessage;
    }
}
