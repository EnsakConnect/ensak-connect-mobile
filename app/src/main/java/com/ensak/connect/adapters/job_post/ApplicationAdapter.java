package com.ensak.connect.adapters.job_post;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.repository.job_post.model.JobPostApplicationResponse;
import com.ensak.connect.service.GlideAuthUrl;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<JobPostApplicationResponse> applications = new ArrayList<>();

    public List<JobPostApplicationResponse> getApplications() {
        return applications;
    }

    public void setApplications(List<JobPostApplicationResponse> applications) {
        this.applications = applications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_applicant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        android.content.Context context = holder.itemView.getContext();
        JobPostApplicationResponse post = applications.get(position);

        TextView tvUserName = holder.itemView.findViewById(R.id.tv_user_name);
        LinearLayout crdUserData = holder.itemView.findViewById(R.id.crdUserData);
        TextView tvUserTitle = holder.itemView.findViewById(R.id.tv_user_title);
        ImageView ivUserImage = holder.itemView.findViewById(R.id.iv_user_image);

        tvUserName.setText(post.getApplicant().getFullName());
        crdUserData.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(ProfileActivity.KEY_USER_ID, post.getApplicant().getUserId());
            context.startActivity(intent);
        });

        if (post.getApplicant().getTitle() == null || post.getApplicant().getTitle().isEmpty()) {
            tvUserTitle.setVisibility(View.GONE);
        } else {
            tvUserTitle.setVisibility(View.VISIBLE);
            tvUserTitle.setText(post.getApplicant().getTitle());
        }
        if(post.getApplicant().getProfilePicture() == null || post.getApplicant().getProfilePicture().isEmpty()){
            Glide.with(context)
                    .load(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(ivUserImage);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(
                            GlideAuthUrl.getUrl(
                                    holder.itemView.getContext(),
                                    AppConstants.BASE_URL + "resources/" + post.getApplicant().getProfilePicture()
                            )
                    )
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(ivUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return applications.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
