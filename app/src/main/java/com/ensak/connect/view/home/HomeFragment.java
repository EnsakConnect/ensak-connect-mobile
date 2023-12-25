package com.ensak.connect.view.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
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
    private boolean isLoadingMore = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        feed = new FeedResponse();
        feed.content = new ArrayList<>();
        recommendedOffersFeed = new FeedResponse();

        rvRecommendedOffers = binding.rvRecommendedOffers;
        recommandedOffersAdapter = new RecommandedOffersAdapter(recommendedOffersFeed);
        rvRecommendedOffers.setAdapter(recommandedOffersAdapter);
        rvRecommendedOffers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAllOffers = binding.rvAllOffers;
        adapter = new HomeAdapter(feed);
        rvAllOffers.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvAllOffers.setLayoutManager(layoutManager);

        // Set up the scroll listener for pagination
        rvAllOffers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Trigger load more when the user is at the end of the list
                if (!isLoadingMore) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        isLoadingMore = true;
                        onLoadMore();
                    }
                }
            }
        });

        getPosts(getContext(), 0, "", false);
        getRecommendedOffersFeed(getContext());

        setupFilterSpinner(getContext());

        return root;
    }

    public void onLoadMore() {
        if (feed.getPageNumber() < feed.getTotalPages() - 1) {
//            getPosts(getContext(), feed.getPageNumber() + 1, "");
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
                        getPosts(getContext(), 0, filter, false);
                    }

                    return true;
                }
            });

            popupMenu.show();
        });


    }

    private void getRecommendedOffersFeed(Context context) {
        getPosts(context, 0, "PFE", true);
    }

    private void getPosts(Context context, int page, String filter, boolean isRecommended) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        try {
            homeViewModel.getFeed(page, "", filter).observe((LifecycleOwner) context, response -> {
                if (response != null) {

                    String message = String.valueOf(response.getPageNumber());
                    Log.d("Main Log", message);

                    if (!isRecommended) {
                        feed.content.clear();
                        feed.content.addAll(response.getContent());
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