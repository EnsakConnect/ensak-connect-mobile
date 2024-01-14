package com.ensak.connect.adapters.feed;


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
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.databinding.MainPostItemBinding;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.presentation.question_post.show.ShowQuestionPost;
import com.ensak.connect.presentation.report.ReportActivity;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.presentation.job_post.comments.CommentsActivity;
import com.ensak.connect.service.GlideAuthUrl;

public class FeedAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainPostItemBinding offerItemHomeBinding;

    private FeedResponse feed = new FeedResponse();

    public void setItems(FeedResponse feed) {
        this.feed = feed;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        offerItemHomeBinding = MainPostItemBinding.inflate(inflater, parent, false);
        ViewHolder viewHolder = new ViewHolder(offerItemHomeBinding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedContentResponse post = feed.getContent().get(position);
        Context context = holder.itemView.getContext();

        offerItemHomeBinding.tvUserName.setText(post.getAuthor().getFullName());
        offerItemHomeBinding.tvBody.setText(post.getDescription());
        offerItemHomeBinding.tvTags.setText("#" + String.join(", #", post.getTags()));
        offerItemHomeBinding.chipTag.setText(post.getPostType());
        offerItemHomeBinding.tvTimeAgo.setText(post.getTimePassed().replace(" minutes ago", "m").replace(" hours ago", "h").replace(" days ago", "d").replace(" months ago", "mon").replace(" years ago", "y"));
        //offerItemHomeBinding.tvTimeAgo.setText(Utils.calculateTimeAgo(post.getDate()));

        if (post.getPostType().equals("Q&A")) {
            /**
             * Setup QNA Post
             */
            offerItemHomeBinding.tvBody.setText(post.getTitle());
            offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_qa_background);
            offerItemHomeBinding.chipTag.setTextColor(context.getColor(R.color.tag_qa_foreground));
            offerItemHomeBinding.tvBody.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShowQuestionPost.class);
                intent.putExtra(ShowQuestionPost.KEY_QUESTION_POST_ID, post.getId());
                context.startActivity(intent);
            });
        }
        else if (post.getPostType().equals("DOCTORATE")) {
            /**
             * Setup Doctorate post
             */
            offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_doctorate);
            offerItemHomeBinding.llPositionDetails.setVisibility(View.VISIBLE);
            offerItemHomeBinding.tvPositionTitle.setText(post.getTitle());
        }
        else if (post.getPostType().equals("BLOG")) {
            offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_blog);
            offerItemHomeBinding.ivBlogImage.setVisibility(View.VISIBLE);
            Glide.with(offerItemHomeBinding.getRoot().getContext())
                    .load("https://www.w3schools.com/w3images/avatar2.png")
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                            .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                    )
                    .into(offerItemHomeBinding.ivBlogImage);
        } else if (post.getPostType().equals("CDI") || post.getPostType().equals("PFE")) {
            offerItemHomeBinding.llPositionDetails.setVisibility(View.VISIBLE);
            offerItemHomeBinding.llCompanyDetails.setVisibility(View.VISIBLE);
            offerItemHomeBinding.tvPositionTitle.setText(post.getTitle());
            offerItemHomeBinding.tvCompanyName.setText("Alten");
            offerItemHomeBinding.tvCompanyLocation.setText("Rabat, Rabat-Salé-Kenitra, Morocco (Hybrid)");
            Glide.with(offerItemHomeBinding.getRoot().getContext())
                    .load("https://pbs.twimg.com/profile_images/794195648178487296/mBLvqqXu_400x400.jpg")
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                            .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                    )
                    .into(offerItemHomeBinding.ivCompanyLogo);

            if (post.getPostType().equals("PFE")) {
                offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_pfe);
            } else {
                offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_cdi);
            }
        }

        offerItemHomeBinding.llComment.setOnClickListener(view -> {
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra("postId", post.getId());
            context.startActivity(intent);
        });

        offerItemHomeBinding.btnReport.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReportActivity.class);
            intent.putExtra("postId",post.getId());
            intent.putExtra("postType",post.getPostType());
            context.startActivity(intent);
        });

        offerItemHomeBinding.crdUserData.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(ProfileActivity.KEY_USER_ID, post.getAuthor().getId());
            context.startActivity(intent);
        });

        if(post.getAuthor().getTitle() == null || post.getAuthor().getTitle().isEmpty()){
            offerItemHomeBinding.tvUserTitle.setVisibility(View.GONE);
        } else {
            offerItemHomeBinding.tvUserTitle.setVisibility(View.VISIBLE);
            offerItemHomeBinding.tvUserTitle.setText(post.getAuthor().getTitle());
        }
        Glide.with(offerItemHomeBinding.getRoot().getContext())
                .load(
                        GlideAuthUrl.getUrl(
                                offerItemHomeBinding.getRoot().getContext(),
                                AppConstants.BASE_URL + "resources/" + post.getAuthor().getProfilePicture()
                        )
                )
                .placeholder(R.drawable.profile_picture_placeholder)
                .error(R.drawable.profile_picture_placeholder)
                .centerCrop()
                .into(offerItemHomeBinding.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return feed.getContent().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}
