package com.ensak.connect.presentation.search;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.adapters.feed.OnPostInteractionListener;
import com.ensak.connect.databinding.ActivitySearchBinding;
import com.ensak.connect.databinding.NotificationActivityBinding;
import com.ensak.connect.presentation.home.FeedViewModel;
import com.ensak.connect.presentation.job_post.JobPostViewModel;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity implements OnPostInteractionListener {

    private ActivitySearchBinding binding;
    String filter = "ALL";
    String searchText = "";
    FeedViewModel feedViewModel;
    private FeedAdapter adapter;
    private FeedResponse feed;
    private RecyclerView recyclerView;
    private JobPostViewModel jobPostViewModel;
    private boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewModel();
        setupFeedRecycleView();

        binding.etName.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
        binding.ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SearchActivity.this, binding.ivFilter);
                popupMenu.getMenuInflater().inflate(R.menu.search_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();


                        if (itemId == R.id.search_blog) {
                            binding.searchOption.setText("Blog");
                            filter = "BlogPost";
                            return true;
                        } else if (itemId == R.id.search_doctorate) {
                            binding.searchOption.setText("Doctorat");
                            filter = "DOCTORATE";
                            return true;
                        } else if (itemId == R.id.search_intern_offer) {
                            binding.searchOption.setText("Offre de stage");
                            filter = "PFE";
                            return true;
                        } else if (itemId == R.id.search_job_offer) {
                            binding.searchOption.setText("Offre d'emploi");
                            filter = "CDI";
                            return true;
                        }
//                        else if (itemId == R.id.search_profile) {
//                            binding.searchOption.setText("Profile");
//                            filter = "Profile";
//                            return true;
//                        }
                        else if (itemId == R.id.search_QA) {
                           binding.searchOption.setText("Q&A");
                           filter = "Q&A";
                            return true;
                        }
                        else if(itemId == R.id.search_all){
                            binding.searchOption.setText("Tout(Posts)");
                            filter = "ALL";
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });
        binding.cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (filter.equals("Profile")){
//                    feedViewModel.fetchProfiles(0, searchText);

//                }else{
                    feedViewModel.fetchFeed(0, searchText, filter);
//                }

            }
        });
    }

    private void setupFeedRecycleView() {
        recyclerView = binding.rvAllOffers;
        adapter = new FeedAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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


    @Override
    public void onJobApply(int position) {
        Log.i("DEBUG", "position:" + Integer.toString(position) + ", post id :" + adapter.getFeed().get(position).getId());
        jobPostViewModel.applyToJob(adapter.getFeed().get(position).getId());
        adapter.getFeed().get(position).setIsLiked(true);
        adapter.updateItem(position,adapter.getFeed().get(position));
        Log.i("DEBUG:", "position" + Integer.toString(position));
    }

    @Override
    public void likeDislikeQuestionPost(FeedContentResponse post, int position) {
        feedViewModel.likeDislikeQuestionPost(post);
        adapter.getFeed().get(position).setIsLiked( !adapter.getFeed().get(position).isLiked() );
        adapter.updateItem(position,adapter.getFeed().get(position));
    }

    @Override
    public void likeDislikeBlogPost(FeedContentResponse post, int position) {
        feedViewModel.likeDislikeBlogPost(post);
        adapter.getFeed().get(position).setIsLiked( !adapter.getFeed().get(position).isLiked() );
        adapter.updateItem(position,adapter.getFeed().get(position));
    }

    public void onLoadMore() {
        if (isLoading) return;

        if (feed.getPageNumber() < feed.getTotalPages()) {
            isLoading = true;
            feedViewModel.fetchFeed(feed.getPageNumber() + 1, searchText, filter);
        }
    }
    private void initViewModel() {
        feedViewModel =
                new ViewModelProvider(this).get(FeedViewModel.class);

        feedViewModel.getFeed().observe(this, feedResponse -> {
            feed = feedResponse;
            adapter.setItems(feedResponse.content);
            isLoading = false;
        });

        feedViewModel.getIsLoading().observe(this, isLoading -> {
            if (isLoading)
                binding.loadingProgressBar.setVisibility(View.VISIBLE);
            else
                binding.loadingProgressBar.setVisibility(View.GONE);
        });

        feedViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage.isEmpty()) {
                return;
            }
            Toast.makeText(this, "An error occurred, please try again", Toast.LENGTH_SHORT).show();
        });
    }




}