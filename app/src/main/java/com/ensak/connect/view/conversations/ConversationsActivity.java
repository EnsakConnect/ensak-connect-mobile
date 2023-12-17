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
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.adapters.comments.CommentsAdapter;
import com.ensak.connect.adapters.conversations.ConversationsAdapter;
import com.ensak.connect.databinding.ActivityCommentsBinding;
import com.ensak.connect.databinding.ActivityConversationsBinding;
import com.ensak.connect.reponse.CommentResponse;
import com.ensak.connect.reponse.ConversationResponse;
import com.ensak.connect.view_model.CommentViewModel;

import java.util.ArrayList;

public class ConversationsActivity extends AppCompatActivity {

    private ActivityConversationsBinding binding;
    private CommentViewModel conversationViewModel;

    private ArrayList<ConversationResponse> conversations;
    private ConversationsAdapter adapter;
    private RecyclerView rvConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        binding = ActivityConversationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        initViews();
        loadConversations(this);
    }

    private void initViews() {
        conversations = new ArrayList<>();

        rvConversations = binding.rvConversations;
        adapter = new ConversationsAdapter(conversations);
        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadConversations(Context context) {
        conversationViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
//        try {
//            conversationViewModel.getConversations().observe((LifecycleOwner) context, responses -> {
//                if (responses != null) {
//
//                    String message = responses.get(0).getUser().getFirstname();
//                    Log.d("Main Log", message);
//
//                    conversations.clear();
//                    conversations.addAll(responses);
//                    adapter.notifyDataSetChanged();
//                }
//            });
//        } catch (Throwable ex) {
//            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
//        }
    }
}