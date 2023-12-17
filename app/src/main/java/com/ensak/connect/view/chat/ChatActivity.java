package com.ensak.connect.view.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.adapters.chat.ChatAdapter;
import com.ensak.connect.databinding.ActivityChatBinding;
import com.ensak.connect.reponse.ChatMessageResponse;
import com.ensak.connect.view_model.chat.ChatViewModel;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ActivityChatBinding binding;
    private ChatViewModel chatViewModel;

    private ArrayList<ChatMessageResponse> messages;
    private ChatAdapter adapter;
    private RecyclerView rvConversations;
    String conversationId, receiverName, receiverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        conversationId = "1";
        if (extras != null) {
            conversationId = extras.getString("conversation_id");
            receiverName = extras.getString("receiver_name");
            receiverImage = extras.getString("receiver_image");
        }

        initViews();
        getChatMessages(conversationId);
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

        binding.tvReceiverName.setText(receiverName);
        Glide.with(this)
                .load(receiverImage)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivReceiverImage);


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

                    sendChatMessage(conversationId, message);
                }
            }
        });
    }

    private void getChatMessages(String conversationId) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        try {
            chatViewModel.getChatMessages(conversationId).observe((LifecycleOwner) this, responses -> {
                if (responses != null) {

                    String message = responses.get(0).getMessage();
                    Log.d("Main Log", message);

                    messages.clear();
                    messages.addAll(responses);
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(this, "Error getting chat messages.", Toast.LENGTH_LONG);
        }
    }

    private void sendChatMessage(String conversationId, String message) {
        try {
            chatViewModel.sendChatMessage(conversationId, message).observe((LifecycleOwner) this, responses -> {
                if (responses != null) {

                    String res = responses.getMessage();
                    Log.d("Main Log", res);
                }
            });
        } catch (Throwable ex) {
            Toast.makeText(this, "Error sending msg", Toast.LENGTH_LONG);
        }
    }
}