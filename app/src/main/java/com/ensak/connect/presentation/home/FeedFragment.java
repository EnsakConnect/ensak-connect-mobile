package com.ensak.connect.presentation.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.adapters.feed.RecommandedOffersAdapter;
import com.ensak.connect.databinding.MainHomeFragementBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FeedFragment extends Fragment {

    private MainHomeFragementBinding binding;
    private FeedViewModel feedViewModel;
    private FeedViewModel recommendedFeedViewModel;
    private FeedAdapter feedAdapter;
    private RecommandedOffersAdapter recommandedOffersAdapter;
    private RecyclerView recyclerView;
    private boolean isLoading = false;
    private FeedResponse feed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = MainHomeFragementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initViewModel();
        initRecommendedViewModel();

        setupRecommendedOffersRecycleView();
        setupFeedRecycleView();
        setupFilterSpinner(getContext());

        feed = new FeedResponse();

        feedViewModel.fetchFeed(0, null, "");
        recommendedFeedViewModel.fetchFeed(0, null, "PFE");


        return root;
    }

    private void setupFeedRecycleView() {
        recyclerView = binding.rvAllOffers;
        feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set up the scroll listener for pagination
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Log.i("visiblePosition", scrollY + " " + ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() ));

                if (scrollY + ( v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight() ) == 0) {
                    Log.i("visiblePosition", "BOTTOM SCROLL");
                    onLoadMore();
                }
            }
        });
    }

    private void setupRecommendedOffersRecycleView() {
        recommandedOffersAdapter = new RecommandedOffersAdapter();
        binding.rvRecommendedOffers.setAdapter(recommandedOffersAdapter);
        binding.rvRecommendedOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initViewModel() {
        feedViewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);

        feedViewModel.getFeed().observe(getViewLifecycleOwner(), feedResponse -> {
            ArrayList<FeedContentResponse> list = feedResponse.getContent();
            list.addAll(feed.getContent());
            list.addAll(feedResponse.getContent());
            feed = feedResponse;

            feed.content = list;
            feedAdapter.setItems(feed);
            feedAdapter.notifyDataSetChanged();
            isLoading = false;
        });

        feedViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading){
                // TODO: Show loading indicator
            } else {
                // TODO: Hide loading indicator
            }
        });

        feedViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if(errorMessage.isEmpty()){ return; }
            Toast.makeText(getContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        });
    }

    private void initRecommendedViewModel() {
        recommendedFeedViewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);

        recommendedFeedViewModel.getFeed().observe(getViewLifecycleOwner(), feedResponse -> {
            recommandedOffersAdapter.setItems(feedResponse);
        });

        recommendedFeedViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if(isLoading){
                // TODO: Show loading indicator
            } else {
                // TODO: Hide loading indicator
            }
        });

        recommendedFeedViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if(errorMessage.isEmpty()){ return; }
            Toast.makeText(getContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        });
    }

    public void onLoadMore() {
        if(isLoading) return;

        if (feed.getPageNumber() < feed.getTotalPages() - 1) {
            isLoading = true;
            feedViewModel.fetchFeed(feed.getPageNumber() + 1, null, "");
        }
    }

    private void setupFilterSpinner(Context context) {
        binding.ivFilter.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.getMenuInflater().inflate(R.menu.home_filter, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    int itemId = menuItem.getItemId();
                    String filter = "";

                    if (itemId == R.id.filter_job_offer) {
                        filter = "CDI";
                    } else if (itemId == R.id.filter_intern_offer) {
                        filter = "PFE";
                    } else if (itemId == R.id.filter_doctor_offer) {
                        filter = "DOCTORATE";
                    } else if (itemId == R.id.filter_blog) {
                        filter = "BLOG";
                    } else if (itemId == R.id.filter_qa) {
                        filter = "Q&A";
                    }

                    if (!filter.isEmpty()) {
                        feedViewModel.fetchFeed(0, null, filter);
//                        getPosts(getContext(), 0, filter, false);
                    }

                    return true;
                }
            });

            popupMenu.show();
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}