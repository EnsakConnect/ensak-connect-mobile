package com.ensak.connect.presentation.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.adapters.feed.OnPostInteractionListener;
import com.ensak.connect.databinding.UserPostActivityBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;
import com.ensak.connect.repository.feed.model.FeedResponse;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserPostActivity extends AppCompatActivity implements OnPostInteractionListener {

    private UserPostActivityBinding binding;
    private UserPostViewModel userPostViewModel;
    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UserPostActivityBinding.inflate(getLayoutInflater());
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

        initView();
        iniVewModel();

    }

    private void iniVewModel() {
        userPostViewModel = new ViewModelProvider(this).get(UserPostViewModel.class);

        userPostViewModel.getFeed().observe(this, feedResponse -> {
            adapter.setItems(feedResponse.content);
        });

        userPostViewModel.getUserPosts();
    }

    private void initView() {
        recyclerView = binding.rvUserPosts;
        adapter = new FeedAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onJobApply(int position) {

    }

    @Override
    public void likeDislikeQuestionPost(FeedContentResponse post, int position) {

    }

    @Override
    public void likeDislikeBlogPost(FeedContentResponse post, int position) {

    }
}