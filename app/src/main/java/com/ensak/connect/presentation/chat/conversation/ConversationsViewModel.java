package com.ensak.connect.presentation.chat.conversation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.repository.chat.ConversationsRepository;

import java.util.ArrayList;

public class ConversationsViewModel extends AndroidViewModel {

    private LiveData<ArrayList<ConversationResponse>> conversations;
    private LiveData<ConversationResponse> addConversation;
    private ConversationsRepository repository;

    public ConversationsViewModel(@NonNull Application application) {
        super(application);
        repository = new ConversationsRepository(application);
    }

    public LiveData<ArrayList<ConversationResponse>> getConversations() {
        conversations = repository.getConversations();
        return conversations;
    }

    public LiveData<ConversationResponse> addConversation() {
        addConversation = repository.addConversation();
        return addConversation;
    }
}
