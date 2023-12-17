package com.ensak.connect.view.chat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ensak.connect.R;
import com.ensak.connect.adapters.chat.ChatAdapter;
import com.ensak.connect.adapters.conversations.ConversationsAdapter;
import com.ensak.connect.databinding.ActivityChatBinding;
import com.ensak.connect.reponse.ChatMessageResponse;
import com.ensak.connect.reponse.ConversationResponse;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
//    private ConversationsViewModel conversationViewModel;

    private ArrayList<ChatMessageResponse> messages;
    private ChatAdapter adapter;
    private RecyclerView rvConversations;
    String conversationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        conversationId = "1";
        if (extras != null) {
            conversationId = extras.getString("conversation_id");
        }

        initViews();
    }

    private void initViews() {
        messages = new ArrayList<>();
        messages.add(new ChatMessageResponse(1));
        messages.add(new ChatMessageResponse(2));
        messages.add(new ChatMessageResponse(1));
        messages.add(new ChatMessageResponse(1));
        messages.add(new ChatMessageResponse(1));
        messages.add(new ChatMessageResponse(2));

        adapter = new ChatAdapter(this, messages);
        rvConversations = binding.rvChatMessages;
        rvConversations.setAdapter(adapter);
        rvConversations.setLayoutManager(new LinearLayoutManager(this));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.cardSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.etMessage.getText().toString();
                if (!message.isEmpty()) {
                    messages.add(new ChatMessageResponse(1));
                    adapter.notifyDataSetChanged();
                    rvConversations.smoothScrollToPosition(messages.size() - 1);
                    binding.etMessage.setText("");
                }
            }
        });
    }
}