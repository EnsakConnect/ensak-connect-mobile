package com.ensak.connect.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.databinding.OfferItemHomeBinding;
import com.ensak.connect.databinding.RecommandedOffersHomeBinding;
import com.ensak.connect.databinding.RecommendedOfferItemBinding;
import com.ensak.connect.models.HomeModel;

import java.util.ArrayList;

public class HomeAdapter extends
        RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    OfferItemHomeBinding homeBinding;
    RecommandedOffersHomeBinding recommandedOffersHomeBinding;
    RecommendedOfferItemBinding recommendedOfferItemBinding;
    OfferItemHomeBinding offerItemHomeBinding;

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        offerItemHomeBinding = OfferItemHomeBinding.inflate(inflater, parent, false);

        ViewHolder viewHolder = new ViewHolder(offerItemHomeBinding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        // Get the data model based on position

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);


        }
    }
}
