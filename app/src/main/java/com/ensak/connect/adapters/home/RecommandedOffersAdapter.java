package com.ensak.connect.adapters.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ensak.connect.R;
import com.ensak.connect.databinding.RecommendedOfferItemBinding;
import com.ensak.connect.models.HomeModel;
import com.ensak.connect.reponse.feed.FeedContentResponse;
import com.ensak.connect.reponse.feed.FeedResponse;
import com.ensak.connect.utils.Utils;

import java.util.ArrayList;

public class RecommandedOffersAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecommendedOfferItemBinding binding;
    private FeedResponse feed;

    public RecommandedOffersAdapter(FeedResponse feed) {
        this.feed = feed;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = RecommendedOfferItemBinding.inflate(inflater, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedContentResponse post = feed.getContent().get(position);

        binding.tvCompanyName.setText("Alten");
        binding.tvTitle.setText("Software Engineer");
        binding.chipTag.setText(post.getPostType());
        binding.tvTags.setText("#" + String.join(", #", post.getTags()));
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
