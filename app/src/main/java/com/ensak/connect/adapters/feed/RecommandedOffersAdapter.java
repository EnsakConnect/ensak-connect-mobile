package com.ensak.connect.adapters.feed;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.adapters.conversations.UsersAdapter;
import com.ensak.connect.databinding.MainRecommendedOfferItemBinding;
import com.ensak.connect.databinding.UserProfileItemBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class RecommandedOffersAdapter extends
        RecyclerView.Adapter<RecommandedOffersAdapter.ViewHolder> {

    private List<FeedContentResponse> feed = new ArrayList<>();

    @Override
    public RecommandedOffersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(MainRecommendedOfferItemBinding.inflate(inflater, parent, false));
    }

    public void setItems(List<FeedContentResponse> feed) {
        this.feed.clear();
        this.feed.addAll(feed);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedContentResponse post = feed.get(position);
        if(post.getCompany() != null) {
            holder.getBinding().tvCompanyName.setText(post.getCompany().getCompanyName() + " - " + post.getCompany().getLocation());
        } else {
            holder.binding.tvCompanyName.setVisibility(View.INVISIBLE);
        }
        holder.getBinding().tvTitle.setText(post.getTitle());
        holder.getBinding().chipTag.setText(post.getPostType());
        holder.getBinding().tvTags.setText("#" + String.join(", #", post.getTags()));
    }

    @Override
    public int getItemCount() {
        return Math.min(feed.size(), 5);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private MainRecommendedOfferItemBinding binding;

        public ViewHolder(MainRecommendedOfferItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public MainRecommendedOfferItemBinding getBinding() {
            return binding;
        }
    }
}
