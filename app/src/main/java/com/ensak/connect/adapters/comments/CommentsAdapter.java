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
import com.ensak.connect.databinding.JobPostCommentItemBinding;
import com.ensak.connect.repository.job_post.model.JobPostCommentResponse;
import com.ensak.connect.utils.DateUtil;

import java.util.ArrayList;

public class CommentsAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    JobPostCommentItemBinding binding;
    private ArrayList<JobPostCommentResponse> comments;

    public CommentsAdapter(ArrayList<JobPostCommentResponse> comments) {
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
        JobPostCommentResponse comment = comments.get(position);
        Context context = holder.itemView.getContext();

        binding.tvUserName.setText(comment.getUser().getFirstname() + " " + comment.getUser().getLastname());
        binding.tvUserTitle.setText("Full Stack Developer - SQLI");
        binding.tvCommentDate.setText(DateUtil.calculateTimeAgo(comment.getDate()));
        binding.tvComment.setText(comment.getComment());

        Glide.with(context)
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);


        }
    }
}
