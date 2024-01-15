package com.ensak.connect.presentation.chat.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
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
import com.ensak.connect.databinding.ChatActivityBinding;
import com.ensak.connect.repository.chat.model.ChatMessageRequest;
import com.ensak.connect.repository.chat.model.ChatMessageResponse;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    private ChatActivityBinding binding;
    private ChatViewModel chatViewModel;

    private ArrayList<ChatMessageResponse> messages = new ArrayList<>();
    private ChatAdapter adapter;
    int conversationId, userId;
    String receiverName, receiverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            conversationId = extras.getInt("conversation_id");
            userId = extras.getInt("user_id");
            receiverName = extras.getString("receiver_name");
            receiverImage = extras.getString("receiver_image");
        }

        initViews();
        initViewModel();
        chatViewModel.fetchChatMessages(conversationId);

        OneSignal.setNotificationWillShowInForegroundHandler(new OneSignal.OSNotificationWillShowInForegroundHandler() {
            @Override
            public void notificationWillShowInForeground(OSNotificationReceivedEvent osNotificationReceivedEvent) {
                Log.v("Notiffffffff", "notificationWillShowInForeground fired with event: " + osNotificationReceivedEvent);

                OSNotification notification = osNotificationReceivedEvent.getNotification();
                JSONObject data = notification.getAdditionalData();
                int notifConversationId = data.optInt("conversationId");
                int notifSenderId = data.optInt("senderId");
                Log.v("Notiffffffff", "title->: " + notification.getTitle());

                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (notifConversationId != conversationId) {
                                return;
                            }
                            if (notifSenderId == userId) {
                                osNotificationReceivedEvent.complete(null);
                                return;
                            }

                            addMessage(new ChatMessageResponse(
                                    0,
                                    notifConversationId,
                                    notifSenderId,
                                    notification.getBody(),
                                    new Date()
                            ));
                            osNotificationReceivedEvent.complete(null);
                            Log.v("Notiffffffff", "message added: " + notification.getBody());
                        }
                    });
                } catch (Exception e) {
                    Log.v("Notiffffffff", "error->: " + e.getMessage());
                }
            }
        });
    }

    private void initViews() {
        adapter = new ChatAdapter(this, messages, userId);
        binding.rvChatMessages.setAdapter(adapter);
        binding.rvChatMessages.setLayoutManager(new LinearLayoutManager(this));

        binding.tvReceiverName.setText(receiverName);
        Glide.with(this)
                .load(receiverImage)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivReceiverImage);


        binding.btnBack.setOnClickListener(view -> finish());

        binding.cardSendMessage.setOnClickListener(view -> {
            sendChatMessage();
        });
    }

    private void initViewModel() {
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        chatViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading)
                binding.loadingProgressBar.setVisibility(View.VISIBLE);
            else
                binding.loadingProgressBar.setVisibility(View.GONE);
        });

        chatViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
        });

        chatViewModel.getMessage().observe(this, responseMessage -> {
            messages.clear();
            messages.addAll(responseMessage);
            adapter.notifyDataSetChanged();
            if (messages.size() > 0)
                binding.rvChatMessages.smoothScrollToPosition(messages.size() - 1);
        });
    }

    private void sendChatMessage() {
        String message = binding.etMessage.getText().toString();
        if (!message.isEmpty()) {
            chatViewModel.sendMessage(new ChatMessageRequest(conversationId, userId, message));
            binding.etMessage.setText("");
            addMessage(new ChatMessageResponse(
                    0,
                    conversationId,
                    userId,
                    message,
                    new Date()
            ));
        }

    }

    public void addMessage(ChatMessageResponse message) {
        messages.add(message);
        adapter.notifyDataSetChanged();
        if (messages.size() > 0)
            binding.rvChatMessages.smoothScrollToPosition(messages.size() - 1);
    }
}