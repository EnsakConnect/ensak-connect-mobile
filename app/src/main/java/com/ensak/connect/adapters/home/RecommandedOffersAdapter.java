package com.ensak.connect.adapters.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.databinding.MainRecommendedOfferItemBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;

public class RecommandedOffersAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    MainRecommendedOfferItemBinding binding;
    private FeedResponse feed;

    public RecommandedOffersAdapter(FeedResponse feed) {
        this.feed = feed;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = MainRecommendedOfferItemBinding.inflate(inflater, parent, false);
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
