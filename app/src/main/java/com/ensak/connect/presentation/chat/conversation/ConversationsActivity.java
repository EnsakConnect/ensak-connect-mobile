package com.ensak.connect.presentation.chat.conversation;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.adapters.conversations.ConversationsAdapter;
import com.ensak.connect.databinding.ChatConversationsActivityBinding;
import com.ensak.connect.repository.chat.model.ConversationResponse;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConversationsActivity extends AppCompatActivity {

    private ChatConversationsActivityBinding binding;
    private ConversationsViewModel conversationViewModel;

    private ArrayList<ConversationResponse> conversations;
    private ConversationsAdapter adapter;
    private RecyclerView rvConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatConversationsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> finish());

        initViews();
        initViewModel();
        conversationViewModel.fetchConversations();
    }

    private void initViews() {
        conversations = new ArrayList<>();

        rvConversations = binding.rvConversations;
        adapter = new ConversationsAdapter(conversations);
        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));

        binding.fabNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewConversation();
            }
        });
    }

    private void initViewModel() {
        conversationViewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);
        conversationViewModel.getConversations().observe(this, conversationResponses -> {
            conversations.clear();
            conversations.addAll(conversationResponses);
            adapter.notifyDataSetChanged();
        });

        conversationViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show loading state
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });

        conversationViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        });
    }

    private void createNewConversation() {
        // TODO: implement the add conversation with the backend endpoint
        conversationViewModel.addConversation();
    }
}