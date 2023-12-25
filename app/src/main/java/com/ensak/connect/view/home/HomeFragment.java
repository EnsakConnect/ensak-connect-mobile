package com.ensak.connect.view.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.adapters.home.HomeAdapter;
import com.ensak.connect.adapters.home.RecommandedOffersAdapter;
import com.ensak.connect.databinding.FragmentHomeBinding;
import com.ensak.connect.reponse.feed.FeedResponse;
import com.ensak.connect.view_model.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rvAllOffers, rvRecommendedOffers;
    private HomeViewModel homeViewModel;
    private FeedResponse feed, recommendedOffersFeed;
    private HomeAdapter adapter;
    private RecommandedOffersAdapter recommandedOffersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        feed = new FeedResponse();
        recommendedOffersFeed = new FeedResponse();

        rvRecommendedOffers = binding.rvRecommendedOffers;
        recommandedOffersAdapter = new RecommandedOffersAdapter(recommendedOffersFeed);
        rvRecommendedOffers.setAdapter(recommandedOffersAdapter);
        rvRecommendedOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAllOffers = binding.rvAllOffers;
        adapter = new HomeAdapter(feed);
        rvAllOffers.setAdapter(adapter);
        rvAllOffers.setLayoutManager(new LinearLayoutManager(getContext()));

        getPosts(getContext(), "");
        getRecommendedOffersFeed(getContext());


//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void getRecommendedOffersFeed(Context context) {
        getPosts(context, "CDI,PFE");
    }

    private void getPosts(Context context, String filter) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        try {
            homeViewModel.getFeed(0, "", filter).observe((LifecycleOwner) context, response -> {
                if (response != null) {

                    String message = String.valueOf(response.getPageNumber());
                    Log.d("Main Log", message);

                    if (filter.isEmpty()) {
                        feed.setContent(response.getContent());
                        feed.setPageNumber(response.getPageNumber());
                        feed.setTotalPages(response.getTotalPages());
                        adapter.notifyDataSetChanged();
                    } else {
                        recommendedOffersFeed.setContent(response.getContent());
                        recommendedOffersFeed.setPageNumber(response.getPageNumber());
                        recommendedOffersFeed.setTotalPages(response.getTotalPages());
                        recommandedOffersAdapter.notifyDataSetChanged();
                    }


                }
            });
        } catch (Throwable ex) {
            Toast.makeText(context, "Error getting posts.", Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}