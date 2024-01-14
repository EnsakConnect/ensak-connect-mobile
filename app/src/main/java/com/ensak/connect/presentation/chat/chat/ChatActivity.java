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
    String conversationId, receiverName, receiverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        conversationId = "1";
        if (extras != null) {
            conversationId = extras.getString("conversation_id");
            receiverName = extras.getString("receiver_name");
            receiverImage = extras.getString("receiver_image");
        }

        initViews();
//        initViewModel();
//        chatViewModel.fetchChatMessages(conversationId);

        OneSignal.setNotificationWillShowInForegroundHandler(new OneSignal.OSNotificationWillShowInForegroundHandler() {
            @Override
            public void notificationWillShowInForeground(OSNotificationReceivedEvent osNotificationReceivedEvent) {
                Log.v("Notiffffffff", "notificationWillShowInForeground fired with event: " + osNotificationReceivedEvent);

                OSNotification notification = osNotificationReceivedEvent.getNotification();
                JSONObject data = notification.getAdditionalData();
                Log.v("Notiffffffff", "title->: " + notification.getTitle());

                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addMessage(new ChatMessageResponse(
                                    0,
                                    0,
                                    0,
                                    notification.getBody(),
                                    new Date()
                            ));
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
        adapter = new ChatAdapter(this, messages);
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
            // TODO: set is loading state
        });

        chatViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        chatViewModel.getMessage().observe(this, responseMessage -> {
            messages.clear();
            messages.addAll(responseMessage);
            adapter.notifyDataSetChanged();
            binding.rvChatMessages.smoothScrollToPosition(messages.size() - 1);
//            adapter.notifyDataSetChanged();
        });
    }

    private void sendChatMessage() {
        String message = binding.etMessage.getText().toString();
        // TODO: add validation
        if (!message.isEmpty()) {
            chatViewModel.sendMessage(conversationId, message);
        }

    }

    public void addMessage(ChatMessageResponse message) {
        messages.add(message);
        adapter.notifyDataSetChanged();
        binding.rvChatMessages.smoothScrollToPosition(messages.size() - 1);
    }
}