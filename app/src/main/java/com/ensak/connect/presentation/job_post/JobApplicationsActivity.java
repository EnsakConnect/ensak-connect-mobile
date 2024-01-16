package com.ensak.connect.presentation.job_post;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ensak.connect.R;
import com.ensak.connect.adapters.feed.FeedAdapter;
import com.ensak.connect.adapters.job_post.ApplicationAdapter;
import com.ensak.connect.databinding.JobApplicationsActivityBinding;
import com.ensak.connect.repository.feed.model.FeedContentResponse;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class JobApplicationsActivity extends AppCompatActivity {

    final private String POST = "post";
    private JobApplicationsActivityBinding binding;
    private FeedContentResponse post;

    private JobPostViewModel jobPostViewModel;

    private RecyclerView recyclerView;

    private ApplicationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JobApplicationsActivityBinding.inflate(getLayoutInflater());
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

        Intent intent = getIntent();
        if (intent != null) {
            post = (FeedContentResponse) intent.getSerializableExtra(POST);
        }

        initView();
        initViewModel();
    }

    private void initViewModel() {
        jobPostViewModel = new ViewModelProvider(this).get(JobPostViewModel.class);

        jobPostViewModel.getApplications().observe(this, applications -> {
            adapter.setApplications(applications);
        });

        jobPostViewModel.getJobApplications(post.getId());

    }

    private void initView() {
        binding.tvUserName.setText(post.getAuthor().getFullName());
        binding.tvBody.setText(post.getDescription());
        binding.tvTags.setText("#" + String.join(", #", post.getTags()));
        binding.tvTimeAgo.setText(post.getTimePassed().replace(" minutes ago", "m").replace(" hours ago", "h").replace(" days ago", "d").replace(" months ago", "mon").replace(" years ago", "y"));
        binding.tvUserTitle.setText(post.getAuthor().getTitle());
        binding.tvApplications.setText(post.getCommentsCount()+" Applications");
        if(post.getCommentsCount() != 0){
            binding.tvNoAnswers.setVisibility(View.GONE);
        }

        recyclerView = binding.rvApplications;
        adapter = new ApplicationAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}