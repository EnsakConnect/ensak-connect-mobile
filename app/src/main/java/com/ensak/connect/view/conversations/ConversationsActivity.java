package com.ensak.connect.view.conversations;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ensak.connect.adapters.conversations.ConversationsAdapter;
import com.ensak.connect.databinding.ActivityConversationsBinding;
import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.view_model.conversations.ConversationsViewModel;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {

    private ActivityConversationsBinding binding;
    private ConversationsViewModel conversationViewModel;

    private ArrayList<ConversationResponse> conversations;
    private ConversationsAdapter adapter;
    private RecyclerView rvConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConversationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViews();
        loadConversations(this);
    }

    private void initViews() {
        conversations = new ArrayList<>();
        conversations.add(new ConversationResponse());
        conversations.add(new ConversationResponse());
        conversations.add(new ConversationResponse());

        rvConversations = binding.rvConversations;
        adapter = new ConversationsAdapter(conversations);
        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));

        binding.fabNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewConversation(ConversationsActivity.this);
            }
        });
    }

    private void loadConversations(Context context) {
        conversationViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        try {
            conversationViewModel.getConversations().observe((LifecycleOwner) context, responses -> {
                if (responses != null) {

                    String message = responses.get(0).getUser().getFirstname();
                    Log.d("Main Log", message);

                    conversations.clear();
                    conversations.addAll(responses);
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }
    }

    private void createNewConversation(Context context) {
        conversationViewModel = ViewModelProviders.of(this).get(ConversationsViewModel.class);
        try {
            conversationViewModel.addConversation().observe((LifecycleOwner) context, responses -> {
                if (responses != null) {

                    String message = responses.getUser().getFirstname();
                    Log.d("Main Log", message);

                    conversations.clear();
                    conversations.add(responses);
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }
    }
}