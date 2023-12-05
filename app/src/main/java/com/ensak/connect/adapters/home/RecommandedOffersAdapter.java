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
import com.ensak.connect.reponse.PostResponse;
import com.ensak.connect.utils.Utils;

import java.util.ArrayList;

public class RecommandedOffersAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    RecommendedOfferItemBinding binding;
    private ArrayList<PostResponse> posts;

    public RecommandedOffersAdapter(ArrayList<PostResponse> posts) {
        this.posts = posts;
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
        PostResponse post = posts.get(position);

        binding.tvCompanyName.setText(post.getUser().getFirstname());
        binding.tvTitle.setText(post.getUser().getFirstname());
        binding.chipTag.setText(post.getType());
        binding.tvTags.setText(Utils.calculateTimeAgo(post.getDate()));
        binding.tvTags.setText(String.join(", ", post.getTags()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);


        }
    }
}
