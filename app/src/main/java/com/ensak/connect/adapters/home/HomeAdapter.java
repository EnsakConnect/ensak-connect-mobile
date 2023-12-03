package com.ensak.connect.adapters.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.databinding.OfferItemHomeBinding;

public class HomeAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {


    OfferItemHomeBinding offerItemHomeBinding;

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
