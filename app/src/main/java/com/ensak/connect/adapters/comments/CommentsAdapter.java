package com.ensak.connect.adapters.comments;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.JobPostCommentItemBinding;
import com.ensak.connect.repository.blog_post.model.BlogPostCommentResponse;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.utils.DateUtil;

import java.util.ArrayList;

public class CommentsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    JobPostCommentItemBinding binding;
    private ArrayList<BlogPostCommentResponse> comments;

    public CommentsAdapter(ArrayList<BlogPostCommentResponse> comments) {
        this.comments = comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = JobPostCommentItemBinding.inflate(inflater, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        BlogPostCommentResponse comment = comments.get(position);
        Context context = holder.itemView.getContext();

        binding.tvUserName.setText(comment.getUser().getFullName());
        binding.tvUserTitle.setText(comment.getUser().getTitle());
        binding.tvCommentDate.setText(DateUtil.calculateTimeAgo(comment.getDate()));
        binding.tvComment.setText(comment.getComment());

        if(comment.getUser().getProfilePicture() == null || comment.getUser().getProfilePicture().isEmpty()){
            Glide.with(context)
                    .load(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(binding.ivUserImage);
        } else {
            Glide.with(context)
                    .load(
                            GlideAuthUrl.getUrl(context, AppConstants.BASE_URL + "resources/" + comment.getUser().getProfilePicture())
                    ).placeholder(R.drawable.profile_banner_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(binding.ivUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void updateComments(ArrayList<BlogPostCommentResponse> commentsValue) {
        comments = commentsValue;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);


        }
    }
}
