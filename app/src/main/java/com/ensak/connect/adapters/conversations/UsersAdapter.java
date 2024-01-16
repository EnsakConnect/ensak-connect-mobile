package com.ensak.connect.adapters.conversations;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.databinding.UserProfileItemBinding;
import com.ensak.connect.presentation.chat.conversation.AddConversationActivity;
import com.ensak.connect.repository.profile.model.ProfileResponseDTO;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private ArrayList<ProfileResponseDTO> users;

    public UsersAdapter(ArrayList<ProfileResponseDTO> users) {
        this.users = users;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        UserProfileItemBinding binding = UserProfileItemBinding.inflate(inflater, parent, false);
        UsersAdapter.ViewHolder viewHolder = new UsersAdapter.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        ProfileResponseDTO user = users.get(position);
        Context context = holder.itemView.getContext();

        String logo = user.getProfilePicture() != null ?
                user.getProfilePicture() :
                "https://www.w3schools.com/w3images/avatar2.png";

        holder.getBinding().tvUserName.setText(user.getFullName());

        Glide.with(context)
                .load(logo)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(holder.getBinding().ivUserImage);

        holder.getBinding().cardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddConversationActivity) context).onClick(user);
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private UserProfileItemBinding binding;
        public ViewHolder(UserProfileItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public UserProfileItemBinding getBinding() {
            return binding;
        }
    }
}
