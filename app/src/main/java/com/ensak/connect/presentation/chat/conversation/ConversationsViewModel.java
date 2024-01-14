package com.ensak.connect.presentation.chat.conversation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensak.connect.repository.chat.model.ConversationRequest;
import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.repository.chat.ConversationsRepository;
import com.ensak.connect.repository.shared.RepositoryCallBack;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ConversationsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ConversationResponse>> conversations = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);
    private ConversationsRepository repository;

    @Inject
    public ConversationsViewModel(ConversationsRepository conversationsRepository) {
        this.repository = conversationsRepository;
    }

    public void fetchConversations() {
        isLoading.setValue(true);
        repository.getConversations(new RepositoryCallBack<ArrayList<ConversationResponse>>() {
            @Override
            public void onSuccess(ArrayList<ConversationResponse> data) {
                conversations.setValue(data);
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load conversations");
                ArrayList<ConversationResponse> tmpRes = new ArrayList<>();
                tmpRes.add(new ConversationResponse());
                tmpRes.add(new ConversationResponse());
                tmpRes.add(new ConversationResponse());
                conversations.setValue(tmpRes);
                isLoading.setValue(false);
            }
        });
    }

    public void addConversation(ConversationRequest conversation) {
        isLoading.setValue(true);
        repository.addConversation(conversation, new RepositoryCallBack<ConversationResponse>() {
            @Override
            public void onSuccess(ConversationResponse data) {
                fetchConversations();
            }

            @Override
            public void onFailure(Throwable throwable) {
                errorMessage.setValue("Could not load conversations");
                isLoading.setValue(false);
            }
        });
    }

    public LiveData<ArrayList<ConversationResponse>> getConversations() {
        return conversations;
    }
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
