package com.ensak.connect.presentation.job_post.comments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ensak.connect.adapters.comments.CommentsAdapter;
import com.ensak.connect.databinding.JobPostCommentsActivityBinding;
import com.ensak.connect.repository.blog_post.model.BlogPostCommentResponse;

import java.util.ArrayList;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CommentsActivity extends AppCompatActivity {

    private JobPostCommentsActivityBinding binding;
    private CommentViewModel commentViewModel;

    private ArrayList<BlogPostCommentResponse> comments;
    private CommentsAdapter adapter;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JobPostCommentsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        /**
         * Action bar
         */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Reading extras
         */
        Bundle extras = getIntent().getExtras();
        postId = "1";
        if (extras != null) {
            postId = String.valueOf(extras.getInt("postId"));
        }

        initView();
        initViewModel();

        commentViewModel.fetchComments(postId);
    }

    private void initViewModel() {
        commentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        commentViewModel.getComments().observe(this, commentsValue -> {
                if(commentsValue.isEmpty())
                    return;
                adapter.updateComments(commentsValue);
        });

        commentViewModel.getIsLoading().observe(this, isLoading -> {
            // TODO: show loading state
        });

        commentViewModel.getErrorMessage().observe(this, isLoading -> {
            // TODO: show error state
        });
    }

    private void initView() {
        comments = new ArrayList<>();
        adapter = new CommentsAdapter(comments);
        binding.rvComments.setAdapter(adapter);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(this));

        binding.cardSendComment.setOnClickListener(view -> {
            sendNewComment();
        });
    }

    private void sendNewComment() {
        String comment = binding.etComment.getText().toString();
        if (comment.isEmpty()) {
            Toast.makeText(CommentsActivity.this, "Please enter a comment.", Toast.LENGTH_LONG).show();
            return;
        }

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.etComment.getWindowToken(), 0);
        commentViewModel.sendComment(postId, comment);
        adapter.notifyItemInserted(0);
        adapter.notifyDataSetChanged();

        // Clear input
        binding.etComment.setText("");
        binding.etComment.clearFocus();
    }
}