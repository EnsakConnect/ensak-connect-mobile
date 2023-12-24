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
import com.ensak.connect.reponse.feed.FeedContentResponse;
import com.ensak.connect.reponse.feed.FeedResponse;
import com.ensak.connect.utils.Utils;
import com.ensak.connect.view.comments.CommentsActivity;

import java.util.ArrayList;

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
        offerItemHomeBinding.tvTags.setText(String.join(", ", post.getTags()));
        offerItemHomeBinding.chipTag.setText(post.getPostType());
        offerItemHomeBinding.tvTimeAgo.setText(post.getTimePassed());
        //offerItemHomeBinding.tvTimeAgo.setText(Utils.calculateTimeAgo(post.getDate()));

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
