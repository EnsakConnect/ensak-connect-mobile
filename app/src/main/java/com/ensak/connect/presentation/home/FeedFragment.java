package com.ensak.connect.presentation.home;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.adapters.feed.OnPostInteractionListener;
import com.ensak.connect.adapters.feed.RecommandedOffersAdapter;
import com.ensak.connect.adapters.fragmentAdapter.FragmentAdapter;
import com.ensak.connect.databinding.MainHomeFragementBinding;
import com.ensak.connect.presentation.job_post.JobPostViewModel;
import com.ensak.connect.presentation.job_post.create.fragments.DetailsFragment;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FeedFragment extends Fragment implements OnPostInteractionListener {

    private MainHomeFragementBinding binding;
    private FeedViewModel feedViewModel;
    private RecommendedOffersViewModel recommendedFeedViewModel;
    private JobPostViewModel jopPostViewModel;
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
        initJobPostViewModel();

        setupRecommendedOffersRecycleView();
        setupFeedRecycleView();
        setupFilterSpinner(getContext());

        binding.tvSeeAllOffers.setVisibility(View.GONE);


        feed = new FeedResponse();

        feedViewModel.fetchFeed(0, null, "");
        recommendedFeedViewModel.fetchFeed(0, null, "PFE");

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void initJobPostViewModel() {
        jopPostViewModel = new ViewModelProvider(this).get(JobPostViewModel.class);

        jopPostViewModel.getIsSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Application Sent", Toast.LENGTH_SHORT).show();
            }
        });

        jopPostViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error)
                Toast.makeText(getContext(), "Error when sending application", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupFeedRecycleView() {
        recyclerView = binding.rvAllOffers;
        feedAdapter = new FeedAdapter(this);
        recyclerView.setAdapter(feedAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Set up the scroll listener for pagination
        binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY + (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight()) == 0) {
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
            feed = feedResponse;
            feedAdapter.addList(feedResponse.content);
            isLoading = false;
        });

        feedViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading)
                binding.loadingProgressBar.setVisibility(View.VISIBLE);
            else
                binding.loadingProgressBar.setVisibility(View.GONE);
        });

        feedViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(getContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        });
    }

    private void initRecommendedViewModel() {
        recommendedFeedViewModel =
                new ViewModelProvider(this).get(RecommendedOffersViewModel.class);

        recommendedFeedViewModel.getFeed().observe(getViewLifecycleOwner(), feedResponse -> {
            Log.d("TGGGG", "Size:" + feedResponse.content.size());
            recommandedOffersAdapter.setItems(feedResponse.content);
        });

        recommendedFeedViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {

        });

        recommendedFeedViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(getContext(), "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        });
    }

    public void onLoadMore() {
        if (isLoading) return;

        if (feed.getPageNumber() < feed.getTotalPages()) {
            isLoading = true;
            feedViewModel.fetchFeed(feed.getPageNumber() + 1, null, "");
        }
    }

    private void setupFilterSpinner(Context context) {
        binding.ivFilter.setVisibility(View.GONE);
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
                        //feedViewModel.fetchFeed(0, null, filter);
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

    @Override
    public void onJobApply(int position) {
        Log.i("DEBUG", "position:" + Integer.toString(position) + ", post id :" + feedAdapter.getFeed().get(position).getId());
        jopPostViewModel.applyToJob(feedAdapter.getFeed().get(position).getId());
        feedAdapter.getFeed().get(position).setIsLiked(true);
        feedAdapter.updateItem(position,feedAdapter.getFeed().get(position));
        Log.i("DEBUG:", "position" + Integer.toString(position));
    }

    @Override
    public void likeDislikeQuestionPost(FeedContentResponse post, int position) {
        feedViewModel.likeDislikeQuestionPost(post);
        feedAdapter.getFeed().get(position).setIsLiked( !feedAdapter.getFeed().get(position).isLiked() );
        feedAdapter.updateItem(position,feedAdapter.getFeed().get(position));
    }

    @Override
    public void likeDislikeBlogPost(FeedContentResponse post, int position) {
        feedViewModel.likeDislikeBlogPost(post);
        feedAdapter.getFeed().get(position).setIsLiked( !feedAdapter.getFeed().get(position).isLiked() );
        feedAdapter.updateItem(position,feedAdapter.getFeed().get(position));
    }

    private void refreshData() {
        feedAdapter.clearContent();
        recommandedOffersAdapter.clearData();

        // Fetch new data
        feedViewModel.fetchFeed(0, null, "");
        recommendedFeedViewModel.fetchFeed(0, null, "PFE");
    }

}