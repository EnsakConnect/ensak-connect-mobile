package com.ensak.connect.adapters.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.databinding.ChatReceiverMessageBinding;
import com.ensak.connect.databinding.ChatSenderMessageBinding;
import com.ensak.connect.repository.chat.model.ChatMessageResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    ChatSenderMessageBinding senderBinding;
    ChatReceiverMessageBinding receiverBinding;
    private ArrayList<ChatMessageResponse> messages;

    final int SENDER_VIEW_HOLDER = 0;
    final int RECEIVER_VIEW_HOLDER = 1;
    final int userId;

    public ChatAdapter(Context context, ArrayList<ChatMessageResponse> messages, int userId) {
        this.context = context;
        this.messages = messages;
        this.userId = userId;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId() == userId)
            return SENDER_VIEW_HOLDER;
        else
            return RECEIVER_VIEW_HOLDER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == SENDER_VIEW_HOLDER) {
            senderBinding = senderBinding.inflate(inflater, parent, false);
            return new ChatAdapter.OutgoingViewHolder(senderBinding.getRoot());
        } else {
            receiverBinding = receiverBinding.inflate(inflater, parent, false);
            return new ChatAdapter.IncomingViewHolder(receiverBinding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessageResponse message = messages.get(position);
        Context context = holder.itemView.getContext();
        String content = message.getContent();
        String date = new SimpleDateFormat("HH:mm").format(message.getDate());

        if (holder.getClass() == OutgoingViewHolder.class) {
            // INFO: sent message
            senderBinding.tvMessage.setText(content);
            senderBinding.tvTime.setText(date);
        } else {
            // INFO: Received message
            receiverBinding.tvMessage.setText(content);
            receiverBinding.tvTime.setText(date);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class OutgoingViewHolder extends RecyclerView.ViewHolder {

        public OutgoingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class IncomingViewHolder extends RecyclerView.ViewHolder {

        public IncomingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
