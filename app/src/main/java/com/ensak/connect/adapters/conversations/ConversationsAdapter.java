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
import com.ensak.connect.databinding.ChatConversationItemBinding;
import com.ensak.connect.presentation.chat.conversation.ConversationsActivity;
import com.ensak.connect.repository.chat.model.ConversationResponse;
import com.ensak.connect.presentation.chat.chat.ChatActivity;
import com.ensak.connect.repository.profile.model.ProfileDetailedResponse;
import com.ensak.connect.service.SessionManagerService;
import com.ensak.connect.utils.DateUtil;

import java.util.ArrayList;

public class ConversationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ChatConversationItemBinding binding;
    private ArrayList<ConversationResponse> conversations;

    public ConversationsAdapter(ArrayList<ConversationResponse> conversations) {
        this.conversations = conversations;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = ChatConversationItemBinding.inflate(inflater, parent, false);
        ConversationsAdapter.ViewHolder viewHolder = new ConversationsAdapter.ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ConversationResponse conversation = conversations.get(position);
        Context context = holder.itemView.getContext();
        SessionManagerService sessionManagerService = new SessionManagerService(context);
        int userId = sessionManagerService.getUserId();

        ProfileDetailedResponse senderProfile = userId == conversation.getSender().getId() ?
                conversation.getReceiver() :
                conversation.getSender();
        String senderName = senderProfile.getFullName();
        String lastMessage = conversation.getLastMessage() != null ?
                conversation.getLastMessage() :
                "";
        String lastMessageDate = conversation.getLastMessage() != null ?
                DateUtil.covertDateToTimeAgo(conversation.getLastMessageDate()) :
                "";
        String logo = senderProfile.getProfilePicture() != null ?
                senderProfile.getProfilePicture() :
                "https://www.w3schools.com/w3images/avatar2.png";
        int conversationId = conversation.getId();

        binding.tvUserName.setText(senderName);
        binding.tvLastMessage.setText(lastMessage);
        binding.tvLastDiscussionDate.setText(lastMessageDate);

        Glide.with(context)
                .load(logo)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivUserImage);

        binding.llConversationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("conversation_id", conversationId);
                intent.putExtra("user_id", userId);
                intent.putExtra("receiver_name", senderName);
                intent.putExtra("receiver_image", logo);
                ((ConversationsActivity) context).startActivityForResult(intent, 1);
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
