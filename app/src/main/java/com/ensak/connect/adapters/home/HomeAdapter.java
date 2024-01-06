package com.ensak.connect.adapters.home;


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
import com.ensak.connect.databinding.OfferItemHomeBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.ensak.connect.view.comments.CommentsActivity;

public class HomeAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {


    OfferItemHomeBinding offerItemHomeBinding;

    private FeedResponse feed;

    public HomeAdapter(FeedResponse feed) {
        this.feed = feed;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        offerItemHomeBinding = OfferItemHomeBinding.inflate(inflater, parent, false);
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
            offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_qa);
            offerItemHomeBinding.tvBody.setText(post.getTitle());
        } else if (post.getPostType().equals("DOCTORATE")) {
            offerItemHomeBinding.chipTag.setChipBackgroundColorResource(R.color.tag_doctorate);
            offerItemHomeBinding.llPositionDetails.setVisibility(View.VISIBLE);
            offerItemHomeBinding.tvPositionTitle.setText(post.getTitle());
        } else if (post.getPostType().equals("BLOG")) {
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

        offerItemHomeBinding.llComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("postId", post.getId());
                context.startActivity(intent);
            }
        });

        // TODO: 5/12/2023 add user image
        // TODO: 5/12/2023 add user title
        offerItemHomeBinding.tvUserTitle.setText("Développeur Android");
        Glide.with(offerItemHomeBinding.getRoot().getContext())
                .load("https://www.w3schools.com/w3images/avatar2.png")
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background) // Placeholder image
                        .error(R.drawable.ic_launcher_background) // Error image in case of loading failure
                )
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
