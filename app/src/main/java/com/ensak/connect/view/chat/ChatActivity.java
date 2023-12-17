package com.ensak.connect.view.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ensak.connect.R;
import com.ensak.connect.adapters.chat.ChatAdapter;
import com.ensak.connect.databinding.ActivityChatBinding;
import com.ensak.connect.reponse.ChatMessageResponse;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
//    private ConversationsViewModel conversationViewModel;

    private ArrayList<ChatMessageResponse> conversations;
    private ChatAdapter adapter;
    private RecyclerView rvConversations;
    String conversationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());

//        setContentView(binding.getRoot());
//        setSupportActionBar(binding.toolbar);
//
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        Bundle extras = getIntent().getExtras();
        conversationId = "1";
        if (extras != null) {
            conversationId = extras.getString("conversation_id");
        }

        initViews();
//        loadMessage(this);
    }

    private void initViews() {
//        conversations = new ArrayList<>();
//        conversations.add(new ConversationResponse());
//        conversations.add(new ConversationResponse());
//        conversations.add(new ConversationResponse());
//
//        rvConversations = binding.rvConversations;
//        adapter = new ConversationsAdapter(conversations);
//        rvConversations.setAdapter(adapter);
//        rvConversations.setLayoutManager(new LinearLayoutManager(this));
//
//        binding.fabNewConversation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createNewConversation(ConversationsActivity.this);
//            }
//        });
    }
}