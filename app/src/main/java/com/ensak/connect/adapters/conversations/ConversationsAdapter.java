package com.ensak.connect.adapters.conversations;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.databinding.ConversationItemBinding;
import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.view.chat.ChatActivity;

import java.util.ArrayList;

public class ConversationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ConversationItemBinding binding;
    private ArrayList<ConversationResponse> conversations;

    public ConversationsAdapter(ArrayList<ConversationResponse> conversations) {
        this.conversations = conversations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = binding.inflate(inflater, parent, false);
        ConversationsAdapter.ViewHolder viewHolder = new ConversationsAdapter.ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationResponse conversation = conversations.get(position);
        Context context = holder.itemView.getContext();

//        binding.tvUserName.setText(conversation.getUser().getFirstname() + " " + conversation.getUser().getLastname());
        binding.tvUserName.setText("Mohamed Amine");
        binding.tvLastMessage.setText("Full Stack Developer - SQLI");
        binding.tvLastDiscussionDate.setText("2j");
//        binding.tvLastDiscussionDate.setText(Utils.calculateTimeAgo(conversation.getDate()));

        Glide.with(context)
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivUserImage);

        binding.llConversationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("conversation_id", "1");
                intent.putExtra("receiver_name", "Mohamed Amine");
                intent.putExtra("receiver_image", "https://www.w3schools.com/w3images/avatar2.png");
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
