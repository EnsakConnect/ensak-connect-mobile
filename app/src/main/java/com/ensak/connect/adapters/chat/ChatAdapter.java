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
import com.ensak.connect.databinding.ChatMessageItemBinding;
import com.ensak.connect.reponse.ChatMessageResponse;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ChatMessageItemBinding binding;
    private ArrayList<ChatMessageResponse> messages;

    public ChatAdapter(ArrayList<ChatMessageResponse> messages) {
        this.messages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = binding.inflate(inflater, parent, false);
        ChatAdapter.ViewHolder viewHolder = new ChatAdapter.ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessageResponse message = messages.get(position);
        Context context = holder.itemView.getContext();

//        binding.tvUserName.setText(conversation.getUser().getFirstname() + " " + conversation.getUser().getLastname());
//        binding.tvLastDiscussionDate.setText(Utils.calculateTimeAgo(conversation.getDate()));

//        Glide.with(context)
//                .load("https://www.w3schools.com/w3images/avatar2.png")
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
//                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
//                )
//                .into(binding.ivUserImage);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
