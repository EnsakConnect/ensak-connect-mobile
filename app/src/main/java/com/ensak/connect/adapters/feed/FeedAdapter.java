package com.ensak.connect.adapters.feed;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.MainPostItemBinding;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.presentation.question_post.show.ShowQuestionPost;
import com.ensak.connect.presentation.report.ReportActivity;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.presentation.job_post.comments.CommentsActivity;
import com.ensak.connect.service.GlideAuthUrl;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainPostItemBinding binding;

    private ArrayList<FeedContentResponse> feed = new ArrayList<FeedContentResponse>();

    private OnPostInteractionListener postInteractionListener;

    public FeedAdapter(OnPostInteractionListener postInteractionListener){
        this.postInteractionListener = postInteractionListener;
    }

    public void updateItem(int position, FeedContentResponse contentResponse){
        feed.set(position,contentResponse);
        notifyItemChanged(position);
    }

    public void setItems(ArrayList<FeedContentResponse> feed) {
        this.feed = feed;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = MainPostItemBinding.inflate(inflater, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        FeedContentResponse post = feed.get(position);
        Log.i("DEBUG---------:","size"+Integer.toString(feed.size()));
        Context context = holder.itemView.getContext();

        binding.tvUserName.setText(post.getAuthor().getFullName());
        binding.tvBody.setText(post.getDescription());
        binding.tvTags.setText("#" + String.join(", #", post.getTags()));
        binding.chipTag.setText(post.getPostType());
        binding.tvTimeAgo.setText(post.getTimePassed().replace(" minutes ago", "m").replace(" hours ago", "h").replace(" days ago", "d").replace(" months ago", "mon").replace(" years ago", "y"));
        //offerItemHomeBinding.tvTimeAgo.setText(Utils.calculateTimeAgo(post.getDate()));

        if (post.getPostType().equals("Q&A")) {
            /**
             * Setup QNA Post
             */
            setupQNAPost(post, context);
        }
        else if (post.getPostType().equals("DOCTORATE")) {
            /**
             * Setup Doctorate post
             */
            setupDoctoratePost(post);
        }
        else if (post.getPostType().equals("BlogPost")) {
            setupBlogpost();
        } else if (post.getPostType().equals("CDI") || post.getPostType().equals("PFE")) {
            setupJobPost(post);
        }

        //all job posts
        if(post.getPostType().equals("CDI") || post.getPostType().equals("PFE") || post.getPostType().equals("DOCTORATE")){
            binding.llBlogInteractions.setVisibility(View.GONE);
        }
        if(post.getPostType().equals("Q&A")) {
            binding.llJobInteractions.setVisibility(View.GONE);
        }
        if(post.getPostType().equals("BlogPost")){
            binding.llJobInteractions.setVisibility(View.GONE);
        }
        Log.i("DEBUG:",post.getPostType());

        binding.llComment.setOnClickListener(view -> {
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra("postId", post.getId());
            context.startActivity(intent);
        });

        binding.llJobApply.setOnClickListener(view -> {
            postInteractionListener.onJobApply(position);
        });

        if(post.isLiked()){
            binding.tvJobApply.setText("Applied");
            binding.ivJobApply.setColorFilter(ContextCompat.getColor(context, R.color.primary), android.graphics.PorterDuff.Mode.SRC_IN);
            binding.tvJobApply.setTextColor(ContextCompat.getColor(context, R.color.primary));
        }

        //TODO: likes for blog


        binding.btnReport.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReportActivity.class);
            intent.putExtra("postId",post.getId());
            intent.putExtra("postType",post.getPostType());
            context.startActivity(intent);
        });

        setupAuthor(post, context);
    }

    private void setupAuthor(FeedContentResponse post, Context context) {
        binding.crdUserData.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(ProfileActivity.KEY_USER_ID, post.getAuthor().getId());
            context.startActivity(intent);
        });

        if(post.getAuthor().getTitle() == null || post.getAuthor().getTitle().isEmpty()){
            binding.tvUserTitle.setVisibility(View.GONE);
        } else {
            binding.tvUserTitle.setVisibility(View.VISIBLE);
            binding.tvUserTitle.setText(post.getAuthor().getTitle());
        }
        Glide.with(binding.getRoot().getContext())
                .load(
                        GlideAuthUrl.getUrl(
                                binding.getRoot().getContext(),
                                AppConstants.BASE_URL + "resources/" + post.getAuthor().getProfilePicture()
                        )
                )
                .placeholder(R.drawable.profile_picture_placeholder)
                .error(R.drawable.profile_picture_placeholder)
                .centerCrop()
                .into(binding.ivUserImage);
    }

    private void setupJobPost(FeedContentResponse post) {
        binding.llPositionDetails.setVisibility(View.VISIBLE);
        binding.llCompanyDetails.setVisibility(View.VISIBLE);
        binding.tvPositionTitle.setText(post.getTitle());
        binding.tvCompanyName.setText(post.getCompany().getCompanyName());
        binding.tvCompanyLocation.setText(post.getCompany().getLocation());
        Glide.with(binding.getRoot().getContext())
                .load(post.getCompany().getLogo())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_default_company_logo_round) // Error image in case of loading failure
                )
                .into(binding.ivCompanyLogo);

        if (post.getPostType().equals("PFE")) {
            binding.chipTag.setChipBackgroundColorResource(R.color.tag_pfe);
        } else {
            binding.chipTag.setChipBackgroundColorResource(R.color.tag_cdi);
        }
    }

    private void setupBlogpost() {
        binding.chipTag.setChipBackgroundColorResource(R.color.tag_blog);
        binding.ivBlogImage.setVisibility(View.VISIBLE);
        Glide.with(binding.getRoot().getContext())
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(binding.ivBlogImage);
    }

    private void setupDoctoratePost(FeedContentResponse post) {
        binding.chipTag.setChipBackgroundColorResource(R.color.tag_doctorate);
        binding.llPositionDetails.setVisibility(View.VISIBLE);
        binding.tvPositionTitle.setText(post.getTitle());
    }

    private void setupQNAPost(FeedContentResponse post, Context context) {
        binding.tvBody.setText(post.getTitle());
        binding.chipTag.setChipBackgroundColorResource(R.color.tag_qa_background);
        binding.chipTag.setTextColor(context.getColor(R.color.tag_qa_foreground));
        binding.tvBody.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowQuestionPost.class);
            intent.putExtra(ShowQuestionPost.KEY_QUESTION_POST_ID, post.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
