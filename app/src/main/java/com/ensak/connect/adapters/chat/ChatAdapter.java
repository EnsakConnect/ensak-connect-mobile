package com.ensak.connect.adapters.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.databinding.ChatReceiverMessageBinding;
import com.ensak.connect.databinding.ChatSenderMessageBinding;
import com.ensak.connect.reponse.ChatMessageResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    ChatSenderMessageBinding senderBinding;
    ChatReceiverMessageBinding receiverBinding;
    private ArrayList<ChatMessageResponse> messages;

    final int SENDER_VIEW_HOLDER = 0;
    final int RECEIVER_VIEW_HOLDER = 1;
    final int MY_ID = 1;

    public ChatAdapter(Context context, ArrayList<ChatMessageResponse> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getSenderId() == MY_ID)
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

        if (holder.getClass() == OutgoingViewHolder.class) {
            senderBinding.tvMessage.setText("Sent Msg");
            senderBinding.tvTime.setText("02:12 pm");

//            long time = msgData.get(position).getMsgTime();
//            final Calendar cal = Calendar.getInstance();
//            cal.setTimeInMillis(time);
//            final String timeString =
//                    new SimpleDateFormat("HH:mm").format(cal.getTime());
        } else {
            receiverBinding.tvMessage.setText("Received Msg");
            receiverBinding.tvTime.setText("02:12 pm");
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
