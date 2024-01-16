package com.ensak.connect.adapters.feed;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.constants.AppConstants;
import com.ensak.connect.presentation.job_post.JobApplicationsActivity;
import com.ensak.connect.presentation.profile.ProfileActivity;
import com.ensak.connect.presentation.question_post.show.ShowQuestionPost;
import com.ensak.connect.presentation.report.ReportActivity;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.presentation.blog_post.comments.CommentsActivity;
import com.ensak.connect.service.GlideAuthUrl;
import com.ensak.connect.service.SessionManagerService;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //MainPostItemBinding binding;

    private SessionManagerService managerService;

    private List<FeedContentResponse> feed = new ArrayList<>();

    public void addList(List<FeedContentResponse> alist) {
        feed.addAll(alist);
        notifyItemRangeChanged(feed.size() - alist.size(), feed.size());
    }

    private OnPostInteractionListener postInteractionListener;

    public FeedAdapter(OnPostInteractionListener postInteractionListener) {
        this.postInteractionListener = postInteractionListener;
    }

    public void updateItem(int position, FeedContentResponse contentResponse) {
        feed.set(position, contentResponse);
        notifyItemChanged(position);
    }

    public void setItems(ArrayList<FeedContentResponse> feed) {
        this.feed.clear();
        this.feed.addAll(feed);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_post_item, parent, false);
        managerService = new SessionManagerService(parent.getContext());
        return new ViewHolder(view);
    }

    public List<FeedContentResponse> getFeed() {
        return feed;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.setIsRecyclable(false);
        FeedContentResponse post = feed.get(position);
        Context context = holder.itemView.getContext();

        TextView tvUserName = holder.itemView.findViewById(R.id.tv_user_name);
        TextView tvBody = holder.itemView.findViewById(R.id.tv_body);
        TextView tvTags = holder.itemView.findViewById(R.id.tv_tags);
        Chip chipTag = holder.itemView.findViewById(R.id.chip_tag);
        TextView tvTimeAgo = holder.itemView.findViewById(R.id.tv_time_ago);
        LinearLayout llLike = holder.itemView.findViewById(R.id.ll_like);
        TextView tvLike = holder.itemView.findViewById(R.id.tv_like);
        ImageView ivLike = holder.itemView.findViewById(R.id.iv_like);
        LinearLayout llShare = holder.itemView.findViewById(R.id.ll_share);
        LinearLayout llLikeDisabled = holder.itemView.findViewById(R.id.ll_like_disabled);

        LinearLayout llBlogInteractions = holder.itemView.findViewById(R.id.ll_blog_interactions);
        LinearLayout llJobInteractions = holder.itemView.findViewById(R.id.ll_job_interactions);
        ImageView btnReport = holder.itemView.findViewById(R.id.btnReport);
        LinearLayout llJobApply = holder.itemView.findViewById(R.id.ll_job_apply);
        TextView tvJobApply = holder.itemView.findViewById(R.id.tv_job_apply);
        ImageView ivJobApply = holder.itemView.findViewById(R.id.iv_job_apply);
        LinearLayout llComment = holder.itemView.findViewById(R.id.ll_comment);

        tvUserName.setText(post.getAuthor().getFullName());
        tvBody.setText(post.getDescription());
        tvTags.setText("#" + String.join(", #", post.getTags()));
        chipTag.setText(post.getPostType());
        tvTimeAgo.setText(post.getTimePassed().replace(" minutes ago", "m").replace(" hours ago", "h").replace(" days ago", "d").replace(" months ago", "mon").replace(" years ago", "y"));

        if (post.getPostType().equals("Q&A")) {
            /**
             * Setup QNA Post
             */
            setupQNAPost(post, context, holder);
        } else if (post.getPostType().equals("DOCTORATE")) {
            /**
             * Setup Doctorate post
             */
            setupDoctoratePost(post, holder);
        } else if (post.getPostType().equals("BlogPost")) {
            setupBlogpost(holder);

            llComment.setOnClickListener(view -> {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("postId", post.getId());
                context.startActivity(intent);
            });
        } else if (post.getPostType().equals("CDI") || post.getPostType().equals("PFE")) {
            setupJobPost(post, holder);
        }

        //all job posts
        if (post.getPostType().equals("CDI") || post.getPostType().equals("PFE") || post.getPostType().equals("DOCTORATE")) {
            llBlogInteractions.setVisibility(View.GONE);
        }
        if (post.getPostType().equals("Q&A")) {
            llJobInteractions.setVisibility(View.GONE);
        }
        if (post.getPostType().equals("BlogPost")) {
            llJobInteractions.setVisibility(View.GONE);
        }
        Log.i("DEBUG:", post.getPostType());


        llJobApply.setOnClickListener(view -> {
            postInteractionListener.onJobApply(position);
        });


        tvLike.setText(post.getLikesCount() > 0 ? post.getLikesCount() + " likes" : "Like");

        if (post.isLiked()) {
            tvJobApply.setText("Applied");
            ivJobApply.setColorFilter(ContextCompat.getColor(context, R.color.primary), android.graphics.PorterDuff.Mode.SRC_IN);
            tvJobApply.setTextColor(ContextCompat.getColor(context, R.color.primary));
            // like also qa and blog post
            ivLike.setImageResource(R.drawable.ic_thumbs_up_filled);
        } else {
            ivLike.setImageResource(R.drawable.ic_thumb_up);
        }

        //TODO: likes for blog
        llLike.setOnClickListener(view -> {
            if (post.getPostType().equals("BlogPost"))
                postInteractionListener.likeDislikeBlogPost(post, position);
            else if (post.getPostType().equals("Q&A"))
                postInteractionListener.likeDislikeQuestionPost(post, position);
        });


        btnReport.setOnClickListener(view -> {
            Intent intent = new Intent(context, ReportActivity.class);
            intent.putExtra("postId", post.getId());
            intent.putExtra("postType", post.getPostType());
            context.startActivity(intent);
        });

        llShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, post.getDescription());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            context.startActivity(shareIntent);
        });

        llLikeDisabled.setOnClickListener(v -> {
            Toast.makeText(context, "Likes are currently disabled for job posts", Toast.LENGTH_SHORT).show();
        });

        setupAuthor(post, context, holder);
    }

    private void setupAuthor(FeedContentResponse post, Context context, RecyclerView.ViewHolder holder) {


        LinearLayout crdUserData = holder.itemView.findViewById(R.id.crdUserData);
        TextView tvUserTitle = holder.itemView.findViewById(R.id.tv_user_title);
        ImageView ivUserImage = holder.itemView.findViewById(R.id.iv_user_image);
        crdUserData.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra(ProfileActivity.KEY_USER_ID, post.getAuthor().getUserId());
            context.startActivity(intent);
        });

        if (post.getAuthor().getTitle() == null || post.getAuthor().getTitle().isEmpty()) {
            tvUserTitle.setVisibility(View.GONE);
        } else {
            tvUserTitle.setVisibility(View.VISIBLE);
            tvUserTitle.setText(post.getAuthor().getTitle());
        }
        if(post.getAuthor().getProfilePicture() == null || post.getAuthor().getProfilePicture().isEmpty()){
            Glide.with(context)
                    .load(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(ivUserImage);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(
                            GlideAuthUrl.getUrl(
                                    holder.itemView.getContext(),
                                    AppConstants.BASE_URL + "resources/" + post.getAuthor().getProfilePicture()
                            )
                    )
                    .placeholder(R.drawable.profile_picture_placeholder)
                    .error(R.drawable.profile_picture_placeholder)
                    .centerCrop()
                    .into(ivUserImage);
        }
    }

    private void setupJobPost(FeedContentResponse post, RecyclerView.ViewHolder holder) {

        LinearLayout llPositionDetails = holder.itemView.findViewById(R.id.ll_position_details);
        LinearLayout llCompanyDetails = holder.itemView.findViewById(R.id.ll_company_details);
        TextView tvPositionTitle = holder.itemView.findViewById(R.id.tv_position_title);
        TextView tvCompanyName = holder.itemView.findViewById(R.id.tv_company_name);
        TextView tvCompanyLocation = holder.itemView.findViewById(R.id.tv_company_location);
        Chip chipTag = holder.itemView.findViewById(R.id.chip_tag);
        ImageView ivCompanyLogo = holder.itemView.findViewById(R.id.iv_company_logo);
        TextView tvBody = holder.itemView.findViewById(R.id.tv_body);

        tvBody.setOnClickListener(v -> {
            if(managerService.getUserId() != post.getAuthor().getUserId())
                return;
            Intent intent = new Intent(holder.itemView.getContext(), JobApplicationsActivity.class);
            intent.putExtra("post", post);
            holder.itemView.getContext().startActivity(intent);
            Log.i("DEBUG1:","we clicked");
        });


        llPositionDetails.setVisibility(View.VISIBLE);
        llCompanyDetails.setVisibility(View.VISIBLE);
        tvPositionTitle.setText(post.getTitle());
        tvCompanyName.setText(post.getCompany().getCompanyName());
        tvCompanyLocation.setText(post.getCompany().getLocation());
        Glide.with(holder.itemView.getContext())
                .load(post.getCompany().getLogo())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_default_company_logo_round) // Error image in case of loading failure
                )
                .into(ivCompanyLogo);

        if (post.getPostType().equals("PFE")) {
            chipTag.setChipBackgroundColorResource(R.color.chip_pfe_bg);
            chipTag.setTextColor(holder.itemView.getContext().getColor(R.color.chip_pfe_fg));
        } else {
            chipTag.setChipBackgroundColorResource(R.color.chip_cdi_bg);
            chipTag.setTextColor(holder.itemView.getContext().getColor(R.color.chip_cdi_fg));

        }
    }

    private void setupBlogpost(RecyclerView.ViewHolder holder) {

        Chip chipTag = holder.itemView.findViewById(R.id.chip_tag);
        ImageView ivBlogImage = holder.itemView.findViewById(R.id.iv_blog_image);


        chipTag.setChipBackgroundColorResource(R.color.chip_blog_bg);
        chipTag.setTextColor(holder.itemView.getContext().getColor(R.color.chip_blog_fg));
        ivBlogImage.setVisibility(View.VISIBLE);
        Glide.with(holder.itemView.getContext())
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
                .into(ivBlogImage);

    }

    private void setupDoctoratePost(FeedContentResponse post, RecyclerView.ViewHolder holder) {

        Chip chipTag = holder.itemView.findViewById(R.id.chip_tag);
        LinearLayout llPositionDetails = holder.itemView.findViewById(R.id.ll_position_details);
        TextView tvPositionTitle = holder.itemView.findViewById(R.id.tv_position_title);
        TextView tvBody = holder.itemView.findViewById(R.id.tv_body);
        tvBody.setOnClickListener(v -> {
            if(managerService.getUserId() != post.getAuthor().getUserId())
                return;
            Intent intent = new Intent(holder.itemView.getContext(), JobApplicationsActivity.class);
            intent.putExtra("post", post);
            holder.itemView.getContext().startActivity(intent);
            Log.i("DEBUG1:","we clicked");
        });
        chipTag.setChipBackgroundColorResource(R.color.chip_doc_bg);
        chipTag.setTextColor(holder.itemView.getContext().getColor(R.color.chip_doc_fg));
        llPositionDetails.setVisibility(View.VISIBLE);
        tvPositionTitle.setText(post.getTitle());
    }

    private void setupQNAPost(FeedContentResponse post, Context context, RecyclerView.ViewHolder holder) {

        TextView tvUserName = holder.itemView.findViewById(R.id.tv_user_name);
        TextView tvBody = holder.itemView.findViewById(R.id.tv_body);
        TextView tvTags = holder.itemView.findViewById(R.id.tv_tags);
        Chip chipTag = holder.itemView.findViewById(R.id.chip_tag);
        TextView tvTimeAgo = holder.itemView.findViewById(R.id.tv_time_ago);
        TextView tvCommentLbl = holder.itemView.findViewById(R.id.tv_comment_lbl);
        LinearLayout llComment = holder.itemView.findViewById(R.id.ll_comment);

        tvBody.setText(post.getTitle());
        chipTag.setChipBackgroundColorResource(R.color.tag_qa_background);
        chipTag.setTextColor(context.getColor(R.color.tag_qa_foreground));
        tvBody.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowQuestionPost.class);
            intent.putExtra(ShowQuestionPost.KEY_QUESTION_POST_ID, post.getId());
            context.startActivity(intent);
        });

        tvCommentLbl.setText("Answers");
        llComment.setOnClickListener(v -> {
            Intent showQuestionIntent = new Intent(context, ShowQuestionPost.class);
            showQuestionIntent.putExtra(ShowQuestionPost.KEY_QUESTION_POST_ID, post.getId());
            context.startActivity(showQuestionIntent);
        });
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }

    public void clearContent() {
        feed.clear();
        notifyDataSetChanged();
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
